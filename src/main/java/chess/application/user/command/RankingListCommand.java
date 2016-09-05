package chess.application.user.command;

import chess.core.common.BasicPaginationCommand;
import chess.core.enums.GameType;

/**
 * Created by yjh
 * Date : 2016/8/9.
 */
public class RankingListCommand extends BasicPaginationCommand {

    private GameType gameType;

    public GameType getGameType() {
        return gameType;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }
}
