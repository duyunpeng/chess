package chess.game.threecard.push;

import java.math.BigDecimal;

/**
 * Author pengyi
 * Create date 16-7-9
 */
public class OperationResponse {

    private int seatNo;

    private BigDecimal minScore;

    public int getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(int seatNo) {
        this.seatNo = seatNo;
    }

    public BigDecimal getMinScore() {
        return minScore;
    }

    public void setMinScore(BigDecimal minScore) {
        this.minScore = minScore;
    }

}
