package chess.game.threecard.push;

import chess.game.threecard.mode.Card;

import java.util.List;

/**
 * Author pengyi
 * Create date 16-6-23
 */
public class CompareResponse {
    private int seatNo;
    private int otherSeatNo;
    private int winSeatNo;
    private List<Card> cards;
    private List<Card> otherCards;

    public int getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(int seatNo) {
        this.seatNo = seatNo;
    }

    public int getOtherSeatNo() {
        return otherSeatNo;
    }

    public void setOtherSeatNo(int otherSeatNo) {
        this.otherSeatNo = otherSeatNo;
    }

    public int getWinSeatNo() {
        return winSeatNo;
    }

    public void setWinSeatNo(int winSeatNo) {
        this.winSeatNo = winSeatNo;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public List<Card> getOtherCards() {
        return otherCards;
    }

    public void setOtherCards(List<Card> otherCards) {
        this.otherCards = otherCards;
    }
}
