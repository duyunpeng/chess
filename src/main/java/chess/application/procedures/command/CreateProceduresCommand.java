package chess.application.procedures.command;

import chess.core.enums.GameType;
import chess.domain.model.user.User;

import java.math.BigDecimal;

/**
 * Created by yjh
 * Date : 2016/8/14.
 */
public class CreateProceduresCommand {

    private User user;
    private GameType gameType;
    private BigDecimal procedures;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public GameType getGameType() {
        return gameType;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public BigDecimal getProcedures() {
        return procedures;
    }

    public void setProcedures(BigDecimal procedures) {
        this.procedures = procedures;
    }
}
