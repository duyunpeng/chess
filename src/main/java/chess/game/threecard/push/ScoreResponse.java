package chess.game.threecard.push;

import chess.game.threecard.mode.Card;

import java.math.BigDecimal;
import java.util.List;

/**
 * Author pengyi
 * Create date 16-6-23
 */
public class ScoreResponse {
    private int seatNo;
    private BigDecimal score;
    private List<Card> cards;

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

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
}
