package chess.game.landlords.mode.push;

import chess.game.landlords.mode.*;

/**
 * 抢地主
 * <p>
 * Created by yjh on 16-6-28.
 */
public class PushRobLandlords {

    private String player;      //玩家

    private Action isRobLandlords;//是否抢地主

    public PushRobLandlords() {
    }

    public PushRobLandlords(Game game, String user) {
        game.getDesk().getSeats().stream().filter(seat -> seat.getPlayer().getUserName().equals(user)).forEachOrdered(seat -> {
            this.player = seat.getPlayer().getUserName();
//            this.isRobLandlords = seat.getIsRobLandlord();
        });
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public Action getIsRobLandlords() {
        return isRobLandlords;
    }

    public void setIsRobLandlords(Action isRobLandlords) {
        this.isRobLandlords = isRobLandlords;
    }
}
