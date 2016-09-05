package chess.game.landlords.function;

import chess.core.common.Constants;
import chess.game.landlords.command.GamePushType;
import chess.game.landlords.command.PushObject;
import chess.game.landlords.listener.LandlordsClient;
import chess.game.landlords.listener.LandlordsTcpService;
import chess.game.landlords.mode.*;
import chess.game.landlords.mode.push.PushDoubleLandlords;
import chess.game.landlords.mode.push.PushPlayLandlords;
import com.alibaba.fastjson.JSONObject;

/**
 * 加倍超时操作
 * Created by yjh on 16-7-6.
 */
public class DoubleTimeOut extends Thread {

    private LandlordsClient landlordsClient;
    private String deskNo;

    public DoubleTimeOut() {
    }

    public DoubleTimeOut(LandlordsClient landlordsClient, String deskNo) {
        this.landlordsClient = landlordsClient;
        this.deskNo = deskNo;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(21000);//30秒操作超时
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

//        String jsonData = landlordsClient.getRedisService().getCache(deskNo);
//        Game game = JSONObject.parseObject(jsonData, Game.class);
        Game game = LandlordsTcpService.games.get(deskNo);
        if (null == game) {
            return;
        }

        //推送加倍结果
        game.getDesk().getSeats().stream().filter(seat -> seat.getIsDouble() == Action.NONE).forEachOrdered(seat -> {
            seat.setIsDouble(Action.FALSE);

//            landlordsClient.getRedisService().addCache(game.getDesk().getDeskNo(), JSONObject.toJSONString(game), Constants.REDIS_GAME_TIME_OUT);

            //推送加倍结果
            game.getDesk().getSeats().forEach(seat1 -> {
                PushObject push = new PushObject();
                push.setType(GamePushType.GAME_21006.getValue());
                push.setData(new PushDoubleLandlords(game, seat.getPlayer().getUserName(), Action.FALSE));
                landlordsClient.send(JSONObject.toJSONString(push), LandlordsTcpService.userClients.get(seat1.getPlayer().getUserName()));
            });
        });

        //统计玩家加过倍的人数
        int isDoubleCount = 0;
        for (int i = 0; i < game.getDesk().getSeats().size(); i++) {
            Seat seat = game.getDesk().getSeats().get(i);
            if (Action.NONE != seat.getIsDouble()) {
                isDoubleCount++;
            }
        }
        if (isDoubleCount == 3 && game.getDesk().getGameStatus() == GameStatus.DOUBLE) {
            //三位玩家都加倍了  开始游戏
            game.getDesk().setGameStatus(GameStatus.PLAY);//出牌阶段
            PastCard pastCard = new PastCard();
            pastCard.setNextPlayer(game.getDesk().getLandlordPlayer());
            pastCard.setIndex(game.getDesk().getPastCard() == null ? 1 : game.getDesk().getPastCard().getIndex() + 1);
            game.getDesk().setPastCard(pastCard);

//            landlordsClient.getRedisService().addCache(game.getDesk().getDeskNo(), landlordsClient.toJSONString(game), Constants.REDIS_GAME_TIME_OUT);

            for (Seat seat : game.getDesk().getSeats()) {
                PushObject push = new PushObject();
                push.setType(GamePushType.GAME_21007.getValue());//出牌
                push.setData(new PushPlayLandlords(game, seat.getPlayer().getUserName()));
                landlordsClient.send(landlordsClient.toJSONString(push), LandlordsTcpService.userClients.get(seat.getPlayer().getUserName()));
            }

            new PlayTimeOut(landlordsClient, deskNo, game.getDesk().getNextPlayer(), game.getDesk().getPastCard().getIndex()).start();
        }
    }
}
