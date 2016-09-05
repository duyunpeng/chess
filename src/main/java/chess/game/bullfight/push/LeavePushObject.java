package chess.game.bullfight.push;

import chess.game.bullfight.mode.Card;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by dyp
 * Date : 16-7-15.
 */
public class LeavePushObject {

    private BigDecimal money;//积分
    private List<Card> cards;//牌
    private int seatNo;//座位号

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

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
}
