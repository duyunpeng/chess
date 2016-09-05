package chess.game.landlords.mode.push;

import chess.game.landlords.mode.Action;
import chess.game.landlords.mode.Game;
import chess.game.landlords.mode.Seat;

/**
 * 托管
 * <p>
 * Created by yjh on 16-7-6.
 */
public class PushRobot {

    private String player;

    private Action isRobot;

    public PushRobot() {
    }

    public PushRobot(String user, Action isRobot) {
        this.player = user;
        this.isRobot = isRobot;
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public Action getIsRobot() {
        return isRobot;
    }

    public void setIsRobot(Action isRobot) {
        this.isRobot = isRobot;
    }
}
