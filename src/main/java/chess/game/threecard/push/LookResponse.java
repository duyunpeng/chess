package chess.game.threecard.push;

import chess.game.threecard.mode.Card;

import java.util.List;

/**
 * Author pengyi
 * Create date 16-6-23
 */
public class LookResponse {
    private int seatNo;
    private List<Card> cards;

    public int getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(int seatNo) {
        this.seatNo = seatNo;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
}
