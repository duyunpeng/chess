package chess.domain.model.gamerecord;

import chess.core.enums.GameResult;
import chess.core.enums.GameType;
import chess.core.id.ConcurrencySafeEntity;
import chess.domain.model.user.User;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 游戏记录
 * <p>
 * Created by yjh on 16-7-5.
 */
public class GameRecord extends ConcurrencySafeEntity {

    private User user;
    private BigDecimal score;
    private GameType gameType;
    private GameResult gameResult;

    private void setUser(User user) {
        this.user = user;
    }

    private void setScore(BigDecimal score) {
        this.score = score;
    }

    private void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    private void setGameResult(GameResult gameResult) {
        this.gameResult = gameResult;
    }

    public User getUser() {
        return user;
    }

    public BigDecimal getScore() {
        return score;
    }

    public GameType getGameType() {
        return gameType;
    }

    public GameResult getGameResult() {
        return gameResult;
    }

    public GameRecord() {
        super();
    }

    public GameRecord(User user, BigDecimal score, GameType gameType, GameResult gameResult) {
        this.user = user;
        this.score = score;
        this.gameType = gameType;
        this.gameResult = gameResult;
        this.setCreateDate(new Date());
    }
}
