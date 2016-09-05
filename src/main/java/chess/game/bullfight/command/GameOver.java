package chess.game.bullfight.command;

import java.math.BigDecimal;

/**
 * Created by dyp
 * Date : 16-7-13.
 */
public class GameOver {

    private int seatNo;//座位号

    private BigDecimal score;//分数

    private int CardType;//牌型

    public int getCardType() {
        return CardType;
    }

    public void setCardType(int cardType) {
        CardType = cardType;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public int getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(int seatNo) {
        this.seatNo = seatNo;
    }
}
