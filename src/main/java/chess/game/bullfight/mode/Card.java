package chess.game.bullfight.mode;

import java.util.Arrays;
import java.util.List;

/**
 * Created by jm
 * Date : 16-6-20.
 */
public class Card {
    public static final List<Card> ALL_CARD = Arrays.asList(
            new Card(1, CardColor.SPADE.getValue()),
            new Card(1, CardColor.HEART.getValue()),
            new Card(1, CardColor.PLUM.getValue()),
            new Card(1, CardColor.BLOCK.getValue()),
            new Card(2, CardColor.SPADE.getValue()),
            new Card(2, CardColor.HEART.getValue()),
            new Card(2, CardColor.PLUM.getValue()),
            new Card(2, CardColor.BLOCK.getValue()),
            new Card(3, CardColor.SPADE.getValue()),
            new Card(3, CardColor.HEART.getValue()),
            new Card(3, CardColor.PLUM.getValue()),
            new Card(3, CardColor.BLOCK.getValue()),
            new Card(4, CardColor.SPADE.getValue()),
            new Card(4, CardColor.HEART.getValue()),
            new Card(4, CardColor.PLUM.getValue()),
            new Card(4, CardColor.BLOCK.getValue()),
            new Card(5, CardColor.SPADE.getValue()),
            new Card(5, CardColor.HEART.getValue()),
            new Card(5, CardColor.PLUM.getValue()),
            new Card(5, CardColor.BLOCK.getValue()),
            new Card(6, CardColor.SPADE.getValue()),
            new Card(6, CardColor.HEART.getValue()),
            new Card(6, CardColor.PLUM.getValue()),
            new Card(6, CardColor.BLOCK.getValue()),
            new Card(7, CardColor.SPADE.getValue()),
            new Card(7, CardColor.HEART.getValue()),
            new Card(7, CardColor.PLUM.getValue()),
            new Card(7, CardColor.BLOCK.getValue()),
            new Card(8, CardColor.SPADE.getValue()),
            new Card(8, CardColor.HEART.getValue()),
            new Card(8, CardColor.PLUM.getValue()),
            new Card(8, CardColor.BLOCK.getValue()),
            new Card(9, CardColor.SPADE.getValue()),
            new Card(9, CardColor.HEART.getValue()),
            new Card(9, CardColor.PLUM.getValue()),
            new Card(9, CardColor.BLOCK.getValue()),
            new Card(10, CardColor.SPADE.getValue()),
            new Card(10, CardColor.HEART.getValue()),
            new Card(10, CardColor.PLUM.getValue()),
            new Card(10, CardColor.BLOCK.getValue()),
            new Card(11, CardColor.SPADE.getValue()),
            new Card(11, CardColor.HEART.getValue()),
            new Card(11, CardColor.PLUM.getValue()),
            new Card(11, CardColor.BLOCK.getValue()),
            new Card(12, CardColor.SPADE.getValue()),
            new Card(12, CardColor.HEART.getValue()),
            new Card(12, CardColor.PLUM.getValue()),
            new Card(12, CardColor.BLOCK.getValue()),
            new Card(13, CardColor.SPADE.getValue()),
            new Card(13, CardColor.HEART.getValue()),
            new Card(13, CardColor.PLUM.getValue()),
            new Card(13, CardColor.BLOCK.getValue())

    );
    private int value;
    private int cardColor;

    public Card() {
    }

    public Card(int value, int cardColor) {
        this.value = value;
        this.cardColor = cardColor;
    }

    public int getCardColor() {
        return cardColor;
    }

    public void setCardColor(int cardColor) {
        this.cardColor = cardColor;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
