package chess.game.landlords.listener;

import chess.application.gamemultiple.IGameMultipleAppService;
import chess.application.gamemultiple.command.ListGameMultipleCommand;
import chess.application.gamerecord.IGameRecordAppService;
import chess.application.user.IApiUserAppService;
import chess.application.user.representation.ApiUserRepresentation;
import chess.core.enums.GameType;
import chess.core.redis.RedisService;
import chess.domain.model.gamemultiple.GameMultiple;
import chess.game.landlords.command.GamePushType;
import chess.game.landlords.command.PushObject;
import chess.game.landlords.command.ReceiveObject;
import chess.game.landlords.function.CallLandlordsTimeOut;
import chess.game.landlords.function.DoubleTimeOut;
import chess.game.landlords.function.PlayTimeOut;
import chess.game.landlords.mode.*;
import chess.game.landlords.mode.push.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created by YJH
 * Date : 2016/3/25.
 */
public class LandlordsClient implements Runnable {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private Socket s;
    private String user;
    private DataInputStream dis = null;
    private DataOutputStream dos = null;
    private String deskNo;
    private boolean isConnection;

    private IApiUserAppService apiUserAppService;
    private RedisService redisService;
    private IGameRecordAppService gameRecordAppService;
    private IGameMultipleAppService gameMultipleAppService;

    public RedisService getRedisService() {
        return redisService;
    }

