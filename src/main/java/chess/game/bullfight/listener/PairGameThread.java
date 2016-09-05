package chess.game.bullfight.listener;

import chess.application.bullfightrecord.IBullfightRecordAppService;
import chess.core.common.Constants;
import chess.core.redis.RedisService;
import chess.game.bullfight.function.RobBullfightTimeOut;
import chess.game.bullfight.mode.Action;
import chess.game.bullfight.take.Game;
import com.alibaba.fastjson.JSONObject;


/**
 * Created by dyp
 * Date : 16-7-4.
 */
public class PairGameThread extends Thread {

    private BullfightClient bullfightClient = new BullfightClient();
    private RedisService redisService;
    private IBullfightRecordAppService bullfightRecordAppService;
    private String deskNo;

    public PairGameThread(RedisService redisService,String deskNo, IBullfightRecordAppService bullfightRecordAppService) {
        this.redisService = redisService;
        this.deskNo = deskNo;
        this.bullfightRecordAppService = bullfightRecordAppService;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(7000);//当前游戏人数已经大于2，等待几秒，看游戏里人员的变化
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String jsonDataNew = redisService.getCache(deskNo);
        Game game = JSONObject.parseObject(jsonDataNew, Game.class);
        //得到该游戏的人数
        int count = 0;
        if (null != game) {
            for (int l = 0; l < game.getDesk().getSeats().size(); l++) {
                if (game.getDesk().getSeats().get(l).getIsExit().getValue() == 1) {
                    count++;
                }
            }
            //当人数>2时，表示可以开始游戏，给每个玩家推送数据
            if (count > 2) {
                //配桌完成 推送给每个玩家
                bullfightClient.completeWithTable(game);
                //判断在游戏中是否有新玩家存在
                boolean b = false;
                for (int j = 0; j < game.getDesk().getSeats().size(); j++) {
                    if (game.getDesk().getSeats().get(j).getIsExit().getValue() == 1) {
                        if (game.getDesk().getSeats().get(j).getIsOldOrNew() == null) {
                            b = true;
                            break;
                        } else {
                            b = false;
                        }
                    }
                }
                //将新玩家的状态isPlaying设置为true
                if (b) {
                    for (int i = 0; i < game.getDesk().getSeats().size(); i++) {
                        if (game.getDesk().getSeats().get(i).getIsExit() != null) {
                            if (game.getDesk().getSeats().get(i).getIsExit().getValue() == 1) {
                                game.getDesk().getSeats().get(i).setIsPlaying(Action.TRUE);
                                redisService.addCache(game.getDesk().getDeskNo(), JSONObject.toJSONString(game), Constants.REDIS_GAME_TIME_OUT);
                            }
                        }
                    }
                    //判断玩家是否还没有开始发牌
                    boolean c = false;
                    for (int i = 0; i < game.getDesk().getSeats().size(); i++) {
                        if (game.getDesk().getSeats().get(i).getIsExit().getValue() == 1) {
                            if (game.getDesk().getSeats().get(i).getCards() == null) {
                                c = true;
                            } else {
                                c = false;
                                break;
                            }
                        }
                    }
                    //这场游戏还没有开始发牌
                    if (c) {
                        //发牌
                        game.dealCard(game, redisService);
                        redisService.addCache(game.getDesk().getDeskNo(), JSONObject.toJSONString(game), Constants.REDIS_GAME_TIME_OUT);
                        new RobBullfightTimeOut(redisService, game.getDesk().getDeskNo(), bullfightRecordAppService).start();
                    }
                }
            }
        }
    }
}

