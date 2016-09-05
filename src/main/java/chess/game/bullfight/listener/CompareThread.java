package chess.game.bullfight.listener;

import chess.application.bullfightrecord.IBullfightRecordAppService;
import chess.core.common.Constants;
import chess.core.redis.RedisService;
import chess.game.bullfight.function.RobBullfightTimeOut;
import chess.game.bullfight.mode.Action;
import chess.game.bullfight.mode.Seat;
import chess.game.bullfight.push.GamePushObject;
import chess.game.bullfight.push.PushObject;
import chess.game.bullfight.take.Game;
import com.alibaba.fastjson.JSONObject;


/**
 * Created by dyp
 * Date : 16-7-5.
 */
public class CompareThread extends Thread {
    private RedisService redisService;
    private String deskNo;
    private IBullfightRecordAppService bullfightRecordAppService;
    private BullfightClient bullfightClient = new BullfightClient();

    public CompareThread(String deskNo, RedisService redisService, IBullfightRecordAppService bullfightRecordAppService) {
        this.deskNo = deskNo;
        this.redisService = redisService;
        this.bullfightRecordAppService = bullfightRecordAppService;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(9000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String jsonDataNew = redisService.getCache(deskNo);
        Game game = JSONObject.parseObject(jsonDataNew, Game.class);
        //当游戏结束后，将断线的玩家移除该场游戏
        for (int i = 0; i < game.getDesk().getSeats().size(); i++) {
            if (game.getDesk().getSeats().get(i).getPlayer() != null) {
                if (game.getDesk().getSeats().get(i).getIsConnect() != null) {
                    if (game.getDesk().getSeats().get(i).getIsConnect().getValue() == 2) {
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
                    }
                }
            }
        }
        boolean isExistConnect = false;
        for (Seat seat : game.getDesk().getSeats()) {
            if (null != seat.getPlayer()) {
                if (seat.getIsConnect().getValue() == 1) {
                    isExistConnect = true;
                    break;
                }
            }
        }
        if (isExistConnect) {
            boolean isExistCard = false;
            for (Seat seat : game.getDesk().getSeats()) {
                if (seat.getCards() != null) {
                    isExistCard = true;
                    break;
                }
            }
            if (!isExistCard) {
                //重新开始之前 扣除进场费用
                int baseScore = game.getDesk().getBaseScore();
                for (int i = 0; i < game.getDesk().getSeats().size(); i++) {
                    if (null != game.getDesk().getSeats().get(i).getPlayer()) {
                        if (null != game.getDesk().getSeats().get(i).getIsPlaying()) {
                            if (game.getDesk().getSeats().get(i).getIsPlaying().getValue() == 1) {
                                //判断玩家的金币是否能够继续进行下一场游戏，如果不能，则移除该玩家
                                Boolean charge = bullfightRecordAppService.charge(game.getDesk().getSeats().get(i).getPlayer().getUserName(), baseScore);
                                if (!charge) {
                                    PushObject push = new PushObject();
                                    push.setCome(Boolean.FALSE);
                                    push.setType(GamePushObject.FIFTEEN.getValue());
                                    if (game.getDesk().getSeats().get(i).getIsExit().getValue() == 1) {
                                        bullfightClient.send(JSONObject.toJSONString(push), BullfightTcpService.userClients.get(game.getDesk().getSeats().get(i).getPlayer().getUserName()));
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
                                    redisService.addCache(game.getDesk().getDeskNo(), JSONObject.toJSONString(game), Constants.REDIS_GAME_TIME_OUT);
                                }
                                if (charge) {
                                    PushObject push = new PushObject();
                                    push.setCome(Boolean.TRUE);
                                    push.setType(GamePushObject.SIXTEEN.getValue());
                                    bullfightClient.send(JSONObject.toJSONString(push), BullfightTcpService.userClients.get(game.getDesk().getSeats().get(i).getPlayer().getUserName()));
                                }
                            }
                        }
                    }
                }
            }
            for (int i = 0; i < game.getDesk().getSeats().size(); i++) {
                if (game.getDesk().getSeats().get(i).getIsExit() != null) {
                    if (game.getDesk().getSeats().get(i).getIsExit().getValue() == 1) {
                        if (game.getDesk().getSeats().get(i).getIsPlaying() == null) {
                            game.getDesk().getSeats().get(i).setIsPlaying(Action.TRUE);
                            redisService.addCache(game.getDesk().getDeskNo(), JSONObject.toJSONString(game), Constants.REDIS_GAME_TIME_OUT);
                        }
                    }
                }
            }
            this.restart(game.getDesk().getDeskNo());
        } else {
            bullfightClient.peopleLessOneDelete(redisService, game);
        }
    }

    //重新开始游戏
    private void restart(String deskNo) {
        String jsonDataNew = redisService.getCache(deskNo);
        Game game = JSONObject.parseObject(jsonDataNew, Game.class);
        boolean isExistCard = false;
        for (Seat seat : game.getDesk().getSeats()) {
            if (seat.getCards() != null) {
                isExistCard = true;
                break;
            }
        }
        int count = 0;
        for (int i = 0; i < game.getDesk().getSeats().size(); i++) {
            if (game.getDesk().getSeats().get(i).getIsExit().getValue() == 1) {
                count++;
            }
        }
        if (count > 2 && !isExistCard) {
            //推送给所有人配桌完成
            bullfightClient.completeWithTable(game);
            game.dealCard(game, redisService);
            redisService.addCache(game.getDesk().getDeskNo(), JSONObject.toJSONString(game), Constants.REDIS_GAME_TIME_OUT);
            new RobBullfightTimeOut(redisService, game.getDesk().getDeskNo(), bullfightRecordAppService).start();
        }
    }
}
