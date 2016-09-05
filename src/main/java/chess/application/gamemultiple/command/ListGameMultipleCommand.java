package chess.application.gamemultiple.command;

import chess.core.common.BasicPaginationCommand;
import chess.core.enums.GameType;

import java.math.BigDecimal;

/**
 * Author pengyi
 * Create date 16-7-19
 */
public class ListGameMultipleCommand extends BasicPaginationCommand {

    private GameType gameType;

    private BigDecimal multiple;

    public GameType getGameType() {
        return gameType;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public BigDecimal getMultiple() {
        return multiple;
    }

    public void setMultiple(BigDecimal multiple) {
        this.multiple = multiple;
    }
}
