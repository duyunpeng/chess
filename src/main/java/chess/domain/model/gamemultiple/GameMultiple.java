package chess.domain.model.gamemultiple;

import chess.core.enums.GameType;
import chess.core.id.ConcurrencySafeEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Author pengyi
 * Create date 16-7-19
 */
public class GameMultiple extends ConcurrencySafeEntity {

    private GameType gameType;
    private BigDecimal multiple;
    private BigDecimal minMoney;

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

    public BigDecimal getMinMoney() {
        return minMoney;
    }

    public void setMinMoney(BigDecimal minMoney) {
        this.minMoney = minMoney;
    }

    public GameMultiple() {
        super();
    }

    public GameMultiple(GameType gameType, BigDecimal multiple, BigDecimal minMoney) {
        this();
        this.gameType = gameType;
        this.multiple = multiple;
        this.minMoney = minMoney;
        this.setCreateDate(new Date());
    }
}
