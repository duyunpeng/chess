package chess.game.bullfight.function;

import chess.application.bullfightrecord.IBullfightRecordAppService;
import chess.core.common.Constants;
import chess.core.redis.RedisService;
import chess.game.bullfight.listener.BullfightClient;
import chess.game.bullfight.listener.CompareThread;
import chess.game.bullfight.mode.Action;
import chess.game.bullfight.mode.Seat;
import chess.game.bullfight.take.Game;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


/**
 * Created by jm on 16-7-19.
 * 抢庄超时
 */
public class RobBullfightTimeOut extends Thread {

    private RedisService redisService;
    private String deskNo;
    private IBullfightRecordAppService bullfightRecordAppService;
    private BullfightClient bullfightClient = new BullfightClient();

    public RobBullfightTimeOut(RedisService redisService, String deskNo, IBullfightRecordAppService bullfightRecordAppService) {
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
        //判断所有玩家是否都已抢完庄
        boolean allRob = bullfightClient.allIsOrNotRob(game);
        //判断这场游戏中是否存在庄家
        boolean isExistBanker = false;
        if (game != null) {
            for (int j = 0; j < game.getDesk().getSeats().size(); j++) {
                if (game.getDesk().getSeats().get(j).getIsTheBanker().getValue() == 1) {
                    isExistBanker = true;
                    break;
                }
            }
            //判断当前游戏正在玩的玩家人数
            int currentCount = bullfightClient.currentCount(redisService, game.getDesk().getDeskNo());
            if (currentCount > 1) {
                if (!allRob) {
                    game.getDesk().getSeats().stream().filter(seat -> null != seat.getPlayer()).filter(seat -> null != seat.getIsPlaying()).filter(seat -> null == seat.getIsRobBanker()).forEach(seat -> {
                        seat.setIsRobBanker(Action.FALSE);
                        seat.setBankerMultiple(0);
                    });
                }
                redisService.addCache(game.getDesk().getDeskNo(), JSONObject.toJSONString(game), Constants.REDIS_GAME_TIME_OUT);
                jsonData = redisService.getCache(deskNo);
                game = JSONObject.parseObject(jsonData, Game.class);
                //当所有的玩家均已抢完庄并且不存在庄家时，开始判断庄家
                if (!isExistBanker) {
                    //抢庄的人数
                    int robCount = bullfightClient.robCountMean(game);
                    if (robCount >= 2) {
                        bullfightClient.robCountGeTwo(game);
                    }
                    //都不抢
                    if (0 == robCount) {
                        //都不抢庄的人数
                        int countOther = bullfightClient.notRobCountMean(game);
                        int ran = (int) (Math.random() * countOther);
                        for (int i = 0; i < game.getDesk().getSeats().size(); i++) {
                            if (null != game.getDesk().getSeats().get(ran).getPlayer()) {
                                game.getDesk().getSeats().get(ran).setIsTheBanker(Action.TRUE);
                                game.getDesk().getSeats().get(ran).setBankerMultiple(0);
                            } else {
                                for (Seat seat : game.getDesk().getSeats()) {
                                    if (null != seat.getPlayer()) {
                                        seat.setIsTheBanker(Action.TRUE);
                                        seat.setBankerMultiple(0);
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    //一个抢
                    if (1 == robCount) {
                        for (int i = 0; i < game.getDesk().getSeats().size(); i++) {
                            if (game.getDesk().getSeats().get(i).getBankerMultiple() != 0) {
                                game.getDesk().getSeats().get(i).setIsTheBanker(Action.TRUE);
                            }
                        }
                    }
                    redisService.addCache(deskNo, JSON.toJSONString(game), Constants.REDIS_GAME_TIME_OUT);
                    //庄家出来了推送数据
                    bullfightClient.robIsCome(game);
                }
                new BetTimeOut(redisService, game.getDesk().getDeskNo(), bullfightRecordAppService).start();
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

