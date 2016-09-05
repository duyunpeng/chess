package chess.game.threecard.listener;

import chess.application.gamerecord.IGameRecordAppService;
import chess.application.user.IApiUserAppService;
import chess.application.user.representation.ApiUserRepresentation;
import chess.core.common.Constants;
import chess.core.redis.RedisService;
import chess.game.threecard.command.GamePushType;
import chess.game.threecard.command.ReceiveObject;
import chess.game.threecard.mode.Game;
import chess.game.threecard.mode.Seat;
import chess.game.threecard.push.*;
import com.alibaba.fastjson.JSON;
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
import java.util.*;

/**
 * Created date 2016/3/25
 * Author pengyi
 */
class ThreeCardClient implements Runnable {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    private Socket s;
    private ApiUserRepresentation user;
    private DataInputStream dis = null;
    private DataOutputStream dos = null;


    private IApiUserAppService userAppService;
    private RedisService redisService;
    private IGameRecordAppService gameRecordAppService;
    private String deskNo;
    private Game game;
    private int seatNo;
    private BigDecimal score;
    private Boolean connect;

    ThreeCardClient(Socket s, RedisService redisService, IApiUserAppService userAppService,
                    IGameRecordAppService gameRecordAppService) {
        this.s = s;
        connect = true;
        try {
            dis = new DataInputStream(s.getInputStream());
            dos = new DataOutputStream(s.getOutputStream());
            this.userAppService = userAppService;
            this.redisService = redisService;
            this.gameRecordAppService = gameRecordAppService;
        } catch (EOFException e) {
            logger.info("socket.shutdown.message");
            close();
        } catch (IOException e) {
            logger.info("socket.connection.fail.message" + e.getMessage());
            close();
        }
    }

    public boolean send(PushObject pushObject, ThreeCardClient client) {
        try {
            if (null != client) {
                String json = JSON.toJSONString(pushObject, SerializerFeature.DisableCircularReferenceDetect)
                        .replace(" ", "").replace("\n", "").replace("\t", "");
                client.dos.writeUTF(json);
                logger.info("socket.server.sendMessage-->user:--message-->" + json);
                return true;
            }
        } catch (IOException e) {
            logger.info("socket.server.sendMessage.fail.message" + e.getMessage());
            client.close();
        }
        return false;
    }

