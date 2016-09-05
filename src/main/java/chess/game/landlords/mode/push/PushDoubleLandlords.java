package chess.game.landlords.mode.push;

import chess.game.landlords.mode.Action;
import chess.game.landlords.mode.Game;
import chess.game.landlords.mode.Seat;

/**
 * 加倍
 * <p>
 * Created by yjh on 16-6-30.
 */
public class PushDoubleLandlords {

    private String player;

    private Action isDouble;

    public PushDoubleLandlords() {
    }

    public PushDoubleLandlords(Game game, String user, Action isDouble) {
        game.getDesk().getSeats().stream().filter(seat -> seat.getPlayer().getUserName().equals(user)).forEachOrdered(seat -> {
            this.player = seat.getPlayer().getUserName();
        });
        this.isDouble = isDouble;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public Action getIsDouble() {
        return isDouble;
    }

    public void setIsDouble(Action isDouble) {
        this.isDouble = isDouble;
    }
}
