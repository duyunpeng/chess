package chess.game.bullfight.function;

import chess.application.bullfightrecord.IBullfightRecordAppService;
import chess.core.common.Constants;
import chess.core.redis.RedisService;
import chess.game.bullfight.listener.BullfightClient;
import chess.game.bullfight.listener.BullfightTcpService;
import chess.game.bullfight.listener.CompareThread;
import chess.game.bullfight.listener.StopTimeThread;
import chess.game.bullfight.mode.Action;
import chess.game.bullfight.mode.Seat;
import chess.game.bullfight.push.BetPushObject;
import chess.game.bullfight.push.GamePushObject;
import chess.game.bullfight.push.PushObject;
import chess.game.bullfight.take.Game;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by jm on 16-7-19.
 * 下注超时
 */
public class BetTimeOut extends Thread {

    private RedisService redisService;
    private String deskNo;
    private IBullfightRecordAppService bullfightRecordAppService;
    private BullfightClient bullfightClient = new BullfightClient();

    public BetTimeOut(RedisService redisService, String deskNo, IBullfightRecordAppService bullfightRecordAppService) {
        this.redisService = redisService;
        this.deskNo = deskNo;
        this.bullfightRecordAppService = bullfightRecordAppService;
    }


    @Override
    public void run() {
        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String jsonData = redisService.getCache(deskNo);
        Game game = JSONObject.parseObject(jsonData, Game.class);
        boolean isBet = false;
        if (null != game) {
            for (Seat seat : game.getDesk().getSeats()) {
                if (null != seat.getPlayer()) {
                    if (seat.getBetYesOrNo().getValue() == 1) {
                        isBet = true;
                    } else {
                        isBet = false;
                        break;
                    }
                }
            }
            //判断庄家是否存在
            boolean isExistBanker = false;
            for (int j = 0; j < game.getDesk().getSeats().size(); j++) {
                if (game.getDesk().getSeats().get(j).getIsTheBanker().getValue() == 1) {
                    isExistBanker = true;
                    break;
                }
            }
            //判断当前游戏正在玩的玩家人数
            int currentCount = bullfightClient.currentCount(redisService, game.getDesk().getDeskNo());
            if (currentCount > 1) {
                if (isExistBanker && !isBet) {
                    for (Seat seat : game.getDesk().getSeats()) {
                        if (null != seat.getPlayer()) {
                            if (null != seat.getIsPlaying()) {
                                if (seat.getIsTheBanker().getValue() == 2) {
                                    if (seat.getBetYesOrNo().getValue() == 2) {
                                        seat.setMultiple(5);
                                        seat.setBetYesOrNo(Action.TRUE);
                                        PushObject push = new PushObject();
                                        push.setType(GamePushObject.FOUR.getValue());
                                        for (int i = 0; i < game.getDesk().getSeats().size(); i++) {
                                            if (null != game.getDesk().getSeats().get(i).getPlayer()) {
                                                if (game.getDesk().getSeats().get(i).getIsConnect().getValue() == 1) {
                                                    BetPushObject betPushObject = new BetPushObject();
                                                    betPushObject.setSeatNo(seat.getSeatNo());
                                                    betPushObject.setMultiple(seat.getMultiple());
                                                    push.setBetPushObject(betPushObject);
                                                    bullfightClient.send(JSONObject.toJSONString(push), BullfightTcpService.userClients.get(game.getDesk().getSeats().get(i).getPlayer().getUserName()));
                                                }
                                            }
                                        }
                                    }
                                    redisService.addCache(game.getDesk().getDeskNo(), JSONObject.toJSONString(game), Constants.REDIS_GAME_TIME_OUT);
                                }
                            }
                        }
                    }
                    game.dealCard(game, redisService);
                    redisService.addCache(deskNo, JSON.toJSONString(game), Constants.REDIS_GAME_TIME_OUT);
                    //发牌之后等待一段时间然后比牌
                    new StopTimeThread(game.getDesk().getDeskNo(), redisService, bullfightRecordAppService).start();
                } else if (!isExistBanker) {
                    new CompareThread(deskNo, redisService, bullfightRecordAppService).start();
                }
            } else {
                //如果当前游戏正在玩的玩家人数<3,但游戏存在的玩家为>2,则重新开始游戏
                int existPlayer = bullfightClient.isExistPlayer(redisService, game.getDesk().getDeskNo());
                if (existPlayer > 2) {
                    new CompareThread(deskNo, redisService, bullfightRecordAppService).start();
                }
            }
        }
    }
}

