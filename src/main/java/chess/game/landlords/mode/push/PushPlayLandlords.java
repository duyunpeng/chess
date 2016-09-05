package chess.game.landlords.mode.push;

import chess.game.landlords.mode.Action;
import chess.game.landlords.mode.Game;
import chess.game.landlords.mode.PastCard;
import chess.game.landlords.mode.Seat;

import java.math.BigDecimal;

/**
 * 准备出牌
 * <p>
 * Created by yjh on 16-6-30.
 */
public class PushPlayLandlords {

    private BigDecimal multiple;

    private PastCard pastCard;

    public PushPlayLandlords() {
    }

    public PushPlayLandlords(Game game, String user) {
        game.getDesk().getSeats().stream().filter(seat -> seat.getPlayer().getUserName().equals(user)).forEach(seat -> {
            this.multiple = game.getDesk().getMultiple();
            if (seat.getIsDouble() == Action.TRUE) {
                this.multiple = this.multiple.multiply(new BigDecimal(2));
            }
        });

        if (null != game.getDesk().getPastCard()) {
            this.pastCard = game.getDesk().getPastCard();
        }
    }

    public BigDecimal getMultiple() {
        return multiple;
    }

    public void setMultiple(BigDecimal multiple) {
        this.multiple = multiple;
    }

    public PastCard getPastCard() {
        return pastCard;
    }

    public void setPastCard(PastCard pastCard) {
        this.pastCard = pastCard;
    }
}
