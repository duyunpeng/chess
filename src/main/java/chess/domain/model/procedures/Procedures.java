package chess.domain.model.procedures;

import chess.core.enums.GameType;
import chess.core.id.ConcurrencySafeEntity;
import chess.domain.model.user.User;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 手续费
 * Created by yjh
 * Date : 2016/8/14.
 */
public class Procedures extends ConcurrencySafeEntity {
    private BigDecimal procedures; //手续费
    private GameType gameType;  //游戏类型
    private User user;//用户

    public BigDecimal getProcedures() {
        return procedures;
    }

    public void setProcedures(BigDecimal procedures) {
        this.procedures = procedures;
    }

    public GameType getGameType() {
        return gameType;
    }

    public void setGameType(GameType gameType) {
        this.gameType = gameType;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Procedures() {
        super();
    }

    public Procedures(BigDecimal procedures, GameType gameType,User user) {
        this.procedures = procedures;
        this.gameType = gameType;
        this.user = user;
        this.setCreateDate(new Date());
    }
}
