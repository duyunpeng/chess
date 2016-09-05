package chess.game.landlords.mode.push;

import chess.game.landlords.mode.Game;

import java.math.BigDecimal;

/**
 * 确认地主
 * <p>
 * Created by yjh on 16-6-29.
 */
public class PushConfirmLandlords {

    private String player;//地主玩家
    private BigDecimal multiple;//玩家自己的倍数

    public PushConfirmLandlords() {
    }

    public PushConfirmLandlords(Game game, String user) {
        this.player = game.getDesk().getLandlordPlayer();
        this.multiple = game.getDesk().getMultiple();
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public BigDecimal getMultiple() {
        return multiple;
    }

    public void setMultiple(BigDecimal multiple) {
        this.multiple = multiple;
    }
}
