package chess.game.bullfight.listener;

import chess.application.bullfightrecord.IBullfightRecordAppService;
import chess.core.common.Constants;
import chess.core.redis.RedisService;
import chess.game.bullfight.mode.Action;
import chess.game.bullfight.take.Game;
import com.alibaba.fastjson.JSONObject;

/**
 * Created by jm
 * Date : 16-7-21.
 */
public class StopTimeThread extends Thread {

    private String deskNo;
    private RedisService redisService;
    private IBullfightRecordAppService bullfightRecordAppService;

    public StopTimeThread(String deskNo, RedisService redisService, IBullfightRecordAppService bullfightRecordAppService) {
        this.deskNo = deskNo;
        this.redisService = redisService;
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
        //判断桌子是否存在，游戏是否存在
        if (redisService.exists(deskNo)) {
            //调用比牌方法
            game.compare(deskNo, bullfightRecordAppService, redisService);
            //清除该轮游戏中玩家的数据
            for (int i = 0; i < game.getDesk().getSeats().size(); i++) {
                if (game.getDesk().getSeats().get(i).getPlayer() != null) {
                    game.getDesk().getSeats().get(i).setBankerMultiple(0);
                    game.getDesk().getSeats().get(i).setIsTheBanker(Action.FALSE);
                    game.getDesk().getSeats().get(i).setMultiple(0);
                    game.getDesk().getSeats().get(i).setBetYesOrNo(Action.FALSE);
                    game.getDesk().getSeats().get(i).setCards(null);
                    game.getDesk().getSeats().get(i).setIsRobBanker(null);
                }
                redisService.addCache(game.getDesk().getDeskNo(), JSONObject.toJSONString(game), Constants.REDIS_GAME_TIME_OUT);
            }
            //比牌结束看是否有新玩家进来 等待一段秒钟
            new CompareThread(deskNo, redisService, bullfightRecordAppService).start();
        } else {
            System.out.println("没有该桌游戏");
        }
    }
}

