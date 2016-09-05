package chess.application.gamemultiple.command;

import chess.core.enums.GameType;

import java.math.BigDecimal;

/**
 * Author pengyi
 * Create date 16-7-19
 */
public class EditGameMultipleCommand {

    private String id;
    private int version;
    private GameType gameType;
    private BigDecimal multiple;
    private BigDecimal minMoney;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

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
}
