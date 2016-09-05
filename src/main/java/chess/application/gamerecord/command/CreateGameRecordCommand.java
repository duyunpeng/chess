package chess.application.gamerecord.command;

import chess.core.enums.GameResult;
import chess.core.enums.GameType;

/**
 * Created by yjh on 16-7-5.
 */
public class CreateGameRecordCommand {

    private Integer score;
    private GameType gameType;
    private GameResult gameResult;
    private String userName;

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public GameType getGameType() {
        return gameType;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public GameResult getGameResult() {
        return gameResult;
    }

    public void setGameResult(GameResult gameResult) {
        this.gameResult = gameResult;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
