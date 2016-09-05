package chess.application.robot.command;

import chess.core.enums.GameType;

import java.math.BigDecimal;

/**
 * Author pengyi
 * Date 16-8-24.
 */
public class AddRobotCommand {

    private GameType gameType;
    private int count;
    private BigDecimal score;

    public GameType getGameType() {
        return gameType;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }
}
