package chess.game.bullfight.push;

import chess.game.bullfight.mode.Card;

import java.util.List;

/**
 * Created by dyp
 * Date : 16-7-13.
 */
public class PairCardPushObject {

    private List<Card> cards;//集合牌

    private Card card;//牌

    private int seatNo;//座位号

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public int getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(int seatNo) {
        this.seatNo = seatNo;
    }

    public Card getCard() { return card; }

    public void setCard(Card card) {
        this.card = card;
    }
}
