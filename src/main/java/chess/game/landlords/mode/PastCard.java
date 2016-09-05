package chess.game.landlords.mode;

import java.util.List;

/**
 * Created by YJH
 * Date : 16-6-21.
 */
public class PastCard {

    private String nextPlayer;    //下家操作玩家
    private String lastPlayer;      //上一家操作玩家
    private String actionPlayer;        //操作的玩家
    private List<Card> cards;
    private CardType cardType;  //牌型
    private Action isPass;          //是否pass
    private int index;

    public PastCard() {
    }

    public void setNextPlayer(Game game, String user) {
        game.getDesk().getSeats().stream().filter(seat -> seat.getPlayer().getUserName().equals(user)).forEach(seat -> {
            if (seat.getSeatNo() == 0) {
                this.nextPlayer = game.getDesk().getSeats().get(1).getPlayer().getUserName();
            } else if (seat.getSeatNo() == 1) {
                this.nextPlayer = game.getDesk().getSeats().get(2).getPlayer().getUserName();
            } else {
                this.nextPlayer = game.getDesk().getSeats().get(0).getPlayer().getUserName();
            }
        });
    }

    public String getNextPlayer() {
        return nextPlayer;
    }

    public void setNextPlayer(String nextPlayer) {
        this.nextPlayer = nextPlayer;
    }

    public String getLastPlayer() {
        return lastPlayer;
    }

    public void setLastPlayer(String lastPlayer) {
        this.lastPlayer = lastPlayer;
    }

    public String getActionPlayer() {
        return actionPlayer;
    }

    public void setActionPlayer(String actionPlayer) {
        this.actionPlayer = actionPlayer;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public Action getIsPass() {
        return isPass;
    }

    public void setIsPass(Action isPass) {
        this.isPass = isPass;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
