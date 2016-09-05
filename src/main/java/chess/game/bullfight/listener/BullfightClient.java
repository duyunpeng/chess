package chess.game.bullfight.listener;

import chess.application.bullfightrecord.IBullfightRecordAppService;
import chess.application.user.IApiUserAppService;
import chess.application.user.representation.ApiUserRepresentation;
import chess.core.common.Constants;
import chess.core.redis.RedisService;
import chess.game.bullfight.command.PushData;
import chess.game.bullfight.command.ReceiveObject;
import chess.game.bullfight.mode.Action;
import chess.game.bullfight.mode.Player;
import chess.game.bullfight.mode.Seat;
import chess.game.bullfight.push.*;
import chess.game.bullfight.take.Game;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by dyp
 * Date : 16-6-20.
 */
public class BullfightClient implements Runnable {

    //判断是否所有的玩家都已抢庄 allRob
    boolean allRob = false;
    //抢庄的人数 robCount
    int robCount = 0;
    //最大的抢庄倍数 max
    int max = 0;
    // 同为最大抢庄倍数的人数 a
    int a = 0;
    //放桌号的集合
    List<String> deskList = new ArrayList<>();
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private Socket s;
    private String user;
    private DataInputStream dis = null;
    private DataOutputStream dos = null;
    private IApiUserAppService userAppService;
    private RedisService redisService;
    private IBullfightRecordAppService bullfightRecordAppService;
    private boolean isConnect;

    public BullfightClient() {

    }

