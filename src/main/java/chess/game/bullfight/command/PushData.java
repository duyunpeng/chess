package chess.game.bullfight.command;

import chess.game.bullfight.mode.Card;
import chess.game.bullfight.mode.Player;
import chess.game.bullfight.mode.Seat;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dyp
 * Date : 16-7-12.
 */
public class PushData {

    private String user;
    private List<Card> cards; //玩家的牌
    private List<Seat> list; //下注的倍数放进集合里面
    private String deskNo;//桌号
    private Card card;//最后一张牌
    private List<Player> players = new ArrayList<>();

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public String getDeskNo() {
        return deskNo;
    }

    public void setDeskNo(String deskNo) {
        this.deskNo = deskNo;
    }

    public List<Seat> getList() {
        return list;
    }

    public void setList(List<Seat> list) {
        this.list = list;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