    public void close() {
        connect = false;
        try {
            if (dis != null)
                dis.close();
            if (dos != null)
                dos.close();
            if (s != null) {
                s.close();
            }
            if (null != user) {
//                ThreeCardTcpService.readyUsers.remove(user.getUserName());
//                ThreeCardTcpService.userClients.remove(user.getUserName());
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            while (connect) {
                String str = dis.readUTF();
                logger.info("扎金花接收消息:" + str + ",座位号:" + seatNo);
                ReceiveObject obj = JSON.parseObject(str, ReceiveObject.class);
                int type = obj.getType();
                JSONObject jsonObject = JSON.parseObject(obj.getData());
                switch (type) {
                    case 1://连接
                        user = userAppService.searchByUserName(jsonObject.getString("userName"));
                        ThreeCardTcpService.userClients.put(jsonObject.getString("userName"), this);
                        if (redisService.exists("threeCard-" + jsonObject.getString("userName"))
                                && ThreeCardTcpService.games.containsKey(redisService.getCache("threeCard-" + jsonObject.getString("userName")))) {
                            deskNo = redisService.getCache("threeCard-" + jsonObject.getString("userName"));
                            game = ThreeCardTcpService.games.get(deskNo);
                            StartResponse response = JSON.parseObject(JSON.toJSONString(game.getDesk()),
                                    StartResponse.class);
                            response.getSeats().stream().filter(seat -> seat.getUserName().equals(jsonObject.getString("userName")))
                                    .forEach(seat -> seatNo = seat.getSeatNo());
                            score = response.getBaseScore();
                            send(new PushObject(GamePushType.RECONNECTION.getValue(), response, true, null), this);
                        } else {
                            this.pairGame(jsonObject.getBigDecimal("multiple"));
                        }
                        break;
                    case 2://看牌
                        synchronized (ThreeCardTcpService.games.get(deskNo)) {
                            game = ThreeCardTcpService.games.get(deskNo);
                            if (game.getDesk().getOperationSeat() == seatNo) {
                                game.getDesk().setDate(new Date());
                                new PlayTimeOutThread(deskNo, seatNo, user.getUserName(), game.getDesk().getDate()).start();
                            }
                            for (Seat seat : game.getDesk().getSeats()) {
                                LookResponse lookResponse = new LookResponse();
                                lookResponse.setSeatNo(seatNo);
                                if (!seat.getUserName().equals(user.getUserName())) {
                                    lookResponse.setCards(null);
                                } else {
                                    lookResponse.setCards(seat.getCards());
                                    seat.setLook(true);
                                }
                                send(new PushObject(GamePushType.LOOK.getValue(), lookResponse, true, null),
                                        ThreeCardTcpService.userClients.get(seat.getUserName()));
                            }
                            if (game.getDesk().getOperationSeat() == seatNo) {
                                game.getDesk().setDate(new Date());
                                new PlayTimeOutThread(deskNo, seatNo, user.getUserName(), game.getDesk().getDate()).start();
                            }
                        }
                        break;
                    case 3://下注

                        BigDecimal score = jsonObject.getBigDecimal("score");
                        synchronized (ThreeCardTcpService.games.get(deskNo)) {
                            game = ThreeCardTcpService.games.get(deskNo);
                            if (game.getDesk().getOperationSeat() != seatNo) {
                                PushObject push = new PushObject();
                                push.setType(GamePushType.PLAY.getValue());
                                push.setCode(false);
                                push.setMessage("不该你操作");
                                send(push, ThreeCardTcpService.userClients.get(user.getUserName()));
                                break;
                            }
                            game.getDesk().getSeats().stream().filter(seat -> seat.getUserName().equals(user.getUserName())).forEach(seat -> {
                                if (score.compareTo(minScore(game.getDesk().getLastScore(), game.getDesk().isLastLook(), seat.isLook())) < 0) {
                                    PushObject push = new PushObject();
                                    push.setType(GamePushType.PLAY.getValue());
                                    push.setCode(false);
                                    push.setMessage("积分太少了，不能下注");
                                    send(push, ThreeCardTcpService.userClients.get(seat.getUserName()));
                                    return;
                                }
                                if (seat.getGold().compareTo(minScore(game.getDesk().getLastScore(), game.getDesk().isLastLook(), seat.isLook())) < 0) {
                                    PushObject push = new PushObject();
                                    push.setType(GamePushType.PLAY.getValue());
                                    push.setCode(false);
                                    push.setMessage("积分不足，不能下注,请选择比牌");
                                    send(push, ThreeCardTcpService.userClients.get(seat.getUserName()));
                                    return;
                                }
                                game.getDesk().setTotalScore(game.getDesk().getTotalScore().add(score));
                                game.getDesk().setLastLook(seat.isLook());
                                game.getDesk().setLastScore(score);
                                seat.setScore(seat.getScore().add(score));
                                seat.setGold(seat.getGold().subtract(score));
                            });
                        }
                        PlayResponse playResponse = new PlayResponse();
                        playResponse.setSeatNo(seatNo);
                        playResponse.setScore(score);
                        for (final Seat seat : game.getDesk().getSeats()) {
                            send(new PushObject(GamePushType.PLAY.getValue(), playResponse, true, null),
                                    ThreeCardTcpService.userClients.get(seat.getUserName()));
                        }
                        operation(game.getNext(seatNo));

                        break;
                    case 4://比牌
                        int otherSeatNo = jsonObject.getInteger("otherSeatNo");
                        game = ThreeCardTcpService.games.get(deskNo);
                        if (0 == otherSeatNo) {
                            otherSeatNo = game.getNext(seatNo);
                        }
                        if (game.getDesk().getOperationSeat() != seatNo) {
                            PushObject push = new PushObject();
                            push.setType(GamePushType.COMPARE.getValue());
                            push.setCode(false);
                            push.setMessage("不该你操作");
                            send(push, ThreeCardTcpService.userClients.get(user.getUserName()));
                            break;
                        }
                        Seat my = null;
                        Seat other = null;
                        for (Seat seat : game.getDesk().getSeats()) {
                            if (seat.getSeatNo() == seatNo) {
                                my = seat;
                                BigDecimal compareScore = minScore(game.getDesk().getLastScore(), game.getDesk().isLastLook(), seat.isLook()).multiply(new BigDecimal(2));
                                seat.setGold(seat.getGold().subtract(compareScore));
                                seat.setScore(seat.getScore().add(compareScore));
                                game.getDesk().setTotalScore(game.getDesk().getTotalScore().add(compareScore));
                            } else if (seat.getSeatNo() == otherSeatNo) {
                                other = seat;
                            }
                        }
                        if (null == my || null == other) {
                            PushObject push = new PushObject();
                            push.setType(GamePushType.COMPARE.getValue());
                            push.setCode(false);
                            push.setMessage("座位号不正确" + seatNo + otherSeatNo);
                            send(push, ThreeCardTcpService.userClients.get(user.getUserName()));
                            break;
                        }
                        CompareResponse compareResponse = new CompareResponse();
                        compareResponse.setSeatNo(seatNo);
                        compareResponse.setOtherSeatNo(otherSeatNo);
                        int endSeat;
                        if (game.compare(my.getCards(), other.getCards())) {
                            compareResponse.setWinSeatNo(seatNo);
                            endSeat = otherSeatNo;
                        } else {
                            compareResponse.setWinSeatNo(otherSeatNo);
                            endSeat = seatNo;
                        }
                        for (final Seat seat : game.getDesk().getSeats()) {
                            if (seat.getSeatNo() == seatNo || seat.getSeatNo() == otherSeatNo) {
                                compareResponse.setCards(my.getCards());
                                compareResponse.setOtherCards(other.getCards());
                            } else {
                                compareResponse.setCards(null);
                                compareResponse.setCards(null);
                            }
                            send(new PushObject(GamePushType.COMPARE.getValue(), compareResponse, true, null),
                                    ThreeCardTcpService.userClients.get(seat.getUserName()));
                        }
                        if (!end(endSeat)) {
                            operation(game.getNext(seatNo));
                        }

                        break;
                    case 5://放弃
                        game = ThreeCardTcpService.games.get(deskNo);
                        if (!end(seatNo)) {
                            if (game.getDesk().getOperationSeat() == seatNo) {
                                operation(game.getNext(seatNo));
                            }
                        }
                        break;
                    case 6://退出
                        if (null != user) {
                            if (null != deskNo) {
                                exit();
                            }
                        }
                        break;
                    case 7://全部比牌
                        game = ThreeCardTcpService.games.get(deskNo);
                        Seat winSeat = null;
                        for (Seat seat : game.getDesk().getSeats()) {
                            if (seat.getSeatNo() == seatNo) {
                                winSeat = seat;
                                seat.setScore(seat.getScore().add(game.getDesk().getBaseScore().multiply(new BigDecimal(40))));
                                seat.setGold(seat.getGold().subtract(game.getDesk().getBaseScore().multiply(new BigDecimal(40))));
                                game.getDesk().setTotalScore(game.getDesk().getTotalScore().add(game.getDesk().getBaseScore().multiply(new BigDecimal(40))));
                            }
                        }
                        if (null == winSeat) {
                            break;
                        }
                        int endSeat1;
                        CompareResponse compareAllResponse = new CompareResponse();
                        for (Seat seat : game.getDesk().getSeats()) {
                            if (seat.getSeatNo() != seatNo && !seat.isEnd()) {
                                compareAllResponse.setSeatNo(seatNo);
                                compareAllResponse.setOtherSeatNo(seat.getSeatNo());
                                if (game.compare(winSeat.getCards(), seat.getCards())) {
                                    compareAllResponse.setWinSeatNo(seatNo);
                                    endSeat1 = seat.getSeatNo();
                                } else {
                                    winSeat = seat;
                                    compareAllResponse.setWinSeatNo(seat.getSeatNo());
                                    endSeat1 = seatNo;
                                }
                                for (final Seat seat1 : game.getDesk().getSeats()) {
                                    if (seat1.getSeatNo() == seatNo || seat1.getSeatNo() == seat.getSeatNo()) {
                                        compareAllResponse.setCards(winSeat.getCards());
                                        compareAllResponse.setOtherCards(seat.getCards());
                                    } else {
                                        compareAllResponse.setCards(null);
                                        compareAllResponse.setCards(null);
                                    }
                                    send(new PushObject(GamePushType.COMPARE.getValue(), compareAllResponse, true, null),
                                            ThreeCardTcpService.userClients.get(seat.getUserName()));
                                }
                                end(endSeat1);
                            }
                        }
                        break;

                    case 8://准备
                        //判断游戏是否存在

                        if (ThreeCardTcpService.games.containsKey(deskNo)) {
                            synchronized (ThreeCardTcpService.games.get(deskNo)) {
                                game = ThreeCardTcpService.games.get(deskNo);
                                //修改玩家准备状态
                                for (Seat seat : game.getDesk().getSeats()) {
                                    if (seat.getUserName().equals(user.getUserName())) {
                                        if (seat.isReady()) {
                                            PushObject push = new PushObject();//推送数据
                                            push.setType(GamePushType.READY.getValue());//玩家更改准备状态
                                            push.setCode(false);
                                            push.setMessage("您已经准备");
                                            send(push, ThreeCardTcpService.userClients.get(user.getUserName()));
                                            break;
                                        }
                                        seat.setReady(true);
                                        break;
                                    }
                                }

                                //有人更改准备状态 告诉其他用户
                                for (Seat item : game.getDesk().getSeats()) {
                                    PushObject push = new PushObject();//推送数据
                                    push.setType(GamePushType.READY.getValue());//玩家更改准备状态
                                    push.setData(seatNo);
                                    push.setCode(true);
                                    send(push, ThreeCardTcpService.userClients.get(item.getUserName()));
                                }
                                checkRead();
                            }
                        }
                        break;
                }
            }
        } catch (EOFException e) {
            logger.info("socket.shutdown.message");
        } catch (IOException e) {
            logger.info("socket.dirty.shutdown.message" + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            logger.info("socket.dirty.shutdown.message");
            e.printStackTrace();
        } finally {
            close();
        }
    }

    public void exit() {
        game = ThreeCardTcpService.games.get(deskNo);
        if (null != game) {
            synchronized (ThreeCardTcpService.games.get(deskNo)) {
                game = ThreeCardTcpService.games.get(deskNo);
                if (!user.getUserName().startsWith("robot")) {
                    redisService.delete("threeCard-" + user.getUserName());
                }
                final int[] noExit = new int[1];
                game.getDesk().getSeats().stream().filter(seat -> seat.getSeatNo() != seatNo).forEach(seat -> {
                    noExit[0]++;
                    send(new PushObject(GamePushType.EXIT.getValue(), seatNo, true, ""), ThreeCardTcpService.userClients.get(seat.getUserName()));
                });
                if (0 == noExit[0]) {
                    redisService.delete(deskNo);
                    ThreeCardTcpService.deskNos.remove(deskNo);
                    if (ThreeCardTcpService.userClients.containsKey(user.getUserName())) {
                        ThreeCardTcpService.userClients.remove(user.getUserName());
                    }
                    return;
                }

                game.getDesk().getSeats().stream().filter(seat -> seat.getSeatNo() == seatNo && !seat.isEnd()).forEach(seat -> end(seatNo));
                if (game.getDesk().getOperationSeat() == seatNo) {
                    if (!end(seatNo)) {
                        operation(game.getNext(seatNo));
                    }
                }
                for (Seat seat : game.getDesk().getSeats()) {
                    if (seat.getSeatNo() == seatNo) {
                        game.getDesk().getSeats().remove(seat);
                        game.getDesk().getSeatsNo().add(seatNo);
                        break;
                    }
                }
            }
        }
        checkRead();
    }

    private BigDecimal minScore(BigDecimal lastScore, boolean lastLook, boolean isLook) {
        if (!lastLook && isLook) {
            return lastScore.multiply(new BigDecimal(2));
        }
        if (lastLook && !isLook) {
            return lastScore.divide(new BigDecimal(2), 2, BigDecimal.ROUND_HALF_UP);
        }
        return lastScore;
    }

    /**
     * 游戏配桌
     *
     * @param multiple 倍数
     */
    private void pairGame(BigDecimal multiple) {
        score = multiple;

        boolean find = false;

        synchronized (ThreeCardTcpService.deskNos) {
            for (Map.Entry<String, BigDecimal> entry : ThreeCardTcpService.deskNos.entrySet()) {
                if (entry.getValue().compareTo(score) == 0 && ThreeCardTcpService.games.containsKey(entry.getKey())) {
                    game = ThreeCardTcpService.games.get(entry.getKey());
                    if (game.getDesk().getSeats().size() < 5 && !game.getDesk().exists(user.getUserName())) {
                        int robot = 0;
                        if (user.getUserName().startsWith("robot")) {
                            for (Seat seat : game.getDesk().getSeats()) {
                                if (seat.getUserName().startsWith("robot")) {
                                    robot++;
                                }
                            }
                        }

                        if (robot < 3) {
                            seatNo = game.addSeat(user);
                            deskNo = game.getDesk().getDeskNo();
                            find = true;
                            if (0 == game.getDesk().getOperationSeat()) {
                                new ReadyTimeOutThread(deskNo, seatNo, user.getUserName(), game.getDesk().getStartDate()).start();
                            }
                            break;
                        }

                    }
                }
            }

            if (!find) {
                game = new Game(user, score, deskNo());
                ThreeCardTcpService.games.put(game.getDesk().getDeskNo(), game);
                seatNo = 1;
                deskNo = game.getDesk().getDeskNo();
                ThreeCardTcpService.deskNos.put(deskNo, score);
                new ReadyTimeOutThread(deskNo, seatNo, user.getUserName(), game.getDesk().getStartDate()).start();
            }
            if (!user.getUserName().startsWith("robot")) {
                redisService.addCache("threeCard-" + user.getUserName(), deskNo, Constants.REDIS_GAME_TIME_OUT);
            }

            final Seat[] joinSeat = new Seat[1];

            game.getDesk().getSeats().stream().filter(seat -> seat.getSeatNo() == seatNo).forEachOrdered(seat -> {
                joinSeat[0] = seat;
                send(new PushObject(GamePushType.START.getValue(), JSON.toJSONString(game.getDesk()), true, null), this);
            });

            game.getDesk().getSeats().stream().filter(seat -> seat.getSeatNo() != seatNo).forEachOrdered(seat -> send(new PushObject(GamePushType.CONNECTION.getValue(), JSON.toJSONString(joinSeat[0]), true, null),
                    ThreeCardTcpService.userClients.get(seat.getUserName())));
        }
    }

    /**
     * 一家被淘汰或放弃
     *
     * @param endSeatNo 淘汰或棋牌的玩家
     */
    public boolean end(int endSeatNo) {
        synchronized (ThreeCardTcpService.games.get(deskNo)) {
            int remain = 0;
            PushObject push = new PushObject();
            int seatNo = 0;
            push.setType(GamePushType.ABANDON.getValue());
            game = ThreeCardTcpService.games.get(deskNo);
            for (Seat seat : game.getDesk().getSeats()) {
                if (endSeatNo != seat.getSeatNo()) {
                    if (!seat.isEnd()) {
                        remain++;
                        seatNo = seat.getSeatNo();
                    }
                } else {
                    seat.setEnd(true);
                }
                push.setData(endSeatNo);
                push.setCode(true);
                send(push, ThreeCardTcpService.userClients.get(seat.getUserName()));
            }
            if (remain == 1) {
                List<ScoreResponse> scoreResponses = new ArrayList<>();
                List<ScoreResult> results = new ArrayList<>();
                for (Seat seat : game.getDesk().getSeats()) {
                    ScoreResponse scoreResponse = new ScoreResponse();
                    ScoreResult scoreResult = new ScoreResult();
                    scoreResponse.setSeatNo(seat.getSeatNo());
                    scoreResult.setUsername(seat.getUserName());
                    scoreResponse.setCards(seat.getCards());
                    if (seat.getSeatNo() == seatNo) {
                        scoreResponse.setScore(game.getDesk().getTotalScore().multiply(new BigDecimal("0.95")));
                        scoreResult.setScore(game.getDesk().getTotalScore().multiply(new BigDecimal("0.95")).subtract(seat.getScore()));
                        seat.setGold(seat.getGold().add(scoreResult.getScore()).add(seat.getScore()));
                        scoreResponses.add(scoreResponse);
                        results.add(scoreResult);
                    } else if (null != seat.getScore()) {
                        scoreResponse.setScore(new BigDecimal(0).subtract(seat.getScore()));
                        scoreResult.setScore(new BigDecimal(0).subtract(seat.getScore()));
                        scoreResponses.add(scoreResponse);
                        results.add(scoreResult);
                    }
                }
                push.setType(GamePushType.SCORE.getValue());
                push.setData(scoreResponses);
                push.setCode(true);
                game.getDesk().getSeats().stream().filter(seat -> ThreeCardTcpService.userClients
                        .containsKey(seat.getUserName())).forEachOrdered(seat -> send(push, ThreeCardTcpService.userClients.get(seat.getUserName())));
                gameRecordAppService.createThreecard(results);
                game.getDesk().reset(seatNo);
                for (Seat seat : game.getDesk().getSeats()) {
                    new ReadyTimeOutThread(deskNo, seat.getSeatNo(), seat.getUserName(), game.getDesk().getStartDate()).start();
                }
                return true;
            }
        }
        return false;
    }


    /**
     * 通知该谁操作
     *
     * @param seatNo 　座位号
     */
    public void operation(final int seatNo) {
        if (0 != seatNo) {
            synchronized (ThreeCardTcpService.games.get(deskNo)) {
                game = ThreeCardTcpService.games.get(deskNo);
                if (null == game) {
                    return;
                }
                game.getDesk().setOperationSeat(seatNo);
                game.getDesk().setDate(new Date());
            }
            final String[] username = new String[1];
            OperationResponse response = new OperationResponse();
            response.setSeatNo(seatNo);
            logger.info("操作－－－－＞" + seatNo);
            game.getDesk().getSeats().stream().filter(seat -> seatNo == seat.getSeatNo()).forEach(seat -> {
                if (new BigDecimal(0).compareTo(game.getDesk().getLastScore()) == 0) {
                    response.setMinScore(seat.isLook() ? game.getDesk().getBaseScore()
                            .divide(new BigDecimal(2), 2, BigDecimal.ROUND_HALF_UP) : game.getDesk().getBaseScore());
                } else {
                    response.setMinScore(minScore(game.getDesk().getLastScore(), game.getDesk().isLastLook(), seat.isLook()));
                }
                username[0] = seat.getUserName();
            });

            for (Seat seat : game.getDesk().getSeats()) {
                send(new PushObject(GamePushType.OPERATION.getValue(), response, true, null), ThreeCardTcpService.userClients.get(seat.getUserName()));
            }
            new PlayTimeOutThread(deskNo, seatNo, username[0], game.getDesk().getDate()).start();
        }
    }

    /**
     * 生成随机桌号
     *
     * @return 桌号
     */
    private String deskNo() {
        String deskNo = UUID.randomUUID().toString();
        while (ThreeCardTcpService.games.containsKey(deskNo)) {
            deskNo = UUID.randomUUID().toString();
        }
        return deskNo;
    }

    public void checkRead() {

        game = ThreeCardTcpService.games.get(deskNo);
        if (0 != game.getDesk().getOperationSeat()) {
            return;
        }
        //判断所有用户是否准备好
        int count = 0;
        for (Seat seat : game.getDesk().getSeats()) {
            if (seat.isReady()) {
                count++;
            }
        }
        //如果所有用户都准备好 即开始游戏
        if (count == game.getDesk().getSeats().size() && count >= 2) {

            synchronized (ThreeCardTcpService.games.get(deskNo)) {
                game.getDesk().setTotalScore(this.score.multiply(new BigDecimal(game.getDesk().getSeats().size())));
                for (final Seat seat : game.getDesk().getSeats()) {
                    seat.setScore(this.score);
                    seat.setEnd(false);
                    seat.setGold(seat.getGold().subtract(this.score));
                }
                if (game.getDesk().getBanker() == 0) {
                    game.getDesk().setBanker(game.getDesk().getSeats().get(0).getSeatNo());
                }
                game.getDesk().setOperationSeat(game.getDesk().getBanker());
                for (Seat seat : game.getDesk().getSeats()) {
                    send(new PushObject(GamePushType.START.getValue(), game.getDesk(), true, ""), ThreeCardTcpService.userClients.get(seat.getUserName()));
                }

                game.dealCard();//发牌
                game.getDesk().setStartDate(new Date());
            }

            operation(game.getNext(game.getDesk().getBanker()));
        }
    }
}