    public BullfightClient(Socket s, RedisService redisService, IApiUserAppService userAppService, IBullfightRecordAppService bullfightRecordAppService) {
        this.s = s;
        try {
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());
            this.userAppService = userAppService;
            this.redisService = redisService;
            this.bullfightRecordAppService = bullfightRecordAppService;
            this.isConnect = true;
        } catch (EOFException e) {
            logger.info("socket.shutdown.message");
            close();
        } catch (IOException e) {
            logger.info("socket.connection.fail.message");
            close();
        }
    }

    public boolean send(String str, BullfightClient client) {
        try {
            if (null != client) {
                client.dos.writeUTF(str.replace(" ", "").replace("\n", "").replace("\t", ""));
                System.out.println("给[" + client.user + "]推送数据------>>" + str);
            }
            return true;
        } catch (IOException e) {
            logger.info("socket.server.sendMessage.fail.message");
            close();
            return false;
        }
    }

    public void close() {
        try {
            breakLine();
            if (dis != null)
                dis.close();
            if (dos != null)
                dos.close();
            if (s != null) {
                s.close();
            }
            BullfightTcpService.userClients.remove(user);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (isConnect) {
                String str = dis.readUTF();
                System.out.println("接收数据<<<<<<<<" + str);
                ReceiveObject obj = JSON.parseObject(str, ReceiveObject.class);
                JSONObject jsonObject = JSON.parseObject(obj.getData());
                user = jsonObject.getString("user");
                int type = obj.getType();
                switch (type) {
                    case 10001:// 连接 根据基础分自动配桌
                        BullfightTcpService.userClients.put(user, this);
                        this.pairGame(obj);
                        break;
                    case 10002://取消配桌
                        this.cancel(obj);
                        break;
                    case 10003://几倍抢庄
                        this.robBullfight(obj);
                        break;
                    case 10004://几倍下注
                        this.bet(obj);
                        break;
                    case 10005://离开
                        this.leaveDesk(obj);
                        break;
                }
                System.out.println("\n");
            }
        } catch (EOFException e) {
            logger.info("socket.shutdown.message");
            close();
        } catch (IOException e) {
            logger.info("socket.dirty.shutdown.message");
            close();
        } finally {
            close();
        }
    }

    /**
     * 掉线处理
     */
    private synchronized void breakLine() {
        System.out.println("-------------------------玩家" + user + "掉线进行托管");
        synchronized (BullfightTcpService.deskList_10) {
            if (BullfightTcpService.deskList_10.size() != 0) {
                String deskNo = isGamePlayer(BullfightTcpService.deskList_10.get("bullfight_10"));
                this.collocation(deskNo);
            }
        }
        synchronized (BullfightTcpService.deskList_20) {
            if (BullfightTcpService.deskList_20.size() != 0) {
                String deskNo = isGamePlayer(BullfightTcpService.deskList_20.get("bullfight_20"));
                this.collocation(deskNo);
            }
        }
        synchronized (BullfightTcpService.deskList_30) {
            if (BullfightTcpService.deskList_30.size() != 0) {
                String deskNo = isGamePlayer(BullfightTcpService.deskList_30.get("bullfight_30"));
                this.collocation(deskNo);
            }
        }
        synchronized (BullfightTcpService.deskList_40) {
            if (BullfightTcpService.deskList_40.size() != 0) {
                String deskNo = isGamePlayer(BullfightTcpService.deskList_40.get("bullfight_40"));
                this.collocation(deskNo);
            }
        }
    }

    /**
     * 游戏配桌
     *
     * @param obj 接收数据
     */
    private synchronized void pairGame(ReceiveObject obj) {
        JSONObject data = JSON.parseObject(obj.getData());
        int baseScore = data.getIntValue("multiple"); //基础分
        user = data.getString("user");
        boolean isGame = false;
        deskList = this.getDeskList(baseScore);
        String isGamePlayer = isGamePlayer(deskList);
        //判断是否在游戏中
        if (null != isGamePlayer) {
            isGame = true;
            this.pairGameRepeat(baseScore);
        }
        //判断该玩家的金币是否能够进入游戏
        if (!isGame) {
            Boolean flag = bullfightRecordAppService.charge(user, baseScore);
            if (flag) {
                PushObject push = new PushObject();
                push.setCome(Boolean.TRUE);
                push.setType(GamePushObject.SIXTEEN.getValue());
                send(JSONObject.toJSONString(push), BullfightTcpService.userClients.get(user));
            }
            if (!flag) {
                PushObject push = new PushObject();
                push.setCome(Boolean.FALSE);
                push.setType(GamePushObject.FIFTEEN.getValue());
                send(JSONObject.toJSONString(push), BullfightTcpService.userClients.get(user));
            }
            if (flag) {
                switch (baseScore) {
                    case 10:
                        synchronized (BullfightTcpService.deskList_10) {
                            this.pairGameRepeat(baseScore);
                        }
                        break;
                    case 20:
                        synchronized (BullfightTcpService.deskList_20) {
                            this.pairGameRepeat(baseScore);
                        }
                        break;
                    case 30:
                        synchronized (BullfightTcpService.deskList_30) {
                            this.pairGameRepeat(baseScore);
                        }
                        break;
                    case 40:
                        synchronized (BullfightTcpService.deskList_40) {
                            this.pairGameRepeat(baseScore);
                        }
                        break;
                }
            }
        }
    }

    //根据积分得到该积分的所有桌子
    private List<String> getDeskList(int baseScore) {
        if(baseScore == 10){
            if (null != BullfightTcpService.deskList_10.get("bullfight_10")) {
                deskList.addAll(BullfightTcpService.deskList_10.get("bullfight_10"));
            }
        }
        if(baseScore == 20){
            if (null != BullfightTcpService.deskList_20.get("bullfight_20")) {
                 deskList.addAll(BullfightTcpService.deskList_20.get("bullfight_20"));
            }
        }
        if(baseScore == 30){
            if (null != BullfightTcpService.deskList_30.get("bullfight_30")) {
                deskList.addAll(BullfightTcpService.deskList_30.get("bullfight_30"));
            }
        }
        if(baseScore == 40){
            if (null != BullfightTcpService.deskList_40.get("bullfight_40")) {
                deskList.addAll(BullfightTcpService.deskList_40.get("bullfight_40"));
            }
        }
        return deskList;
    }

    /**
     * 取消配桌
     *
     * @param obj 接收数据
     */
    private synchronized void cancel(ReceiveObject obj) {
        JSONObject data = JSON.parseObject(obj.getData());
        user = data.getString("user");
        if (null != BullfightTcpService.deskList_10.get("bullfight_10")) {
            deskList.addAll(BullfightTcpService.deskList_10.get("bullfight_10"));
        }
        if (null != BullfightTcpService.deskList_20.get("bullfight_20")) {
            deskList.addAll(BullfightTcpService.deskList_20.get("bullfight_20"));
        }
        if (null != BullfightTcpService.deskList_30.get("bullfight_30")) {
            deskList.addAll(BullfightTcpService.deskList_30.get("bullfight_30"));
        }
        if (null != BullfightTcpService.deskList_40.get("bullfight_40")) {
            deskList.addAll(BullfightTcpService.deskList_40.get("bullfight_40"));
        }
        //得到该玩家的桌号
        String deskNo = isGamePlayer(deskList);
        if (redisService.exists(deskNo)) {
            String jsonData = redisService.getCache(deskNo);
            Game game = JSONObject.parseObject(jsonData, Game.class);
            //将该玩家的取消配桌的状态推送，并清除改玩家的数据
            for (int i = 0; i < game.getDesk().getSeats().size(); i++) {
                if (game.getDesk().getSeats().get(i).getPlayer() != null) {
                    if (game.getDesk().getSeats().get(i).getPlayer().getUserName().equals(user)) {
                        PushObject push = new PushObject();
                        push.setType(GamePushObject.FOURTEEN.getValue());
                        if (game.getDesk().getSeats().get(i).getIsExit().getValue() == 1) {
                            if (game.getDesk().getSeats().get(i).getIsConnect().getValue() == 1) {
                                send(JSONObject.toJSONString(push), BullfightTcpService.userClients.get(game.getDesk().getSeats().get(i).getPlayer().getUserName()));
                            }
                        }
                        game.getDesk().getSeats().get(i).setBankerMultiple(0);
                        game.getDesk().getSeats().get(i).setIsTheBanker(Action.FALSE);
                        game.getDesk().getSeats().get(i).setMultiple(0);
                        game.getDesk().getSeats().get(i).setBetYesOrNo(Action.FALSE);
                        game.getDesk().getSeats().get(i).setCards(null);
                        game.getDesk().getSeats().get(i).setIsRobBanker(null);
                        game.getDesk().getSeats().get(i).setPlayer(null);
                        game.getDesk().getSeats().get(i).setIsExit(Action.FALSE);
                        game.getDesk().getSeats().get(i).setIsOldOrNew(null);
                        game.getDesk().getSeats().get(i).setIsPlaying(null);
                        game.getDesk().getSeats().get(i).setIsConnect(Action.FALSE);
                    }
                }
            }
            redisService.addCache(game.getDesk().getDeskNo(), JSONObject.toJSONString(game), Constants.REDIS_GAME_TIME_OUT);
        }
    }

    /**
     * 斗牛（几倍抢庄）
     *
     * @param obj 接收数据
     */
    private synchronized void robBullfight(ReceiveObject obj) {
        JSONObject data = JSON.parseObject(obj.getData());
        String deskNo = data.getString("deskNo");
        int seatNo = data.getIntValue("seatNo");
        int bankerMultiple = data.getIntValue("multiple"); //抢庄倍数(1.2.3.4)
        String jsonData = redisService.getCache(deskNo);
        Game game = JSON.parseObject(jsonData, Game.class);
        //判断游戏是否存在
        if (redisService.exists(deskNo)) {
            for (int i = 0; i < game.getDesk().getSeats().size(); i++) {
                if (game.getDesk().getSeats().get(i).getSeatNo() == seatNo) {
                    user = game.getDesk().getSeats().get(i).getPlayer().getUserName();
                }
            }
            //设置该玩家的抢庄倍数
            for (int i = 0; i < game.getDesk().getSeats().size(); i++) {
                if (game.getDesk().getSeats().get(i).getPlayer() != null) {
                    if (game.getDesk().getSeats().get(i).getPlayer().getUserName().equals(user)) {
                        if (game.getDesk().getSeats().get(i).getIsRobBanker() == null) {
                            if (bankerMultiple == 0) {
                                game.getDesk().getSeats().get(i).setBankerMultiple(0);
                                game.getDesk().getSeats().get(i).setIsRobBanker(Action.FALSE);
                            } else {
                                game.getDesk().getSeats().get(i).setBankerMultiple(bankerMultiple);
                                game.getDesk().getSeats().get(i).setIsRobBanker(Action.TRUE);
                            }
                        }
                    }
                }
            }
            //将断线的并且没有下注的玩家默认下注
            for (int i = 0; i < game.getDesk().getSeats().size(); i++) {
                if (game.getDesk().getSeats().get(i).getPlayer() != null) {
                    if (game.getDesk().getSeats().get(i).getIsConnect().getValue() == 2) {
                        if (game.getDesk().getSeats().get(i).getIsRobBanker() == null) {
                            game.getDesk().getSeats().get(i).setBankerMultiple(0);
                            game.getDesk().getSeats().get(i).setIsRobBanker(Action.FALSE);
                        }
                    }
                }
            }
            redisService.addCache(deskNo, JSON.toJSONString(game), Constants.REDIS_GAME_TIME_OUT);
        } else {
            System.out.println("游戏不存在");
        }
    }

    /**
     * 获取下注的倍数
     *
     * @param obj 接收数据
     */
    private synchronized void bet(ReceiveObject obj) {
        JSONObject data = JSON.parseObject(obj.getData());
        String deskNo = data.getString("deskNo");    //桌号
        int seatNo = data.getIntValue("seatNo");     //座位号
        int multiple = data.getIntValue("multiple"); //下注的倍数
        String jsonData = redisService.getCache(deskNo);
        Game game = JSON.parseObject(jsonData, Game.class);
        if (redisService.exists(deskNo)) {
            //通过座位号找到玩家
            for (int j = 0; j < game.getDesk().getSeats().size(); j++) {
                if (game.getDesk().getSeats().get(j).getSeatNo() == seatNo) {
                    user = game.getDesk().getSeats().get(j).getPlayer().getUserName();
                }
            }
            //将玩家的下注设置
            for (int i = 0; i < game.getDesk().getSeats().size(); i++) {
                if (game.getDesk().getSeats().get(i).getPlayer() != null) {
                    if (game.getDesk().getSeats().get(i).getPlayer().getUserName().equals(user)) {
                        if (game.getDesk().getSeats().get(i).getIsTheBanker().getValue() != 1) {
                            if (game.getDesk().getSeats().get(i).getBetYesOrNo().getValue() == 2) {
                                if (multiple == 0) {
                                    game.getDesk().getSeats().get(i).setBetYesOrNo(Action.TRUE);
                                    game.getDesk().getSeats().get(i).setMultiple(5);//如果不下注的话 默认5倍下注
                                } else {
                                    game.getDesk().getSeats().get(i).setBetYesOrNo(Action.TRUE);
                                    game.getDesk().getSeats().get(i).setMultiple(multiple);
                                }
                            }
                        }
                    }
                }
            }
            redisService.addCache(deskNo, JSON.toJSONString(game), Constants.REDIS_GAME_TIME_OUT);
            //将该玩家的下注倍数推送给所有的玩家
            this.collocationDefaultBet(game);
            redisService.addCache(deskNo, JSON.toJSONString(game), Constants.REDIS_GAME_TIME_OUT);
        } else {
            System.out.println("游戏不存在");
        }
    }

    /**
     * 离开桌
     *
     * @param obj 接收数据
     */
    private synchronized void leaveDesk(ReceiveObject obj) {
        JSONObject data = JSON.parseObject(obj.getData());
        int seatNo = data.getIntValue("seatNo");
        List<String> deskList = new ArrayList<>();
        int leave_seatNo = -1;//离开的玩家的座位号
        if (null != BullfightTcpService.deskList_10.get("bullfight_10")) {
            deskList.addAll(BullfightTcpService.deskList_10.get("bullfight_10"));
        }
        if (null != BullfightTcpService.deskList_20.get("bullfight_20")) {
            deskList.addAll(BullfightTcpService.deskList_20.get("bullfight_20"));
        }
        if (null != BullfightTcpService.deskList_30.get("bullfight_30")) {
            deskList.addAll(BullfightTcpService.deskList_30.get("bullfight_30"));
        }
        if (null != BullfightTcpService.deskList_40.get("bullfight_40")) {
            deskList.addAll(BullfightTcpService.deskList_40.get("bullfight_40"));
        }
        String deskNo = isGamePlayer(deskList);
        if (redisService.exists(deskNo)) {
            String JsonData = redisService.getCache(deskNo);
            Game game = JSONObject.parseObject(JsonData, Game.class);
            //通过座位号得到玩家
            for (int i = 0; i < game.getDesk().getSeats().size(); i++) {
                if (game.getDesk().getSeats().get(i).getSeatNo() == seatNo) {
                    user = game.getDesk().getSeats().get(i).getPlayer().getUserName();
                    leave_seatNo = game.getDesk().getSeats().get(i).getSeatNo();
                }
            }
            //得到离开玩家的身份
            boolean playerIdentity = false;
            for (int i = 0; i < game.getDesk().getSeats().size(); i++) {
                if (game.getDesk().getSeats().get(i).getPlayer() != null) {
                    if (game.getDesk().getSeats().get(i).getPlayer().getUserName().equals(user)) {
                        //离开的玩家isExit设置为false
                        game.getDesk().getSeats().get(i).setIsExit(Action.FALSE);
                        if (game.getDesk().getSeats().get(i).getIsTheBanker().getValue() == 1)
                            playerIdentity = true;
                        break;
                    }
                }
            }
            boolean isBet = false;//是否已经开始下注
            int multiple = 0;//得到下注的倍数
            int bankerMultiple = 0; //得到抢庄的倍数
            boolean isExistBanker = false;//是否存在庄家
            int baseScore = game.getDesk().getBaseScore();//得到这场游戏的底分
            int banker_seatNo = -1;
            //判断这场游戏有没有庄家
            for (int i = 0; i < game.getDesk().getSeats().size(); i++) {
                if (game.getDesk().getSeats().get(i).getIsTheBanker().getValue() == 1) {
                    isExistBanker = true;
                }
            }
            //判断游戏进行的程度(是否开始下注)
            for (int i = 0; i < game.getDesk().getSeats().size(); i++) {
                if (!isExistBanker) {
                    isBet = false;
                    break;
                }
                if (game.getDesk().getSeats().get(i).getMultiple() != 0) {
                    if (!playerIdentity) {
                        if (game.getDesk().getSeats().get(i).getSeatNo() == leave_seatNo) {
                            //离开的玩家为闲家，得到闲家下注的倍数
                            multiple = game.getDesk().getSeats().get(i).getMultiple();
                        }
                    }
                    isBet = true;
                } else {
                    if (game.getDesk().getSeats().get(i).getIsTheBanker().getValue() == 1) {
                        //得到庄家的抢庄倍数
                        bankerMultiple = game.getDesk().getSeats().get(i).getBankerMultiple();
                        if(bankerMultiple == 0){
                            bankerMultiple = 1;
                        }
                        banker_seatNo = game.getDesk().getSeats().get(i).getSeatNo();
                    }
                }
            }
            List<LeavePushObject> leavePushObjectList = new ArrayList<>();
            if (isBet) {
                //已经开始下注
                if (playerIdentity) {
                    //离开的是庄家,得到每个闲家
                    for (int i = 0; i < game.getDesk().getSeats().size(); i++) {
                        if (game.getDesk().getSeats().get(i).getPlayer() != null) {
                            if (null != game.getDesk().getSeats().get(i).getIsPlaying()) {
                                if (game.getDesk().getSeats().get(i).getIsTheBanker().getValue() == 2) {
                                    //得到每个闲家的下注倍数
                                    multiple = game.getDesk().getSeats().get(i).getMultiple();
                                    if (multiple == 0) {
                                        multiple = 1;
                                    }
                                    //庄家赔给闲家的积分
                                    BigDecimal s_sum = new BigDecimal(baseScore * multiple * bankerMultiple);
                                    PushObject push = new PushObject();
                                    LeavePushObject leavePushObject = new LeavePushObject();
                                    leavePushObject.setMoney(s_sum);
                                    leavePushObject.setSeatNo(game.getDesk().getSeats().get(i).getSeatNo());
                                    push.setLeavePushObject(leavePushObject);
                                    leavePushObjectList.add(leavePushObject);
                                }
                            }
                        }
                    }
                } else {
                    //闲家赔给庄家的金币
                    BigDecimal sum_score = new BigDecimal(baseScore * multiple * bankerMultiple);
                    for (int i = 0; i < game.getDesk().getSeats().size(); i++) {
                        if (game.getDesk().getSeats().get(i).getPlayer() != null) {
                            if (game.getDesk().getSeats().get(i).getIsTheBanker().getValue() == 1) {
                                sum_score = bullfightRecordAppService.leaveCompensate(game, user, sum_score);
                                PushObject push = new PushObject();
                                LeavePushObject leavePushObject = new LeavePushObject();
                                leavePushObject.setMoney(sum_score);
                                leavePushObject.setSeatNo(leave_seatNo);
                                push.setLeavePushObject(leavePushObject);
                                send(JSONObject.toJSONString(push), BullfightTcpService.userClients.get(game.getDesk().getSeats().get(i).getPlayer().getUserName()));
                            }
                        }
                    }
                }
            }
            if (!isBet) {
                //还没有下注，判断庄家是否出来了
                for (int i = 0; i < game.getDesk().getSeats().size(); i++) {
                    if (game.getDesk().getSeats().get(i).getIsTheBanker().getValue() == 1) {
                        isExistBanker = true;
                        break;
                    }
                }
                if (isExistBanker) {
                    //庄家已经出来了
                    if (playerIdentity) {
                        //庄家离开,赔偿给正在进行游戏的玩家金币
                        for (int i = 0; i < game.getDesk().getSeats().size(); i++) {
                            if (game.getDesk().getSeats().get(i).getIsExit().getValue() == 1) {
                                if (null != game.getDesk().getSeats().get(i).getIsPlaying()) {
                                    if (game.getDesk().getSeats().get(i).getIsTheBanker().getValue() == 2) {
                                        //庄家赔给闲家的积分
                                        int s_sum = baseScore * bankerMultiple;
                                        PushObject push = new PushObject();
                                        LeavePushObject leavePushObject = new LeavePushObject();
                                        leavePushObject.setMoney(new BigDecimal(s_sum));
                                        leavePushObject.setSeatNo(game.getDesk().getSeats().get(i).getSeatNo());
                                        push.setLeavePushObject(leavePushObject);
                                        push.setType(GamePushObject.THIRTEEN.getValue());
                                        leavePushObjectList.add(leavePushObject);
                                    }
                                }
                            }
                        }
                    } else {
                        //闲家离开，赔偿给庄家金币
                        int sum_score = baseScore * bankerMultiple;
                        for (int i = 0; i < game.getDesk().getSeats().size(); i++) {
                            if (game.getDesk().getSeats().get(i).getPlayer() != null) {
                                if (game.getDesk().getSeats().get(i).getPlayer().getUserName().equals(user)) {
                                    if (game.getDesk().getSeats().get(i).getIsTheBanker().getValue() == 2) {
                                        if (game.getDesk().getSeats().get(i).getIsExit().getValue() == 2) {
                                            for (int j = 0; j < game.getDesk().getSeats().size(); j++) {
                                                if (game.getDesk().getSeats().get(j).getIsExit().getValue() == 1) {
                                                    if (game.getDesk().getSeats().get(j).getIsConnect().getValue() == 1) {
                                                        if (game.getDesk().getSeats().get(j).getIsTheBanker().getValue() == 1) {
                                                            BigDecimal bigDecimal = bullfightRecordAppService.leaveCompensate(game, user, new BigDecimal(sum_score));
                                                            PushObject push = new PushObject();
                                                            LeavePushObject leavePushObject = new LeavePushObject();
                                                            leavePushObject.setSeatNo(leave_seatNo);
                                                            leavePushObject.setMoney(bigDecimal);
                                                            push.setLeavePushObject(leavePushObject);
                                                            push.setType(GamePushObject.EIGHT.getValue());
                                                            send(JSONObject.toJSONString(push), BullfightTcpService.userClients.get(game.getDesk().getSeats().get(j).getPlayer().getUserName()));
                                                        }
                                                        if (game.getDesk().getSeats().get(j).getIsTheBanker().getValue() == 2) {
                                                            //告知其余的闲家，有玩家离开
                                                            Integer playerSeatNoTwo = game.getDesk().getSeats().get(i).getSeatNo();
                                                            PushObject push = new PushObject();
                                                            PlayerLeavePushObject playerLeavePushObject = new PlayerLeavePushObject();
                                                            playerLeavePushObject.setSeatNo(playerSeatNoTwo);
                                                            push.setPlayerLeavePushObject(playerLeavePushObject);
                                                            push.setType(GamePushObject.EIGHT.getValue());
                                                            send(JSONObject.toJSONString(push), BullfightTcpService.userClients.get(game.getDesk().getSeats().get(j).getPlayer().getUserName()));
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                //庄家还没有出来之前离开 只需要推送谁离开告知所有人即可
                if (!isExistBanker) {
                    for (int i = 0; i < game.getDesk().getSeats().size(); i++) {
                        if (game.getDesk().getSeats().get(i).getPlayer() != null) {
                            if (null != game.getDesk().getSeats().get(i).getIsPlaying()) {
                                if (game.getDesk().getSeats().get(i).getPlayer().getUserName().equals(user)) {
                                    if (game.getDesk().getSeats().get(i).getIsExit().getValue() == 2) {
                                        PushObject push = new PushObject();
                                        PlayerLeavePushObject player = new PlayerLeavePushObject();
                                        player.setSeatNo(game.getDesk().getSeats().get(i).getSeatNo());
                                        push.setPlayerLeavePushObject(player);
                                        push.setType(GamePushObject.EIGHT.getValue());
                                        for (int j = 0; j < game.getDesk().getSeats().size(); j++) {
                                            if (game.getDesk().getSeats().get(j).getIsExit().getValue() == 1) {
                                                if (game.getDesk().getSeats().get(j).getIsConnect().getValue() == 1) {
                                                    send(JSONObject.toJSONString(push), BullfightTcpService.userClients.get(game.getDesk().getSeats().get(j).getPlayer().getUserName()));
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            //离开的玩家是庄家
            if (playerIdentity) {
                leavePushObjectList = bullfightRecordAppService.bankerLeaveCompensate(leavePushObjectList, game);
                //清除玩家的数据
                this.clearData(game);
                //将数据推送给每个闲家
                for (Seat seat : game.getDesk().getSeats()) {
                    if (null != seat.getPlayer()) {
                        if (seat.getIsTheBanker().getValue() == 2) {
                            for (LeavePushObject leavePushObject : leavePushObjectList) {
                                if (seat.getSeatNo() == leavePushObject.getSeatNo()) {
                                    PushObject push = new PushObject();
                                    LeavePushObject leavePush = new LeavePushObject();
                                    leavePush.setMoney(leavePushObject.getMoney());
                                    leavePush.setSeatNo(banker_seatNo);//庄家的座位号
                                    push.setLeavePushObject(leavePush);
                                    push.setType(GamePushObject.THIRTEEN.getValue());
                                    if (seat.getIsConnect().getValue() == 1) {
                                        send(JSONObject.toJSONString(push), BullfightTcpService.userClients.get(seat.getPlayer().getUserName()));
                                    }
                                    push = new PushObject();
                                    push.setType(GamePushObject.TEN.getValue());
                                    if (seat.getIsConnect().getValue() == 1) {
                                        send(JSONObject.toJSONString(push), BullfightTcpService.userClients.get(seat.getPlayer().getUserName()));
                                    }
                                }
                            }
                        }
                    }
                }
            }
            //得到这场游戏的玩家
            int count = 0;
            for (Seat seat : game.getDesk().getSeats()) {
                if (seat.getPlayer() != null) {
                    count++;
                }
            }
            //清除离开玩家的数据
            for (int i = 0; i < game.getDesk().getSeats().size(); i++) {
                if (game.getDesk().getSeats().get(i).getPlayer() != null) {
                    if (game.getDesk().getSeats().get(i).getPlayer().getUserName().equals(this.user)) {
                        game.getDesk().getSeats().get(i).setBankerMultiple(0);
                        game.getDesk().getSeats().get(i).setIsTheBanker(Action.FALSE);
                        game.getDesk().getSeats().get(i).setMultiple(0);
                        game.getDesk().getSeats().get(i).setBetYesOrNo(Action.FALSE);
                        game.getDesk().getSeats().get(i).setCards(null);
                        game.getDesk().getSeats().get(i).setIsRobBanker(null);
                        game.getDesk().getSeats().get(i).setPlayer(null);
                        game.getDesk().getSeats().get(i).setIsExit(Action.FALSE);
                        game.getDesk().getSeats().get(i).setIsOldOrNew(null);
                        game.getDesk().getSeats().get(i).setIsPlaying(null);
                        game.getDesk().getSeats().get(i).setIsConnect(Action.FALSE);
                        redisService.addCache(game.getDesk().getDeskNo(), JSONObject.toJSONString(game), Constants.REDIS_GAME_TIME_OUT);
                        count--;
                        if (count < 1) {
                            break;
                        }
                    }
                }
            }
            //如果当前玩家的人数
            if (count == 1) {
                for (int i = 0; i < game.getDesk().getSeats().size(); i++) {
                    if (game.getDesk().getSeats().get(i).getPlayer() != null) {
                        game.getDesk().getSeats().get(i).setBankerMultiple(0);
                        game.getDesk().getSeats().get(i).setIsTheBanker(Action.FALSE);
                        game.getDesk().getSeats().get(i).setMultiple(0);
                        game.getDesk().getSeats().get(i).setBetYesOrNo(Action.FALSE);
                        game.getDesk().getSeats().get(i).setCards(null);
                        game.getDesk().getSeats().get(i).setIsRobBanker(null);
                        PushObject push = new PushObject();
                        push.setType(GamePushObject.TEN.getValue());
                        send(JSONObject.toJSONString(push), BullfightTcpService.userClients.get(game.getDesk().getSeats().get(i).getPlayer().getUserName()));
                    }
                    redisService.addCache(game.getDesk().getDeskNo(), JSONObject.toJSONString(game), Constants.REDIS_GAME_TIME_OUT);
                }
            }
            //如果该桌的人数小于1则删除该桌,
            if (count < 1) {
                if (baseScore == 10) {
                    this.peopleLessOneDelete(redisService, game);
                }
            }
            //通知 离开桌成功
            PushObject push = new PushObject();
            push.setType(GamePushObject.ELEVEN.getValue());
            send(JSONObject.toJSONString(push), BullfightTcpService.userClients.get(user));
        } else {
            System.out.println("游戏不存在");
        }
    }

    //清除玩家的数据
    private void clearData(Game game) {
        game.getDesk().getSeats().stream().filter(seat -> null != seat.getPlayer()).filter(seat -> seat.getIsTheBanker().getValue() == 2).forEach(seat -> {
            seat.setBankerMultiple(0);
            seat.setIsTheBanker(Action.FALSE);
            seat.setMultiple(0);
            seat.setBetYesOrNo(Action.FALSE);
            seat.setCards(null);
            seat.setIsRobBanker(null);
        });
    }

    /**
     * 判断是不是在游戏中了
     */
    private String isGamePlayer(List<String> deskList) {
        for (String deskNo : deskList) {
            String jsonData = redisService.getCache(deskNo);
            Game game = JSONObject.parseObject(jsonData, Game.class);
            if (null != game) {
                for (Seat seat : game.getDesk().getSeats()) {
                    if (seat.getPlayer() != null) {
                        if (seat.getPlayer().getUserName().equals(user)) {
                            return game.getDesk().getDeskNo();
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * 生成随机桌号
     */
    private String deskNo() {
        String deskNo = UUID.randomUUID().toString();
        if (redisService.exists(deskNo)) {
            deskNo = deskNo();
        }
        return deskNo;
    }


    /**
     * 判断是否所有的玩家都已抢庄
     * 重复方法提取
     */
    public boolean allIsOrNotRob(Game game) {
        if (null != game) {
            for (int i = 0; i < game.getDesk().getSeats().size(); i++) {
                if (game.getDesk().getSeats().get(i).getIsExit().getValue() == 1) {
                    if (game.getDesk().getSeats().get(i).getIsPlaying() != null) {
                        if (game.getDesk().getSeats().get(i).getIsPlaying().getValue() == 1) {
                            if (game.getDesk().getSeats().get(i).getIsRobBanker() == null) {
                                allRob = false;
                                break;
                            } else {
                                allRob = true;
                            }
                        }
                    }
                }
            }
        }
        return allRob;
    }


    /**
     * 抢庄的人数
     * 重复方法提取
     */
    public int robCountMean(Game game) {
        for (int i = 0; i < game.getDesk().getSeats().size(); i++) {
            if (game.getDesk().getSeats().get(i).getIsPlaying() != null) {
                if (null != game.getDesk().getSeats().get(i).getIsPlaying()) {
                    if (game.getDesk().getSeats().get(i).getIsExit().getValue() == 1) {
                        if (game.getDesk().getSeats().get(i).getIsRobBanker().getValue() == 1) {
                            robCount++;
                        }
                    }
                }
            }
        }
        return robCount;
    }

    /**
     * 不抢庄的人数
     * 重复方法提取
     */
    public int notRobCountMean(Game game) {
        int countOther = 0;
        for (int i = 0; i < game.getDesk().getSeats().size(); i++) {
            if (game.getDesk().getSeats().get(i).getIsPlaying() != null) {
                if (game.getDesk().getSeats().get(i).getIsPlaying().getValue() == 1) {
                    if (game.getDesk().getSeats().get(i).getIsExit().getValue() == 1) {
                        countOther++;
                    }
                }
            }
        }
        return countOther;
    }

    /**
     * robCount>=2抢庄
     * 重复方法提取
     */
    public void robCountGeTwo(Game game) {
        for (int i = 0; i < game.getDesk().getSeats().size(); i++) {
            if (game.getDesk().getSeats().get(i).getIsExit().getValue() == 1) {
                if (max < game.getDesk().getSeats().get(i).getBankerMultiple()) {
                    max = game.getDesk().getSeats().get(i).getBankerMultiple();
                }
            }
        }
        for (int i = 0; i < game.getDesk().getSeats().size(); i++) {
            if (game.getDesk().getSeats().get(i).getIsExit().getValue() == 1) {
                if (max == game.getDesk().getSeats().get(i).getBankerMultiple()) {
                    a++;
                }
            }
        }
        if (a == 1) {
            countPeople(game);
        }
        if (a > 1) {
            countPeopleGtOne(game);
        }
    }

    /**
     * 最大抢庄倍数的人数为1时
     * 重复方法提取
     */
    private void countPeople(Game game) {
        for (int i = 0; i < game.getDesk().getSeats().size(); i++) {
            if (game.getDesk().getSeats().get(i).getIsExit().getValue() == 1) {
                if (max == game.getDesk().getSeats().get(i).getBankerMultiple()) {
                    game.getDesk().getSeats().get(i).setIsTheBanker(Action.TRUE);
                }
            }
        }
    }

    /**
     * 最大抢庄倍数的人数大于1
     * 重复方法提取
     */
    private void countPeopleGtOne(Game game) {
        List<Seat> seats = new ArrayList<>();
        for (int i = 0; i < game.getDesk().getSeats().size(); i++) {
            if (game.getDesk().getSeats().get(i).getIsExit().getValue() == 1) {
                if (max == game.getDesk().getSeats().get(i).getBankerMultiple()) {
                    seats.add(game.getDesk().getSeats().get(i));
                }
            }
        }
        int s = (int) (Math.random() * seats.size());
        seats.get(s).setIsTheBanker(Action.TRUE);
        for (int i = 0; i < game.getDesk().getSeats().size(); i++) {
            if (game.getDesk().getSeats().get(i).getIsPlaying() != null) {
                if (game.getDesk().getSeats().get(i).getIsPlaying().getValue() == 1) {
                    if (game.getDesk().getSeats().get(i).getIsExit().getValue() == 1) {
                        if (game.getDesk().getSeats().get(i) == seats.get(s)) {
                            game.getDesk().getSeats().get(i).setIsTheBanker(Action.TRUE);
                        }
                    }
                }
            }
        }
    }

    /**
     * 庄家出来了的消息推送
     * 重复方法提取
     */
    public void robIsCome(Game game) {
        for (int i = 0; i < game.getDesk().getSeats().size(); i++) {
            if (game.getDesk().getSeats().get(i).getIsExit().getValue() == 1) {
                for (int j = 0; j < game.getDesk().getSeats().size(); j++) {
                    if (game.getDesk().getSeats().get(j).getIsTheBanker().getValue() == 1) {
                        PushObject push = new PushObject();
                        BankerPushObject banker = new BankerPushObject();
                        banker.setBankerMultiple(game.getDesk().getSeats().get(j).getBankerMultiple());
                        banker.setSeatNo(game.getDesk().getSeats().get(j).getSeatNo());
                        push.setType(GamePushObject.SEVEN.getValue());
                        push.setBankerPushObject(banker);
                        if (game.getDesk().getSeats().get(i).getIsConnect().getValue() == 1) {
                            send(JSONObject.toJSONString(push), BullfightTcpService.userClients.get(game.getDesk().getSeats().get(i).getPlayer().getUserName()));
                        }
                    }
                }
            }
        }
    }

    /**
     * 推送托管时，该玩家的下注的默认数据
     * 重复方法提取
     */
    private void collocationDefaultBet(Game game) {
        for (int i = 0; i < game.getDesk().getSeats().size(); i++) {
            if (game.getDesk().getSeats().get(i).getIsExit().getValue() == 1) {
                PushObject push = new PushObject();
                push.setType(GamePushObject.FOUR.getValue());
                for (int j = 0; j < game.getDesk().getSeats().size(); j++) {
                    if (game.getDesk().getSeats().get(j).getPlayer() != null) {
                        if (game.getDesk().getSeats().get(j).getPlayer().getUserName().equals(user)) {
                            BetPushObject betPushObject = new BetPushObject();
                            betPushObject.setSeatNo(game.getDesk().getSeats().get(j).getSeatNo());
                            betPushObject.setMultiple(game.getDesk().getSeats().get(j).getMultiple());
                            push.setBetPushObject(betPushObject);
                            if (game.getDesk().getSeats().get(i).getIsConnect().getValue() == 1) {
                                send(JSONObject.toJSONString(push), BullfightTcpService.userClients.get(game.getDesk().getSeats().get(i).getPlayer().getUserName()));
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * 如果该桌的人数小于1则删除该桌,
     * 重复方法提取
     */
    public void peopleLessOneDelete(RedisService redisService, Game game) {
        redisService.delete(game.getDesk().getDeskNo());
        int baseScore = game.getDesk().getBaseScore();
        if (baseScore == 10) {
            List<String> deskNoList_10 = new ArrayList<>();
            if (BullfightTcpService.deskList_10.get("bullfight_10") == null) {
                return;
            }
            deskNoList_10.addAll(BullfightTcpService.deskList_10.get("bullfight_10"));
            for (String temp_deskNo : deskNoList_10) {
                if (temp_deskNo.equals(game.getDesk().getDeskNo())) {
                    redisService.delete(game.getDesk().getDeskNo());
                    deskNoList_10.remove(temp_deskNo);
                    BullfightTcpService.deskList_10.get("bullfight_10").remove(temp_deskNo);
                    break;
                }
            }
            if (deskNoList_10.size() != 0) {
                BullfightTcpService.deskList_10.put("bullfight_10", deskNoList_10);
            }
        }
        if (baseScore == 20) {
            List<String> deskNoList_20 = new ArrayList<>();
            if (BullfightTcpService.deskList_20.get("bullfight_20") == null) {
                return;
            }
            deskNoList_20.addAll(BullfightTcpService.deskList_20.get("bullfight_20"));
            for (String temp_deskNo : deskNoList_20) {
                if (temp_deskNo.equals(game.getDesk().getDeskNo())) {
                    redisService.delete(game.getDesk().getDeskNo());
                    deskNoList_20.remove(temp_deskNo);
                    BullfightTcpService.deskList_20.get("bullfight_20").remove(temp_deskNo);
                    break;
                }
            }
            if (deskNoList_20.size() != 0) {
                BullfightTcpService.deskList_20.put("bullfight_20", deskNoList_20);
            }
        }
        if (baseScore == 30) {
            List<String> deskNoList_30 = new ArrayList<>();
            if (BullfightTcpService.deskList_30.get("bullfight_30") == null) {
                return;
            }
            deskNoList_30.addAll(BullfightTcpService.deskList_30.get("bullfight_30"));
            for (String temp_deskNo : deskNoList_30) {
                if (temp_deskNo.equals(game.getDesk().getDeskNo())) {
                    redisService.delete(game.getDesk().getDeskNo());
                    deskNoList_30.remove(temp_deskNo);
                    BullfightTcpService.deskList_30.get("bullfight_30").remove(temp_deskNo);
                    break;
                }
            }
            if (deskNoList_30.size() != 0) {
                BullfightTcpService.deskList_30.put("bullfight_30", deskNoList_30);
            }
        }
        if (baseScore == 40) {
            List<String> deskNoList_40 = new ArrayList<>();
            if (BullfightTcpService.deskList_40.get("bullfight_40") == null) {
                return;
            }
            deskNoList_40.addAll(BullfightTcpService.deskList_40.get("bullfight_40"));
            for (String temp_deskNo : deskNoList_40) {
                if (temp_deskNo.equals(game.getDesk().getDeskNo())) {
                    redisService.delete(game.getDesk().getDeskNo());
                    deskNoList_40.remove(temp_deskNo);
                    BullfightTcpService.deskList_40.get("bullfight_40").remove(temp_deskNo);
                    break;
                }
            }
            if (deskNoList_40.size() != 0) {
                BullfightTcpService.deskList_40.put("bullfight_40", deskNoList_40);
            }
        }
    }

    /**
     * 配桌重复方法提取
     * baseScore == 10 20 30 40
     * 重复方法提取
     */
    private void pairGameRepeat(int baseScore) {
        boolean result = true;
        //判断该玩家是否已经在游戏中了
        String isGamePlayer = isGamePlayer(deskList);
        if (null != isGamePlayer) {
            String jsonData = redisService.getCache(isGamePlayer);
            Game game = JSONObject.parseObject(jsonData, Game.class);
            //给断线重连的改玩家推送数据(如果庄家已经出来，则推送庄家，否则推送该玩家的抢庄倍数)
            for (int i = 0; i < game.getDesk().getSeats().size(); i++) {
                if (game.getDesk().getSeats().get(i).getPlayer() != null) {
                    if (game.getDesk().getSeats().get(i).getPlayer().getUserName().equals(user)) {
                        if (game.getDesk().getSeats().get(i).getIsExit().getValue() == 1) {
                            game.getDesk().getSeats().get(i).setIsConnect(Action.TRUE);
                            redisService.addCache(game.getDesk().getDeskNo(), JSONObject.toJSONString(game), Constants.REDIS_GAME_TIME_OUT);
                            PushObject push = new PushObject();
                            PushData pushData = new PushData();
                            push.setType(GamePushObject.ONE.getValue());
                            pushData.setDeskNo(game.getDesk().getDeskNo());
                            if (game.getDesk().getSeats().get(i).getCards() != null) {
                                pushData.setCards(game.getDesk().getSeats().get(i).getCards());
                            }
                            //判断是否有庄家
                            boolean isBanker = false;
                            for (int l = 0; l < game.getDesk().getSeats().size(); l++) {
                                if (game.getDesk().getSeats().get(l).getIsTheBanker().getValue() == 1) {
                                    isBanker = true;
                                    break;
                                }
                            }
                            if (isBanker) {
                                for (int j = 0; j < game.getDesk().getSeats().size(); j++) {
                                    if (game.getDesk().getSeats().get(j).getPlayer() != null) {
                                        if (game.getDesk().getSeats().get(j).getIsTheBanker().getValue() == 1) {
                                            //存在庄家，推送庄家的信息
                                            BankerPushObject bankerPushObject = new BankerPushObject();
                                            bankerPushObject.setSeatNo(game.getDesk().getSeats().get(j).getSeatNo());
                                            bankerPushObject.setBankerMultiple(game.getDesk().getSeats().get(j).getBankerMultiple());
                                            push.setBankerPushObject(bankerPushObject);
                                        }
                                    }
                                }
                            } else {
                                //推送该玩家的抢庄倍数
                                for (int l = 0; l < game.getDesk().getSeats().size(); l++) {
                                    if (null != game.getDesk().getSeats().get(l).getPlayer()) {
                                        if (game.getDesk().getSeats().get(l).getPlayer().getUserName().equals(user)) {
                                            //庄家出来了(不推送数据) 庄家还没有出来(推送该玩家的抢庄倍数)
                                            if (null != game.getDesk().getSeats().get(l).getIsRobBanker()) {
                                                RobBullfightPushObject robBullfightPushObject = new RobBullfightPushObject();
                                                robBullfightPushObject.setSeatNo(game.getDesk().getSeats().get(l).getSeatNo());
                                                robBullfightPushObject.setBankerMultiple(game.getDesk().getSeats().get(l).getBankerMultiple());
                                                push.setRobBullfightPushObject(robBullfightPushObject);
                                            }
                                        }
                                    }
                                }
                            }
                            //得到这场游戏中的所有的玩家
                            ArrayList<Player> players = new ArrayList<>();
                            for (int j = 0; j < game.getDesk().getSeats().size(); j++) {
                                if (game.getDesk().getSeats().get(j).getPlayer() != null) {
                                    players.add(game.getDesk().getSeats().get(j).getPlayer());
                                }
                            }
                            pushData.setPlayers(players);
                            List<BetPushObject> betPushObjects = new ArrayList<>();
                            //判断是否有人下注，将已经下注的玩家推送
                            for (int l = 0; l < game.getDesk().getSeats().size(); l++) {
                                if (game.getDesk().getSeats().get(l).getPlayer() != null) {
                                    if (game.getDesk().getSeats().get(l).getBetYesOrNo().getValue() == 1) {
                                        BetPushObject betPushObject = new BetPushObject();
                                        betPushObject.setMultiple(game.getDesk().getSeats().get(l).getMultiple());
                                        betPushObject.setSeatNo(game.getDesk().getSeats().get(l).getSeatNo());
                                        betPushObjects.add(betPushObject);
                                    }
                                }
                            }
                            push.setData(pushData);
                            push.setBetPushObjects(betPushObjects);
                            jsonData = redisService.getCache(isGamePlayer);
                            game = JSONObject.parseObject(jsonData, Game.class);
                            //给该断线重连的玩家推送数据
                            for (int l = 0; l < game.getDesk().getSeats().size(); l++) {
                                if (game.getDesk().getSeats().get(l).getPlayer() != null) {
                                    if (game.getDesk().getSeats().get(l).getPlayer().getUserName().equals(user)) {
                                        if (game.getDesk().getSeats().get(l).getIsConnect().getValue() == 1) {
                                            send(JSONObject.toJSONString(push), BullfightTcpService.userClients.get(game.getDesk().getSeats().get(l).getPlayer().getUserName()));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return;
        }
        Game game = null;
        if(baseScore == 10){
            deskList =  BullfightTcpService.deskList_10.get("bullfight_"+baseScore);
        }
        if(baseScore == 20){
            deskList =  BullfightTcpService.deskList_20.get("bullfight_"+baseScore);
        }
        if(baseScore == 30){
            deskList =  BullfightTcpService.deskList_30.get("bullfight_"+baseScore);
        }
        if(baseScore == 40){
            deskList =  BullfightTcpService.deskList_40.get("bullfight_"+baseScore);
        }
        if(deskList != null){
            for (String deskNo : deskList) {
                if (!result) {
                    break;
                }
                String jsonData = redisService.getCache(deskNo);
                game = JSONObject.parseObject(jsonData, Game.class);
                //有空位  加入这一桌
                if (null != game && game.getDesk().getBaseScore() == baseScore) {
                    for (int i = 0; i < game.getDesk().getSeats().size(); i++) {
                        if (null == game.getDesk().getSeats().get(i).getPlayer()) {
                            if (!result) {
                                break;
                            }
                            Player player = new Player(userAppService.searchByUserName(user), game.getDesk().getSeats().get(i));
                            game.getDesk().getSeats().get(i).setPlayer(player);
                            game.getDesk().getSeats().get(i).setIsExit(Action.TRUE);
                            game.getDesk().getSeats().get(i).setIsConnect(Action.TRUE);
                            redisService.addCache(game.getDesk().getDeskNo(), JSONObject.toJSONString(game), Constants.REDIS_GAME_TIME_OUT);
                            result = false;
                        }
                    }
                }
            }
        }

        //当桌子已经没有空位置时，创建一张桌子
        if (result) {
            List<ApiUserRepresentation> userList = new ArrayList<>();
            userList.add(userAppService.searchByUserName(user));
            game = new Game(userList, baseScore, deskNo());
            List<String> newCreateDeskNo = new ArrayList<>();
            newCreateDeskNo.add(game.getDesk().getDeskNo());
            if (baseScore == 10) {
                BullfightTcpService.deskList_10.put("bullfight_10", newCreateDeskNo);
            }
            if (baseScore == 20) {
                BullfightTcpService.deskList_20.put("bullfight_20", newCreateDeskNo);
            }
            if (baseScore == 30) {
                BullfightTcpService.deskList_30.put("bullfight_30", newCreateDeskNo);
            }
            if (baseScore == 40) {
                BullfightTcpService.deskList_40.put("bullfight_40", newCreateDeskNo);
            }
            redisService.addCache(game.getDesk().getDeskNo(), JSONObject.toJSONString(game), Constants.REDIS_GAME_TIME_OUT);
        }
        String jsonData = redisService.getCache(game.getDesk().getDeskNo());
        game = JSONObject.parseObject(jsonData, Game.class);
        //得到这场游戏的人数，人数>2 则可以开始游戏
        int count = 0;
        for (int j = 0; j < game.getDesk().getSeats().size(); j++) {
            if (game.getDesk().getSeats().get(j).getIsExit() != null) {
                if (game.getDesk().getSeats().get(j).getIsExit().getValue() == 1) {
                    count++;
                }
            }
        }
        if (count == 3) {
            new PairGameThread(redisService, game.getDesk().getDeskNo(), bullfightRecordAppService).start();
        }
        if (count > 3) {
            int c = 0;
            for (int i = 0; i < game.getDesk().getSeats().size(); i++) {
                if (game.getDesk().getSeats().get(i).getIsOldOrNew() != null) {
                    c++;
                }
            }
            //判断该玩家进入时，是否已经开始游戏
            if (count == c + 1) {
                boolean b = false;
                for (int i = 0; i < game.getDesk().getSeats().size(); i++) {
                    if (game.getDesk().getSeats().get(i).getIsOldOrNew() != null) {
                        if (game.getDesk().getSeats().get(i).getCards() == null) {
                            b = true;
                            break;
                        }
                    }
                }
                //当该玩家进入时，该桌正在游戏，则该玩家作为旁观的存在
                if (!b) {
                    for (int j = 0; j < game.getDesk().getSeats().size(); j++) {
                        if (game.getDesk().getSeats().get(j).getIsExit().getValue() == 1) {
                            if (game.getDesk().getSeats().get(j).getIsConnect().getValue() == 1) {
                                PushObject push = new PushObject();
                                PushData pushData = new PushData();

                                ArrayList<Player> players = new ArrayList<>();
                                for (int i = 0; i < game.getDesk().getSeats().size(); i++) {
                                    if (game.getDesk().getSeats().get(i).getIsExit().getValue() == 1) {
                                        players.add(game.getDesk().getSeats().get(i).getPlayer());
                                    }
                                }
                                pushData.setPlayers(players);
                                pushData.setDeskNo(game.getDesk().getDeskNo());
                                push.setType(GamePushObject.EIGHT.getValue());//有人进入或离开桌
                                push.setData(pushData);//将pushData放进push
                                send(JSONObject.toJSONString(push), BullfightTcpService.userClients.get(game.getDesk().getSeats().get(j).getPlayer().getUserName()));//推送给其他人
                            }
                        }
                    }
                }
                //该玩家进入时，还没有开始游戏，则该玩家加入游戏
                if (b) {
                    new PairGameThread(redisService, game.getDesk().getDeskNo(), bullfightRecordAppService).start();
                }
            }
        }
    }

    /**
     * 托管
     * 重复方法提取
     */
    private void collocation(String deskNo) {
        if (null != deskNo) {
            String jsonData = redisService.getCache(deskNo);
            Game game = JSONObject.parseObject(jsonData, Game.class);
            //将掉线玩家的连接状态设为false
            game.getDesk().getSeats().stream().filter(seat -> seat.getPlayer() != null).filter(seat -> seat.getPlayer().getUserName().equals(user)).forEach(seat -> {
                seat.setIsConnect(Action.FALSE);
                redisService.addCache(game.getDesk().getDeskNo(), JSONObject.toJSONString(game), Constants.REDIS_GAME_TIME_OUT);
            });
        }
    }

    /**
     * 得到这场游戏的玩家的人数
     */
    public int isExistPlayer(RedisService redisService, String deskNo) {
        String jsonData = redisService.getCache(deskNo);
        Game game = JSONObject.parseObject(jsonData, Game.class);
        int isExistCount = 0;
        for (Seat seat : game.getDesk().getSeats()) {
            if (null != seat.getPlayer()) {
                isExistCount++;
            }
        }
        return isExistCount;
    }

    /**
     * 得到这场游戏的正在玩的玩家的人数
     */
    public int currentCount(RedisService redisService, String deskNo) {
        String jsonData = redisService.getCache(deskNo);
        Game game = JSONObject.parseObject(jsonData, Game.class);
        int currentCount = 0;
        for (Seat seat : game.getDesk().getSeats()) {
            if (null != seat.getPlayer()) {
                if (null != seat.getIsPlaying()) {
                    if (seat.getIsPlaying().getValue() == 1) {
                        currentCount++;
                    }
                }
            }
        }
        return currentCount;
    }

    /**
     * 配桌完成推送数据
     */
    public void completeWithTable(Game game) {
        for (int j = 0; j < game.getDesk().getSeats().size(); j++) {
            if (game.getDesk().getSeats().get(j).getPlayer() != null) {
                if (game.getDesk().getSeats().get(j).getIsExit().getValue() == 1) {
                    PushObject push = new PushObject();
                    PushData pushData = new PushData();
                    ArrayList<Player> players = new ArrayList<>();
                    for (int i = 0; i < game.getDesk().getSeats().size(); i++) {
                        if (game.getDesk().getSeats().get(i).getIsExit().getValue() == 1) {
                            players.add(game.getDesk().getSeats().get(i).getPlayer());
                        }
                    }
                    pushData.setPlayers(players);
                    pushData.setDeskNo(game.getDesk().getDeskNo());
                    push.setType(GamePushObject.THREE.getValue());//有人进入或离开桌
                    push.setData(pushData);//将pushData放进push
                    if (game.getDesk().getSeats().get(j).getIsConnect().getValue() == 1) {
                        send(JSONObject.toJSONString(push), BullfightTcpService.userClients.get(game.getDesk().getSeats().get(j).getPlayer().getUserName()));//推送给其他人
                    }
                }
            }
        }
    }
}

