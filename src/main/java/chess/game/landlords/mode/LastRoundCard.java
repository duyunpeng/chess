package chess.game.landlords.mode;

import java.util.List;

/**
 * Created by yjh
 * Date : 2016/8/15.
 */
public class LastRoundCard {

    private List<Card> cardList;
    private String play;

    public List<Card> getCardList() {
        return cardList;
    }

    public void setCardList(List<Card> cardList) {
        this.cardList = cardList;
    }

    public String getPlay() {
        return play;
    }

    public void setPlay(String play) {
        this.play = play;
    }

    public LastRoundCard() {
    }

    public LastRoundCard(List<Card> cardList, String play) {
        this.cardList = cardList;
        this.play = play;
    }
}
