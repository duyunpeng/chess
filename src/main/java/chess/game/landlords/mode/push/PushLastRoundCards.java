package chess.game.landlords.mode.push;

import chess.game.landlords.mode.LastRoundCard;

import java.util.List;

/**
 * Created by yjh
 * Date : 2016/8/15.
 */
public class PushLastRoundCards {

    private List<LastRoundCard> lastRoundCards;//上一轮牌

    public List<LastRoundCard> getLastRoundCards() {
        return lastRoundCards;
    }

    public void setLastRoundCards(List<LastRoundCard> lastRoundCards) {
        this.lastRoundCards = lastRoundCards;
    }
}
