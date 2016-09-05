package chess.game.landlords.function;

import chess.game.landlords.command.GamePushType;
import chess.game.landlords.command.PushObject;
import chess.game.landlords.listener.LandlordsClient;
import chess.game.landlords.listener.LandlordsTcpService;
import chess.game.landlords.mode.Game;
import chess.game.landlords.mode.GameStatus;
import chess.game.landlords.mode.PastCard;
import chess.game.landlords.mode.Seat;
import chess.game.landlords.mode.push.PushPlayLandlords;

/**
 * 出牌超时操作
 * Created by yjh on 16-7-7.
 */
public class PlayTimeOut extends Thread {

    private LandlordsClient landlordsClient;

    private String deskNo;

    private String userName;

    private int index;

    public PlayTimeOut() {
    }

    public PlayTimeOut(LandlordsClient landlordsClient, String deskNo, String userName, int index) {
        this.landlordsClient = landlordsClient;
        this.deskNo = deskNo;
        this.userName = userName;
        this.index = index;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(21000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        String jsonData = landlordsClient.getRedisService().getCache(deskNo);
//        Game game = JSONObject.parseObject(jsonData, Game.class);
        Game game = LandlordsTcpService.games.get(deskNo);
        if (null == game || game.getDesk().getGameStatus() != GameStatus.PLAY) {
            return;
        }

        for (Seat seat : game.getDesk().getSeats()) {
            if (seat.getPlayer().getUserName().equals(userName)) {
                if (game.getDesk().getPastCard().getNextPlayer().equals(seat.getPlayer().getUserName())) {
                    if (index != game.getDesk().getPastCard().getIndex()) {
                        return;
                    }
                    PastCard pastCard = game.robotPlay(seat.getPlayer().getUserName());
                    pastCard.setCardType(game.getCardType(pastCard.getCards()));
                    pastCard.setIndex(game.getDesk().getPastCard() == null ? 1 : game.getDesk().getPastCard().getIndex() + 1);

                    game.getDesk().setPastCard(pastCard);

                    game.getDesk().setNextPlayer(landlordsClient.getNextPlayer(game, userName));

                    game.removerCard(pastCard.getCards(), userName);
//                    landlordsClient.getRedisService().addCache(deskNo, landlordsClient.toJSONString(game), Constants.REDIS_GAME_TIME_OUT);

                    //判断是不是牌出完了
                    boolean result = false;
                    for (Seat temp_seat : game.getDesk().getSeats()) {
                        if (temp_seat.getCards().size() == 0) {
                            result = true;
                            break;
                        }
                    }

                    if (result) {
                        landlordsClient.gameOver(game, seat);

                        return;
                    } else {
                        //出牌
                        game.getDesk().getSeats().forEach(seat1 -> {
                            PushObject push = new PushObject();
                            push.setType(GamePushType.GAME_21007.getValue());//出牌
                            push.setData(new PushPlayLandlords(game, seat1.getPlayer().getUserName()));
                            landlordsClient.send(landlordsClient.toJSONString(push), LandlordsTcpService.userClients.get(seat1.getPlayer().getUserName()));
                        });

                        new PlayTimeOut(landlordsClient, deskNo, game.getDesk().getNextPlayer(), game.getDesk().getPastCard().getIndex()).start();
                    }
                } else {
                    break;
                }
            }
        }
    }
}