    LandlordsClient(Socket s, RedisService redisService, IApiUserAppService apiUserAppService, IGameRecordAppService gameRecordAppService,
                    IGameMultipleAppService gameMultipleAppService) {
        this.s = s;
        try {
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());
            this.apiUserAppService = apiUserAppService;
            this.redisService = redisService;
            this.gameRecordAppService = gameRecordAppService;
            this.gameMultipleAppService = gameMultipleAppService;
            this.isConnection = true;
        } catch (EOFException e) {
            logger.info("socket.shutdown.message");
            close();
        } catch (IOException e) {
            logger.info("socket.connection.fail.message");
            close();
        }
    }

    public boolean send(String str, LandlordsClient client) {
        try {
            if (null != client) {
                client.dos.writeUTF(str.replace(" ", "").replace("\n", "").replace("\t", ""));
                System.out.println("给[" + client.user + "]发送数据------" + str);
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
            abnormalExit();
            if (dis != null)
                dis.close();
            if (dos != null)
                dos.close();
            if (s != null) {
                s.close();
            }
            LandlordsTcpService.userClients.remove(user);
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (isConnection) {
                String str = dis.readUTF();
                System.out.println("接收到[" + user + "]数据------" + str);
                System.out.println("桌号" + deskNo);
                ReceiveObject obj = JSON.parseObject(str, ReceiveObject.class);
                int type = obj.getType();
                switch (type) {
                    case 20001://配桌
                        //加入玩家数据
                        JSONObject jsonObject = JSON.parseObject(obj.getData());
                        user = jsonObject.getString("user");
                        LandlordsTcpService.userClients.put(user, this);
                        pairGame(obj);
                        break;
                    case 20002://离开桌
                        leave();
                        break;
                    case 20003://叫地主
                        callLandlord(obj);
                        break;
//                    case 20004://抢地主
//                        robLandlord(obj);
//                        break;
                    case 20005://加倍
                        doubleAction(obj);
                        break;
                    case 20006://出牌
                        playCard(obj);
                        break;
                    case 20007://托管
                        robot(obj);
                        break;
//                    case 20008://继续游戏
//                        continueGame();
//                        break;
                    case 20009://获取上一轮牌
                        Game game = LandlordsTcpService.games.get(deskNo);
                        if (null != game.getDesk().getLastRoundCards() && game.getDesk().getGameStatus() == GameStatus.PLAY) {
                            PushLastRoundCards pushData = new PushLastRoundCards();

                            if (4 > game.getDesk().getLastRoundCards().size()) {
                                pushData.setLastRoundCards(new ArrayList<>());
                            } else if (7 > game.getDesk().getLastRoundCards().size()) {
                                pushData.setLastRoundCards(game.getDesk().getLastRoundCards().subList(0, game.getDesk().getLastRoundCards().size() - 3));
                            } else {
                                pushData.setLastRoundCards(game.getDesk().getLastRoundCards().subList(game.getDesk().getLastRoundCards().size() - 6, game.getDesk().getLastRoundCards().size() - 3));
                            }


                            PushObject push = new PushObject();
                            push.setData(pushData);
                            push.setType(GamePushType.GAME_21017.getValue());
                            send(toJSONString(push), LandlordsTcpService.userClients.get(user));
                        }
                        break;
                }
            }
        } catch (EOFException e) {
            logger.info("socket.shutdown.message");
        } catch (IOException e) {
            logger.info("socket.dirty.shutdown.message");
        } finally {
            close();
        }
    }

    /**
     * 继续游戏
     */
    private void continueGame() {
        Game game = LandlordsTcpService.games.get(deskNo);
        game.getDesk().getSeats().stream().filter(seat -> null != seat.getPlayer() && seat.getPlayer().getUserName().equals(user)).forEach(seat -> seat.setIsReady(Action.TRUE));

        int readyCount = 0;
        for (Seat seat : game.getDesk().getSeats()) {
            if (seat.getIsReady() == Action.TRUE) {
                readyCount++;
            }
        }

        //开始游戏
        if (readyCount == 3) {
            //发牌
            game.dealCard();
            game.initLastRoundCard();
            game.getDesk().setNextPlayer(game.getDesk().getRandomLandlordPlayer());
            for (Seat send_seat : game.getDesk().getSeats()) {
                PushObject push = new PushObject(GamePushType.GAME_21001.getValue(), new PushStartLandlords(game, send_seat.getPlayer().getUserName()), "success");
                send(this.toJSONString(push), LandlordsTcpService.userClients.get(send_seat.getPlayer().getUserName()));
            }

            new CallLandlordsTimeOut(this, deskNo, game.getDesk().getNextPlayer(), game.getDesk().getLandlordsDate()).start();
        }
    }

    /**
     * 玩家异常退出
     */
    private void abnormalExit() {
        System.out.println("-------------------------玩家" + user + "掉线离开");
        if (null == deskNo) {
            return;
        }
        //移除玩家
        Game game = LandlordsTcpService.games.get(deskNo);
        if (null != game) {
            //游戏已经开始
            if (null != game.getDesk().getRandomLandlordPlayer() || (game.getDesk().getGameStatus() != GameStatus.OVER && game.getDesk().getGameStatus() != null)) {
                for (Seat seat : game.getDesk().getSeats()) {
                    if (null != seat.getPlayer() && seat.getPlayer().getUserName().equals(user)) {
                        seat.setIsOffLine(Action.TRUE);
                        seat.setIsRobot(Action.TRUE);
                        break;
                    }
                }

                if (!user.startsWith("robot")) {
                    //托管
                    game.getDesk().getSeats().stream().filter(seat -> Action.TRUE != seat.getIsOffLine() && null != seat.getPlayer()).forEach(seat -> {
                        PushObject push = new PushObject();
                        push.setType(GamePushType.GAME_21008.getValue());//托管
                        push.setData(new PushRobot(user, Action.TRUE));
                        send(this.toJSONString(push), LandlordsTcpService.userClients.get(seat.getPlayer().getUserName()));
                    });

                }

//                    if (user.equals(game.getDesk().getNextPlayer())) {
//                        game.getDesk().getSeats().stream().filter(seat -> seat.getPlayer().getUserName().equals(user))
//                                .forEach(seat -> robotPlay(seat.getSeatNo()));
//                    }

                final int[] playerCount = {0};
                game.getDesk().getSeats().stream().filter(seat -> seat.getIsOffLine() == Action.TRUE && seat.getIsRobot() == Action.TRUE
                        && !seat.getPlayer().getUserName().startsWith("robot"))
                        .forEach(seat -> playerCount[0]++);

                //如果该桌三个人都掉线了  删除该桌
                if (playerCount[0] == 3) {
//                    redisService.delete(game.getDesk().getDeskNo());
                    List<String> deskNoList = new ArrayList<>();
                    deskNoList.addAll(LandlordsTcpService.deskList.get("landlords" + game.getDesk().getBaseScore()));
                    for (String temp_deskNo : deskNoList) {
                        if (temp_deskNo.equals(game.getDesk().getDeskNo())) {
                            deskNoList.remove(temp_deskNo);
                            break;
                        }
                    }
                    LandlordsTcpService.deskList.put("landlords" + game.getDesk().getBaseScore(), deskNoList);
                    LandlordsTcpService.games.remove(game.getDesk().getDeskNo());
                }
            } else {
                for (Seat seat : game.getDesk().getSeats()) {
                    if (null != seat.getPlayer() && seat.getPlayer().getUserName().equals(user)) {
                        seat.setIsReady(Action.NONE);
                        seat.setIsJoin(Action.TRUE);
                        seat.setIsDouble(Action.NONE);
                        seat.setCallLandlordScore(0);
                        seat.setIsCallLandlord(Action.NONE);
                        seat.setIsOffLine(Action.FALSE);
                        seat.setIsEscape(Action.FALSE);
                        seat.setCards(null);
                        seat.setCardsSize(0);
                        seat.setPlayer(null);
                        break;
                    }
                }

                int playerCount = 0;
                for (Seat seat : game.getDesk().getSeats()) {
                    if (null != seat.getPlayer()) {
                        playerCount++;
                    }
                }

                //如果该桌没人了  删除该桌
                if (playerCount == 0) {
                    List<String> deskNoList = new ArrayList<>();
                    deskNoList.addAll(LandlordsTcpService.deskList.get("landlords" + game.getDesk().getBaseScore()));
                    for (String temp_deskNo : deskNoList) {
                        if (temp_deskNo.equals(game.getDesk().getDeskNo())) {
                            deskNoList.remove(temp_deskNo);
                            break;
                        }
                    }
                    LandlordsTcpService.deskList.put("landlords" + game.getDesk().getBaseScore(), deskNoList);
                    LandlordsTcpService.games.remove(game.getDesk().getDeskNo());
                }
            }
        }
    }

    /**
     * 游戏配桌
     */
    private void pairGame(ReceiveObject obj) {
        JSONObject data = JSON.parseObject(obj.getData());
        BigDecimal baseScore = data.getBigDecimal("baseScore");

        ListGameMultipleCommand command = new ListGameMultipleCommand();
        command.setGameType(GameType.LANDLORDS);
        command.setMultiple(baseScore);
        List<GameMultiple> gameMultiples = gameMultipleAppService.list(command);
        if (baseScore.compareTo(new BigDecimal(0)) == 0 || null == gameMultiples || gameMultiples.size() == 0) {
            System.out.println(baseScore + "倍数场不存在!");
            return;
        }

        ApiUserRepresentation userRepresentation = apiUserAppService.searchByUserName(this.user);
        if (null == userRepresentation || userRepresentation.getMoney().compareTo(gameMultiples.get(0).getMinMoney()) < 0) {
            System.out.println("积分不够");

            PushObject push = new PushObject();
            push.setType(GamePushType.GAME_21013.getValue());//积分不够
            push.setCode("fail");
            send(this.toJSONString(push), LandlordsTcpService.userClients.get(user));
            return;
        }

        //已经在游戏中了
        String isGamePlayer = isGamePlayer();
        if (null != isGamePlayer && LandlordsTcpService.games.containsKey(isGamePlayer)) {
            System.out.println(user + " ------已经在游戏中了");
            synchronized (LandlordsTcpService.games.get(isGamePlayer)) {
                Game game = LandlordsTcpService.games.get(isGamePlayer);

                game.getDesk().getSeats().stream().filter(seat -> null != seat.getPlayer() && seat.getPlayer().getUserName().equals(user)).forEach(seat -> {
                    seat.setIsEscape(Action.FALSE);
                    seat.setIsRobot(Action.FALSE);
                    seat.setIsOffLine(Action.FALSE);
                    seat.setIsRobot(Action.FALSE);
                });

                this.deskNo = game.getDesk().getDeskNo();

                PushReconnectLandlords pushData = new PushReconnectLandlords(game, user);
                PushObject pushObject = new PushObject(GamePushType.GAME_21010.getValue(), pushData, "success");
                send(this.toJSONString(pushObject), LandlordsTcpService.userClients.get(user));

                for (Seat seat : game.getDesk().getSeats()) {
                    if (null != seat.getPlayer() && !seat.getPlayer().getUserName().startsWith("robot") && !seat.getPlayer().getUserName().equals(user)) {
                        pushObject = new PushObject(GamePushType.GAME_21008.getValue(), new PushRobot(user, Action.FALSE), "success");
                        send(this.toJSONString(pushObject), LandlordsTcpService.userClients.get(seat.getPlayer().getUserName()));
                    }
                }
            }

            return;
        }

        //读取桌数据
        synchronized (LandlordsTcpService.deskList) {
            List<String> deskList = new ArrayList<>();
            if (null != LandlordsTcpService.deskList.get("landlords" + gameMultiples.get(0).getMultiple())) {
                deskList.addAll(LandlordsTcpService.deskList.get("landlords" + gameMultiples.get(0).getMultiple()));
            }
            boolean result = true;

            for (String deskNo : deskList) {
                Game game = LandlordsTcpService.games.get(deskNo);

                if (null != game) {
                    int robot = 0;
                    if (user.startsWith("robot")) {
                        for (Seat seat : game.getDesk().getSeats()) {
                            if (null != seat && null != seat.getPlayer() && seat.getPlayer().getUserName().startsWith("robot")) {
                                robot++;
                            }
                        }
                    }

                    if (robot < 2) {
                        //有空位  加入这一桌
                        for (Seat seat : game.getDesk().getSeats()) {
                            if (null == seat.getPlayer()) {

                                Player player = new Player(userRepresentation);
                                seat.setPlayer(player);
                                seat.setIsJoin(Action.TRUE);
                                seat.setIsReady(Action.TRUE);

                                this.deskNo = game.getDesk().getDeskNo();//桌号

                                int playerSize = 0;
                                for (Seat seat_item : game.getDesk().getSeats()) {
                                    if (null != seat_item.getPlayer() && seat_item.getIsReady() == Action.TRUE) {
                                        playerSize++;
                                    }
                                }

                                //如果有三个人了 就开始游戏
                                if (playerSize == 3) {
                                    //发牌
                                    game.dealCard();
                                    game.initLastRoundCard();
                                    game.getDesk().setGameStatus(GameStatus.CALL);
                                    game.getDesk().setNextPlayer(game.getDesk().getRandomLandlordPlayer());
                                    for (Seat send_seat : game.getDesk().getSeats()) {
                                        PushObject push = new PushObject(GamePushType.GAME_21001.getValue(), new PushStartLandlords(game, send_seat.getPlayer().getUserName()), "success");
                                        send(this.toJSONString(push), LandlordsTcpService.userClients.get(send_seat.getPlayer().getUserName()));
                                    }

                                    new CallLandlordsTimeOut(this, deskNo, game.getDesk().getNextPlayer(), game.getDesk().getLandlordsDate()).start();
                                    return;
                                }

                                result = false;
                                break;
                            }
                        }
                    }
                }

            }

            //如果没有找到空位 创建一桌
            if (result) {
                List<ApiUserRepresentation> userList = new ArrayList<>();
                userList.add(apiUserAppService.searchByUserName(this.user));
                Game game = new Game(userList, deskNo(), gameMultiples.get(0).getMultiple());
                deskList.add(game.getDesk().getDeskNo());
                LandlordsTcpService.deskList.put("landlords" + gameMultiples.get(0).getMultiple(), deskList);

                this.deskNo = game.getDesk().getDeskNo();//桌号

                LandlordsTcpService.games.put(deskNo, game);
            }
        }
    }


    /**
     * 离开
     */
    private void leave() {
        if (!LandlordsTcpService.games.containsKey(deskNo)) {
            return;
        }
        synchronized (LandlordsTcpService.games.get(deskNo)) {
            Game game = LandlordsTcpService.games.get(deskNo);
            if (null != game) {
                if (game.getDesk().getGameStatus() == GameStatus.OVER) {
                    for (Seat seat : game.getDesk().getSeats()) {
                        if (seat.getPlayer().getUserName().equals(user)) {
                            Seat leave_seat = seat;
                            seat.setPlayer(null);
                            seat.setIsReady(Action.NONE);
                            seat.setIsJoin(Action.NONE);
                            seat.setIsDouble(Action.NONE);
                            seat.setCallLandlordScore(0);
                            seat.setIsCallLandlord(Action.NONE);
                            seat.setIsOffLine(Action.FALSE);
                            seat.setIsEscape(Action.FALSE);
                            seat.setCards(null);
                            seat.setCardsSize(0);

                            //推送离开数据
                            game.getDesk().getSeats().stream().filter(send_seat -> null != send_seat.getPlayer()).forEach(send_seat -> {
                                PushObject push = new PushObject(GamePushType.GAME_21102.getValue(), leave_seat, "success");
                                send(this.toJSONString(push), LandlordsTcpService.userClients.get(send_seat.getPlayer().getUserName()));
                            });
                            break;
                        }
                    }
                } else {
                    for (Seat seat : game.getDesk().getSeats()) {
                        if (seat.getPlayer().getUserName().equals(user)) {
                            //更改状态为逃跑
                            seat.setIsEscape(Action.TRUE);
                            seat.setIsRobot(Action.TRUE);
                            seat.setIsOffLine(Action.TRUE);
                            seat.setIsRobot(Action.TRUE);

                            //推送逃跑数据
                            game.getDesk().getSeats().stream().filter(send_seat -> null != send_seat.getPlayer()
                                    && !send_seat.getPlayer().getUserName().equals(user)).forEach(send_seat -> {
                                PushObject push = new PushObject(GamePushType.GAME_21002.getValue(), seat, "success");
                                send(this.toJSONString(push), LandlordsTcpService.userClients.get(send_seat.getPlayer().getUserName()));
                            });
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * 叫地主
     *
     * @param obj 接收数据
     */
    private void callLandlord(ReceiveObject obj) {
        JSONObject data = JSON.parseObject(obj.getData());
        int callLandlordScore = data.getIntValue("callLandlordScore");

        Game game = LandlordsTcpService.games.get(deskNo);

        if (null == game) {
            return;
        }
        if (!game.getDesk().getNextPlayer().equals(user)) {
            System.out.println("不该你操作" + user);

            PushObject push = new PushObject();
            push.setType(GamePushType.GAME_21014.getValue());//不该当前操作
            push.setCode("fail");
            send(this.toJSONString(push), LandlordsTcpService.userClients.get(user));

            return;
        }

        //更改叫地主状态
        for (Seat seat : game.getDesk().getSeats()) {
            if (seat.getPlayer().getUserName().equals(user)) {
                if (Action.NONE != seat.getIsCallLandlord()) {
                    System.out.println("已经操作过了" + user);

                    PushObject push = new PushObject();
                    push.setType(GamePushType.GAME_21015.getValue());//已经操作过了
                    push.setCode("fail");
                    send(this.toJSONString(push), LandlordsTcpService.userClients.get(user));

                    return;
                }
                if (callLandlordScore != 0) {
                    seat.setIsCallLandlord(Action.TRUE);
                    seat.setCallLandlordScore(callLandlordScore);
                } else {
                    seat.setIsCallLandlord(Action.FALSE);
                }
                break;
            }
        }

        //推送叫地主状态
        game.getDesk().getSeats().forEach(seat -> {
            PushObject push = new PushObject(GamePushType.GAME_21003.getValue(), new PushCallLandlords(game, user), "success");
            send(this.toJSONString(push), LandlordsTcpService.userClients.get(seat.getPlayer().getUserName()));
        });

        //if (callLandlord.equals("true")) {
        //   game.getDesk().setGameStatus(GameStatus.ROB);
        //}
        //}

        game.getDesk().setNextPlayer(getNextPlayer(game, user));

        if (callLandlordScore == 3) {
            game.getDesk().getSeats().stream().filter(seat -> seat.getPlayer().getUserName().equals(user)).forEach(seat -> {
                game.getDesk().setLandlordPlayer(seat.getPlayer().getUserName());//确认地主
                seat.getCards().addAll(game.getDesk().getLandlordCard());//分地主牌
                seat.setCards(Game.sort(seat.getCards()));//排序

                game.getDesk().setCallLandlordScore(3);
                game.getDesk().setNextPlayer(game.getDesk().getLandlordPlayer());
                game.getDesk().setGameStatus(GameStatus.DOUBLE);

                //推送确认地主
                for (Seat send_seat : game.getDesk().getSeats()) {
                    PushConfirmLandlords send_data = new PushConfirmLandlords(game, send_seat.getPlayer().getUserName());
                    PushObject push = new PushObject(GamePushType.GAME_21005.getValue(), send_data, "success");
                    send(this.toJSONString(push), LandlordsTcpService.userClients.get(send_seat.getPlayer().getUserName()));
                }

                new DoubleTimeOut(this, deskNo).start();
            });
            return;
        }

        //如果三个玩家都不叫地主  洗牌重来
        int callLandlordCount = 0;
        for (int i = 0; i < game.getDesk().getSeats().size(); i++) {
            Seat seat = game.getDesk().getSeats().get(i);
            if (seat.getIsCallLandlord() != Action.NONE && seat.getCallLandlordScore() == 0) {
                callLandlordCount++;
            }
        }

        if (callLandlordCount == 3) {
            game.dealCard();
            game.initLastRoundCard();
            for (Seat seat : game.getDesk().getSeats()) {
                game.getDesk().setLandlordsDate(new Date());
                seat.setIsCallLandlord(Action.NONE);
            }
            game.getDesk().setNextPlayer(game.getDesk().getRandomLandlordPlayer());
            for (Seat seat : game.getDesk().getSeats()) {
                PushObject push = new PushObject();
                push.setType(GamePushType.GAME_21009.getValue());//没人叫地主洗牌重来
                push.setData(new PushStartLandlords(game, seat.getPlayer().getUserName()));
                send(this.toJSONString(push), LandlordsTcpService.userClients.get(seat.getPlayer().getUserName()));
            }

            new CallLandlordsTimeOut(this, game.getDesk().getDeskNo(), game.getDesk().getRandomLandlordPlayer(), game.getDesk().getLandlordsDate()).start();
            return;
        }

        //三个玩家都操作叫地主了
        callLandlordCount = 0;
        for (int i = 0; i < game.getDesk().getSeats().size(); i++) {
            Seat seat = game.getDesk().getSeats().get(i);
            if (seat.getIsCallLandlord() != Action.NONE) {
                callLandlordCount++;
            }
        }

        if (callLandlordCount == 3) {
            Seat seat_1 = game.getDesk().getSeats().get(0);
            Seat seat_2 = game.getDesk().getSeats().get(1);
            Seat seat_3 = game.getDesk().getSeats().get(2);

            if (seat_1.getCallLandlordScore() > seat_2.getCallLandlordScore() && seat_1.getCallLandlordScore() > seat_3.getCallLandlordScore()) {
                game.getDesk().getSeats().stream().filter(seat -> seat.getPlayer().getUserName().equals(seat_1.getPlayer().getUserName())).forEach(seat -> {
                    game.getDesk().setLandlordPlayer(seat.getPlayer().getUserName());//确认地主
                    seat.getCards().addAll(game.getDesk().getLandlordCard());//分地主牌
                    seat.setCards(Game.sort(seat.getCards()));//排序

                    game.getDesk().setCallLandlordScore(seat.getCallLandlordScore());
                    game.getDesk().setNextPlayer(game.getDesk().getLandlordPlayer());
                    game.getDesk().setGameStatus(GameStatus.DOUBLE);

                    //推送确认地主
                    for (Seat send_seat : game.getDesk().getSeats()) {
                        PushConfirmLandlords send_data = new PushConfirmLandlords(game, send_seat.getPlayer().getUserName());
                        PushObject push = new PushObject(GamePushType.GAME_21005.getValue(), send_data, "success");
                        send(this.toJSONString(push), LandlordsTcpService.userClients.get(send_seat.getPlayer().getUserName()));
                    }

                    new DoubleTimeOut(this, deskNo).start();
                });
                return;
            }

            if (seat_2.getCallLandlordScore() > seat_1.getCallLandlordScore() && seat_2.getCallLandlordScore() > seat_3.getCallLandlordScore()) {
                game.getDesk().getSeats().stream().filter(seat -> seat.getPlayer().getUserName().equals(seat_2.getPlayer().getUserName())).forEach(seat -> {
                    game.getDesk().setLandlordPlayer(seat.getPlayer().getUserName());//确认地主
                    seat.getCards().addAll(game.getDesk().getLandlordCard());//分地主牌
                    seat.setCards(Game.sort(seat.getCards()));//排序

                    game.getDesk().setCallLandlordScore(seat.getCallLandlordScore());
                    game.getDesk().setNextPlayer(game.getDesk().getLandlordPlayer());
                    game.getDesk().setGameStatus(GameStatus.DOUBLE);

                    //推送确认地主
                    for (Seat send_seat : game.getDesk().getSeats()) {
                        PushConfirmLandlords send_data = new PushConfirmLandlords(game, send_seat.getPlayer().getUserName());
                        PushObject push = new PushObject(GamePushType.GAME_21005.getValue(), send_data, "success");
                        send(this.toJSONString(push), LandlordsTcpService.userClients.get(send_seat.getPlayer().getUserName()));
                    }

                    new DoubleTimeOut(this, deskNo).start();
                });
                return;
            }

            if (seat_3.getCallLandlordScore() > seat_1.getCallLandlordScore() && seat_3.getCallLandlordScore() > seat_2.getCallLandlordScore()) {
                game.getDesk().getSeats().stream().filter(seat -> seat.getPlayer().getUserName().equals(seat_3.getPlayer().getUserName())).forEach(seat -> {
                    game.getDesk().setLandlordPlayer(seat.getPlayer().getUserName());//确认地主
                    seat.getCards().addAll(game.getDesk().getLandlordCard());//分地主牌
                    seat.setCards(Game.sort(seat.getCards()));//排序

                    game.getDesk().setCallLandlordScore(seat.getCallLandlordScore());
                    game.getDesk().setNextPlayer(game.getDesk().getLandlordPlayer());
                    game.getDesk().setGameStatus(GameStatus.DOUBLE);

                    //推送确认地主
                    for (Seat send_seat : game.getDesk().getSeats()) {
                        PushConfirmLandlords send_data = new PushConfirmLandlords(game, send_seat.getPlayer().getUserName());
                        PushObject push = new PushObject(GamePushType.GAME_21005.getValue(), send_data, "success");
                        send(this.toJSONString(push), LandlordsTcpService.userClients.get(send_seat.getPlayer().getUserName()));
                    }

                    new DoubleTimeOut(this, deskNo).start();
                });
                return;
            }
        }

        new CallLandlordsTimeOut(this, game.getDesk().getDeskNo(), getNextPlayer(game, user), game.getDesk().getLandlordsDate()).start();
    }

    /**
     * 加倍操作
     *
     * @param obj 接收数据
     */
    private void doubleAction(ReceiveObject obj) {
        JSONObject data = JSON.parseObject(obj.getData());
        String isDouble = data.getString("isDouble");
        Game game = LandlordsTcpService.games.get(deskNo);
        if (game.getDesk().getGameStatus() != GameStatus.DOUBLE) {
            System.out.println("已近过了加倍阶段" + user);

            PushObject push = new PushObject();
            push.setType(GamePushType.GAME_21016.getValue());//错误的操作
            push.setCode("fail");
            send(this.toJSONString(push), LandlordsTcpService.userClients.get(user));

            return;
        }
        game.getDesk().getSeats().stream().filter(seat -> seat.getPlayer().getUserName().equals(user)).forEach(seat -> {
            if (seat.getIsDouble() != Action.NONE) {
                System.out.println("已近操作过了" + user);

                PushObject push = new PushObject();
                push.setType(GamePushType.GAME_21015.getValue());//已近操作过了
                push.setCode("fail");
                send(this.toJSONString(push), LandlordsTcpService.userClients.get(user));

                return;
            }

            if (isDouble.equals("true")) {
                seat.setIsDouble(Action.TRUE);
            } else {
                seat.setIsDouble(Action.FALSE);
            }
        });
        game.getDesk().setGameStatus(GameStatus.DOUBLE);

        //推送加倍结果
        game.getDesk().getSeats().forEach(seat -> {
            PushObject push = new PushObject();
            push.setType(GamePushType.GAME_21006.getValue());
            if (isDouble.equals("true")) {
                push.setData(new PushDoubleLandlords(game, user, Action.TRUE));
            } else {
                push.setData(new PushDoubleLandlords(game, user, Action.FALSE));
            }
            send(this.toJSONString(push), LandlordsTcpService.userClients.get(seat.getPlayer().getUserName()));
        });

        //统计玩家加过倍的人数
        int isDoubleCount = 0;
        for (int i = 0; i < game.getDesk().getSeats().size(); i++) {
            Seat seat = game.getDesk().getSeats().get(i);
            if (Action.NONE != seat.getIsDouble()) {
                isDoubleCount++;
            }
        }
        if (isDoubleCount == 3) {
            //三位玩家都加倍了  开始游戏
            game.getDesk().setGameStatus(GameStatus.PLAY);//出牌阶段
            PastCard pastCard = new PastCard();
            pastCard.setNextPlayer(game.getDesk().getLandlordPlayer());
            game.getDesk().setPastCard(pastCard);

            game.getDesk().setNextPlayer(game.getDesk().getLandlordPlayer());
            game.getDesk().setGameStatus(GameStatus.PLAY);

            for (Seat seat : game.getDesk().getSeats()) {
                PushObject push = new PushObject();
                push.setType(GamePushType.GAME_21007.getValue());//出牌
                push.setData(new PushPlayLandlords(game, seat.getPlayer().getUserName()));
                send(this.toJSONString(push), LandlordsTcpService.userClients.get(seat.getPlayer().getUserName()));
            }

            new PlayTimeOut(this, deskNo, game.getDesk().getNextPlayer(), game.getDesk().getPastCard().getIndex()).start();
        }
    }

    /**
     * 出牌
     *
     * @param obj 接受数据
     */
    private void playCard(ReceiveObject obj) {
        JSONObject data = JSONObject.parseObject(obj.getData());
        List<Card> playCard = JSONArray.parseArray(data.getString("playCard"), Card.class);
        if (!LandlordsTcpService.games.containsKey(deskNo)) {
            return;
        }
        synchronized (LandlordsTcpService.games.get(deskNo)) {
            Game game = LandlordsTcpService.games.get(deskNo);

            if (!game.getDesk().getNextPlayer().equals(user)) {
                System.out.println("不该你操作" + user);

                PushObject push = new PushObject();
                push.setType(GamePushType.GAME_21014.getValue());//不该你操作
                push.setCode("fail");
                send(this.toJSONString(push), LandlordsTcpService.userClients.get(user));

                return;
            }

            for (int i = 0; i < game.getDesk().getSeats().size(); i++) {
                Seat seat = game.getDesk().getSeats().get(i);
                if (seat.getPlayer().getUserName().equals(user)) {
                    //没有出牌 (不要)
                    if (null == playCard || playCard.size() == 0) {

                        PastCard pastCard = new PastCard();
                        pastCard.setCards(game.getDesk().getPastCard().getCards());
                        pastCard.setCardType(game.getDesk().getPastCard().getCardType());
                        pastCard.setLastPlayer(user);
                        pastCard.setActionPlayer(game.getDesk().getPastCard().getActionPlayer());
                        pastCard.setNextPlayer(game, user);
                        pastCard.setIsPass(Action.TRUE);
                        pastCard.setIndex(game.getDesk().getPastCard() == null ? 1 : game.getDesk().getPastCard().getIndex() + 1);

                        game.getDesk().setPastCard(pastCard);

                        game.getDesk().setNextPlayer(getNextPlayer(game, user));

//                        game.getDesk().getLastRoundCards().stream().filter(item -> item.getPlay().equals(user)).forEach(item -> item.setCardList(playCard));
                        game.getDesk().getLastRoundCards().add(new LastRoundCard(null, seat.getPlayer().getUserName()));

                        new PlayTimeOut(this, deskNo, game.getDesk().getNextPlayer(), game.getDesk().getPastCard().getIndex()).start();

                        for (Seat seat1 : game.getDesk().getSeats()) {
                            PushObject push = new PushObject();
                            push.setType(GamePushType.GAME_21007.getValue());//出牌
                            push.setData(new PushPlayLandlords(game, seat1.getPlayer().getUserName()));
                            send(this.toJSONString(push), LandlordsTcpService.userClients.get(seat1.getPlayer().getUserName()));
                        }

                        Seat nextSeat;
                        if (i == 0) {
                            nextSeat = game.getDesk().getSeats().get(1);
                        } else if (i == 1) {
                            nextSeat = game.getDesk().getSeats().get(2);
                        } else {
                            nextSeat = game.getDesk().getSeats().get(0);
                        }

                        //判断下家是否电脑出牌
                        new Thread(() -> robotPlay(nextSeat.getSeatNo())).start();

                    } else {//出牌

                        if (!game.checkCards(user, playCard)) {
                            System.out.println("用户手中没有此牌");
                            //通知用户出牌错误
                            PushObject push = new PushObject();
                            push.setType(GamePushType.GAME_21011.getValue());//牌型错误
                            PushPlayLandlords pushData = new PushPlayLandlords();
                            push.setData(pushData);
                            push.setCode("fail");
                            send(this.toJSONString(push), LandlordsTcpService.userClients.get(user));
                            return;
                        }

                        CardType playCardType = game.getCardType(playCard);
                        if (playCardType != CardType.ERROR) {

                            if (playCardType == CardType.ZHADAN || playCardType == CardType.HUOJIAN) {
                                game.getDesk().setMultiple(game.getDesk().getMultiple().multiply(new BigDecimal(2)));
                            }


                            //如果没有上一手牌  说明是第一个出牌的人
                            //上一手牌是他自己出的   说明没人出牌  他继续出牌
                            if (null == game.getDesk().getPastCard().getLastPlayer() || game.getDesk().getPastCard().getActionPlayer().equals(seat.getPlayer().getUserName())) {
                                //出牌成功 移除出的牌
                                game.removerCard(playCard, user);
                                //更换游戏中的上一手牌
                                PastCard pastCard = new PastCard();

                                pastCard.setLastPlayer(user);
                                pastCard.setActionPlayer(user);
                                pastCard.setNextPlayer(game, user);
                                pastCard.setCards(playCard);
                                pastCard.setCardType(playCardType);
                                pastCard.setIsPass(Action.FALSE);
                                pastCard.setIndex(game.getDesk().getPastCard() == null ? 1 : game.getDesk().getPastCard().getIndex() + 1);
                                game.getDesk().setPastCard(pastCard);

                                seat.setIndex(seat.getIndex() + 1);
                                game.getDesk().setNextPlayer(getNextPlayer(game, user));

//                                game.getDesk().getLastRoundCards().stream().filter(item -> item.getPlay().equals(user)).forEach(item -> item.setCardList(playCard));
                                game.getDesk().getLastRoundCards().add(new LastRoundCard(playCard, seat.getPlayer().getUserName()));

                                new PlayTimeOut(this, deskNo, game.getDesk().getNextPlayer(), game.getDesk().getPastCard().getIndex()).start();

                                for (Seat seat1 : game.getDesk().getSeats()) {
                                    PushObject push = new PushObject();
                                    push.setType(GamePushType.GAME_21007.getValue());//出牌
                                    push.setData(new PushPlayLandlords(game, seat1.getPlayer().getUserName()));
                                    send(this.toJSONString(push), LandlordsTcpService.userClients.get(seat1.getPlayer().getUserName()));
                                }

                                //判断是不是牌出完了
                                boolean result = false;
                                for (Seat temp_seat : game.getDesk().getSeats()) {
                                    if (temp_seat.getCards().size() == 0) {
                                        result = true;
                                        break;
                                    }
                                }

                                //结算游戏
                                if (result) {
                                    gameOver(game, seat);

                                    return;
                                } else {

                                    Seat nextSeat;
                                    if (i == 0) {
                                        nextSeat = game.getDesk().getSeats().get(1);
                                    } else if (i == 1) {
                                        nextSeat = game.getDesk().getSeats().get(2);
                                    } else {
                                        nextSeat = game.getDesk().getSeats().get(0);
                                    }

                                    //判断下家是否电脑出牌
                                    new Thread(() -> robotPlay(nextSeat.getSeatNo())).start();

                                    return;
                                }
                            } else {
                                //判断出的牌是不是比上一次大
                                if (game.compare(game.getDesk().getPastCard().getCards(), playCard) == 1) {
                                    //出牌成功 移除出的牌
                                    game.removerCard(playCard, user);
                                    //更换游戏中的上一手牌
                                    PastCard pastCard = new PastCard();
                                    pastCard.setLastPlayer(user);
                                    pastCard.setActionPlayer(user);
                                    pastCard.setNextPlayer(game, user);
                                    pastCard.setCards(playCard);
                                    pastCard.setCardType(playCardType);
                                    pastCard.setIsPass(Action.FALSE);
                                    pastCard.setIndex(game.getDesk().getPastCard() == null ? 1 : game.getDesk().getPastCard().getIndex() + 1);
                                    game.getDesk().setPastCard(pastCard);

                                    seat.setIndex(seat.getIndex() + 1);

                                    game.getDesk().setNextPlayer(getNextPlayer(game, user));

//                                    game.getDesk().getLastRoundCards().stream().filter(item -> item.getPlay().equals(user)).forEach(item -> item.setCardList(playCard));
                                    game.getDesk().getLastRoundCards().add(new LastRoundCard(playCard, seat.getPlayer().getUserName()));

                                    new PlayTimeOut(this, deskNo, game.getDesk().getNextPlayer(), game.getDesk().getPastCard().getIndex()).start();

                                    for (Seat seat1 : game.getDesk().getSeats()) {
                                        PushObject push = new PushObject();
                                        push.setType(GamePushType.GAME_21007.getValue());//出牌
                                        push.setData(new PushPlayLandlords(game, seat1.getPlayer().getUserName()));
                                        send(this.toJSONString(push), LandlordsTcpService.userClients.get(seat1.getPlayer().getUserName()));
                                    }


                                    //判断是不是牌出完了
                                    boolean result = false;
                                    for (Seat temp_seat : game.getDesk().getSeats()) {
                                        if (temp_seat.getCards().size() == 0) {
                                            result = true;
                                            break;
                                        }
                                    }

                                    //结算游戏
                                    if (result) {
                                        gameOver(game, seat);

                                        return;
                                    } else {

                                        Seat nextSeat;
                                        if (i == 0) {
                                            nextSeat = game.getDesk().getSeats().get(1);
                                        } else if (i == 1) {
                                            nextSeat = game.getDesk().getSeats().get(2);
                                        } else {
                                            nextSeat = game.getDesk().getSeats().get(0);
                                        }

                                        //判断下家是否电脑出牌
                                        new Thread(() -> robotPlay(nextSeat.getSeatNo())).start();

                                        return;
                                    }
                                } else {
                                    //通知用户出牌错误
                                    PushObject push = new PushObject();
                                    push.setType(GamePushType.GAME_21011.getValue());//牌型错误
                                    PushPlayLandlords pushData = new PushPlayLandlords();
                                    push.setData(pushData);
                                    push.setCode("fail");
                                    send(this.toJSONString(push), LandlordsTcpService.userClients.get(user));
                                }
                            }
                        } else {
                            //通知用户出牌错误
                            PushObject push = new PushObject();
                            push.setType(GamePushType.GAME_21011.getValue());//牌型错误
                            PushPlayLandlords pushData = new PushPlayLandlords();
                            push.setData(pushData);
                            push.setCode("fail");
                            send(this.toJSONString(push), LandlordsTcpService.userClients.get(user));
                        }
                    }
                }
            }
        }
    }

    /**
     * 托管
     *
     * @param obj 接收数据
     */
    private void robot(ReceiveObject obj) {
        JSONObject data = JSON.parseObject(obj.getData());
        String isRobot = data.getString("isRobot");
        Game game = LandlordsTcpService.games.get(deskNo);
        game.getDesk().getSeats().stream().filter(seat -> seat.getPlayer().getUserName().equals(user)).forEach(seat -> {
            if (isRobot.equals("true")) {
                seat.setIsRobot(Action.TRUE);
            } else {
                seat.setIsRobot(Action.FALSE);
            }
        });

        //托管
        game.getDesk().getSeats().stream().filter(seat1 -> !seat1.getPlayer().getUserName().equals(user)).forEach(seat1 -> {
            PushObject push = new PushObject();
            push.setType(GamePushType.GAME_21008.getValue());//托管
            if (isRobot.equals("true")) {
                push.setData(new PushRobot(user, Action.TRUE));
            } else {
                push.setData(new PushRobot(user, Action.FALSE));
            }
            send(this.toJSONString(push), LandlordsTcpService.userClients.get(seat1.getPlayer().getUserName()));
        });
    }

    /**
     * 电脑出牌
     *
     * @param seatNo 座位数据
     */
    private void robotPlay(int seatNo) {
        Seat seat = null;
        Seat nextSeat;
        synchronized (this) {
            try {
                this.wait(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if (LandlordsTcpService.games.containsKey(deskNo)) {
            Game game = LandlordsTcpService.games.get(deskNo);
            for (Seat seat1 : game.getDesk().getSeats()) {
                if (seatNo == seat1.getSeatNo()) {
                    seat = seat1;
                    break;
                }
            }
            if (null == seat) {
                return;
            }
            if (seat.getIsOffLine() == Action.TRUE && seat.getIsRobot() == Action.TRUE) {

                synchronized (LandlordsTcpService.games.get(deskNo)) {
                    PastCard pastCard = game.robotPlay(seat.getPlayer().getUserName());
                    pastCard.setCardType(game.getCardType(pastCard.getCards()));
                    pastCard.setIndex(game.getDesk().getPastCard() == null ? 1 : game.getDesk().getPastCard().getIndex() + 1);
                    game.getDesk().setPastCard(pastCard);
                    game.getDesk().setNextPlayer(getNextPlayer(game, seat.getPlayer().getUserName()));

                    game.removerCard(pastCard.getCards(), seat.getPlayer().getUserName());

                    if (Action.TRUE != pastCard.getIsPass()) {
                        seat.setIndex(seat.getIndex() + 1);
                    }

                    //判断是不是出了炸弹
                    if ((pastCard.getCardType() == CardType.ZHADAN || pastCard.getCardType() == CardType.HUOJIAN) && pastCard.getIsPass() == Action.FALSE) {
                        game.getDesk().setMultiple(game.getDesk().getMultiple().multiply(new BigDecimal(2)));
                    }

                    Seat finalSeat = seat;
//                    game.getDesk().getLastRoundCards().stream().filter(item -> item.getPlay().equals(finalSeat.getPlayer().getUserName()))
//                            .forEach(item -> item.setCardList(pastCard.getCards()));
                    if (pastCard.getIsPass().compareTo(Action.TRUE) == 0) {
                        game.getDesk().getLastRoundCards().add(new LastRoundCard(null, seat.getPlayer().getUserName()));
                    } else {
                        game.getDesk().getLastRoundCards().add(new LastRoundCard(pastCard.getCards(), seat.getPlayer().getUserName()));
                    }
                }

                new PlayTimeOut(this, deskNo, game.getDesk().getNextPlayer(), game.getDesk().getPastCard().getIndex()).start();

                //出牌
                game.getDesk().getSeats().stream().filter(seat1 -> seat1.getIsOffLine() != Action.TRUE).forEach(seat1 -> {
                    PushObject push = new PushObject();
                    push.setType(GamePushType.GAME_21007.getValue());//出牌
                    push.setData(new PushPlayLandlords(game, seat1.getPlayer().getUserName()));
                    send(this.toJSONString(push), LandlordsTcpService.userClients.get(seat1.getPlayer().getUserName()));
                });

                //判断牌是不是出完
                for (Seat seat1 : game.getDesk().getSeats()) {
                    if (seat1.getPlayer().getUserName().equals(seat.getPlayer().getUserName())) {
                        if (seat1.getCards().size() == 0) {
                            gameOver(game, seat1);
                            return;
                        }
                    }
                }

                if (seat.getSeatNo() == 0) {
                    nextSeat = game.getDesk().getSeats().get(1);
                } else if (seat.getSeatNo() == 1) {
                    nextSeat = game.getDesk().getSeats().get(2);
                } else {
                    nextSeat = game.getDesk().getSeats().get(0);
                }
                //判断下家是否电脑出牌
                new Thread(() -> robotPlay(nextSeat.getSeatNo())).start();
            }
        }
    }

    /**
     * 游戏结束  结算
     *
     * @param game 游戏数据
     * @param seat 座位数据
     */
    public void gameOver(Game game, Seat seat) {
        boolean isLandlords = seat.getPlayer().getUserName().equals(game.getDesk().getLandlordPlayer());

        Seat landlords = null;
        Seat play1 = null;
        Seat play2 = null;
        for (Seat seat1 : game.getDesk().getSeats()) {
            if (seat1.getPlayer().getUserName().equals(game.getDesk().getLandlordPlayer())) {
                landlords = seat1;
            } else {
                if (null == play1) {
                    play1 = seat1;
                } else if (null == play2) {
                    play2 = seat1;
                }
            }
        }

        assert play1 != null;
        assert landlords != null;
        assert play2 != null;


        BigDecimal play1_score = game.getDesk().getBaseScore().multiply(new BigDecimal(game.getDesk().getCallLandlordScore())).multiply(game.getDesk().getMultiple());
        BigDecimal play1_surplus_score = apiUserAppService.searchByUserName(play1.getPlayer().getUserName()).getMoney();
        if (play1.getIsDouble() == Action.TRUE) {
            play1_score = play1_score.multiply(new BigDecimal("2"));
        }

        BigDecimal play2_score = game.getDesk().getBaseScore().multiply(new BigDecimal(game.getDesk().getCallLandlordScore())).multiply(game.getDesk().getMultiple());
        BigDecimal play2_surplus_score = apiUserAppService.searchByUserName(play2.getPlayer().getUserName()).getMoney();
        if (play2.getIsDouble() == Action.TRUE) {
            play2_score = play2_score.multiply(new BigDecimal("2"));
        }

        if (1 == landlords.getIndex() || (0 == play1.getIndex() && 0 == play2.getIndex())) {
            play1_score = play1_score.multiply(new BigDecimal("2"));
            play2_score = play2_score.multiply(new BigDecimal("2"));
        }

        BigDecimal landlords_surplus_score = apiUserAppService.searchByUserName(landlords.getPlayer().getUserName()).getMoney();

        GameBalance landlords_balance = new GameBalance();
        GameBalance play1_balance = new GameBalance();
        GameBalance play2_balance = new GameBalance();
        if (isLandlords) {

            landlords_balance.setGameResult(Action.TRUE);
            landlords_balance.setUserName(landlords.getPlayer().getUserName());
            landlords_balance.setName(landlords.getPlayer().getName());
            landlords_balance.setCardList(landlords.getCards());

            play1_balance.setGameResult(Action.FALSE);
            play1_balance.setUserName(play1.getPlayer().getUserName());
            play1_balance.setName(play1.getPlayer().getName());
            play1_balance.setCardList(play1.getCards());

            play2_balance.setGameResult(Action.FALSE);
            play2_balance.setUserName(play2.getPlayer().getUserName());
            play2_balance.setName(play2.getPlayer().getName());
            play2_balance.setCardList(play2.getCards());

            if (play1_surplus_score.compareTo(play1_score) < 0) {
                landlords_balance.setScore(play1_surplus_score);
                play1_balance.setScore(play1_surplus_score);
            } else {
                landlords_balance.setScore(play1_score);
                play1_balance.setScore(play1_score);
            }

            if (play2_surplus_score.compareTo(play2_score) < 0) {
                landlords_balance.setScore(landlords_balance.getScore().add(play2_surplus_score));
                play2_balance.setScore(play2_surplus_score);
            } else {
                landlords_balance.setScore(landlords_balance.getScore().add(play2_score));
                play2_balance.setScore(play2_score);
            }

        } else {
            landlords_balance.setGameResult(Action.FALSE);
            landlords_balance.setUserName(landlords.getPlayer().getUserName());
            landlords_balance.setName(landlords.getPlayer().getName());
            landlords_balance.setCardList(landlords.getCards());

            play1_balance.setGameResult(Action.TRUE);
            play1_balance.setUserName(play1.getPlayer().getUserName());
            play1_balance.setName(play1.getPlayer().getName());
            play1_balance.setCardList(play1.getCards());

            play2_balance.setGameResult(Action.TRUE);
            play2_balance.setUserName(play2.getPlayer().getUserName());
            play2_balance.setName(play2.getPlayer().getName());
            play2_balance.setCardList(play2.getCards());

            if ((play1_score.add(play2_score)).compareTo(landlords_surplus_score) > 0) {//余额不足以支付
                //两家分数一样
                landlords_balance.setScore(landlords_surplus_score);
                play1_balance.setScore(landlords_surplus_score.divide(new BigDecimal(2), 2, BigDecimal.ROUND_HALF_UP));
                play2_balance.setScore(landlords_surplus_score.divide(new BigDecimal(2), 2, BigDecimal.ROUND_HALF_UP));
            } else {
                landlords_balance.setScore(play1_score.add(play2_score));
                play1_balance.setScore(play1_score);
                play2_balance.setScore(play2_score);
            }

        }

        PushObject push = new PushObject();
        PushGameOver pushData = new PushGameOver();
        pushData.getPlayers().add(landlords_balance);
        pushData.getPlayers().add(play1_balance);
        pushData.getPlayers().add(play2_balance);
        pushData.setLandlordsPlayer(landlords.getPlayer().getUserName());

        gameRecordAppService.createLandlords(pushData);

        pushData.getPlayers().stream().filter(gameBalance -> gameBalance.getGameResult() == Action.TRUE).forEach(gameBalance -> {
            gameBalance.setScore(gameBalance.getScore().subtract(gameBalance.getScore().multiply(new BigDecimal(0.05))).setScale(2, BigDecimal.ROUND_UP));
        });

        push.setData(pushData);
        push.setType(GamePushType.GAME_OVER.getValue());

        //推送游戏结果
        for (Seat seat1 : game.getDesk().getSeats()) {
            send(this.toJSONString(push), LandlordsTcpService.userClients.get(seat1.getPlayer().getUserName()));
        }

        //删除该桌
        List<String> deskNoList = new ArrayList<>();
        deskNoList.addAll(LandlordsTcpService.deskList.get("landlords" + game.getDesk().getBaseScore()));
        for (String temp_deskNo : deskNoList) {
            if (temp_deskNo.equals(game.getDesk().getDeskNo())) {
                deskNoList.remove(temp_deskNo);
                break;
            }
        }
        LandlordsTcpService.deskList.put("landlords" + game.getDesk().getBaseScore(), deskNoList);
        LandlordsTcpService.games.remove(game.getDesk().getDeskNo());
        //TODO 初始化桌数据
//        clearDesk(game.getDesk().getDeskNo());
    }

    /**
     * 清空桌数据
     *
     * @param deskNo 桌号
     */
    private void clearDesk(String deskNo) {
        Game game = LandlordsTcpService.games.get(deskNo);
        if (null != game) {
            game.getDesk().setGameStatus(null);
            game.getDesk().setRandomLandlordPlayer(null);
            game.getDesk().setLandlordPlayer(null);
            game.getDesk().setLandlordCard(null);
            game.getDesk().setMultiple(new BigDecimal(1));
            game.getDesk().setLandlordCard(null);
            game.getDesk().setGameStatus(GameStatus.OVER);
            for (Seat seat : game.getDesk().getSeats()) {
                seat.setPlayer(new Player(apiUserAppService.searchByUserName(seat.getPlayer().getUserName())));
                seat.setIsReady(Action.NONE);
                seat.setIsJoin(Action.TRUE);
                seat.setIsDouble(Action.NONE);
                seat.setCallLandlordScore(0);
                seat.setIsCallLandlord(Action.NONE);
                seat.setIsOffLine(Action.FALSE);
                seat.setIsEscape(Action.FALSE);
                seat.setCards(null);
                seat.setCardsSize(0);
            }
        }
    }

    /**
     * 生成随机桌号
     *
     * @return 桌号
     */

    private String deskNo() {
        String deskNo = UUID.randomUUID().toString();
        if (LandlordsTcpService.games.containsKey(deskNo)) {
            deskNo = deskNo();
        }
        return deskNo;
    }

    /**
     * 判断是不是在游戏中了
     *
     * @return 玩家所在桌号
     */
    private String isGamePlayer() {
        ListGameMultipleCommand command = new ListGameMultipleCommand();
        command.setGameType(GameType.LANDLORDS);
        List<GameMultiple> gameMultiples = gameMultipleAppService.list(command);
        for (GameMultiple item : gameMultiples) {
            List<String> deskList = LandlordsTcpService.deskList.get("landlords" + item.getMultiple());
            if (null != deskList) {
                for (String deskNo : deskList) {
                    Game game = LandlordsTcpService.games.get(deskNo);
                    if (null != game) {
                        for (Seat seat : game.getDesk().getSeats()) {
                            if (null != seat.getPlayer() && seat.getPlayer().getUserName().equals(user)) {
                                return game.getDesk().getDeskNo();
                            }
                        }
                    }
                }
            }
        }
        return null;
    }

    /**
     * 对象转JSON
     *
     * @param obj 数据
     * @return JSON数据
     */
    public String toJSONString(Object obj) {
        return JSON.toJSONString(obj, SerializerFeature.DisableCircularReferenceDetect);
    }

    /**
     * 获取下一个操作玩家
     *
     * @param game 游戏
     * @param user 当前玩家
     * @return 下一个玩家
     */
    public String getNextPlayer(Game game, String user) {
        for (int i = 0; i < game.getDesk().getSeats().size(); i++) {
            Seat seat = game.getDesk().getSeats().get(i);
            if (seat.getPlayer().getUserName().equals(user)) {
                if (i == 0) {
                    return game.getDesk().getSeats().get(1).getPlayer().getUserName();
                } else if (i == 1) {
                    return game.getDesk().getSeats().get(2).getPlayer().getUserName();
                } else {
                    return game.getDesk().getSeats().get(0).getPlayer().getUserName();
                }
            }
        }
        return null;
    }
}