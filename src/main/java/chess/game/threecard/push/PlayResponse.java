package chess.game.threecard.push;

import java.math.BigDecimal;

/**
 * Author pengyi
 * Create date 16-6-23
 */
public class PlayResponse {
    private int seatNo;
    private BigDecimal score;

    public int getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(int seatNo) {
        this.seatNo = seatNo;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }
}
