package chess.game.landlords.mode;

import java.util.Arrays;
import java.util.List;

/**
 * Created by YJH
 * Date : 16-6-12.
 */
public class Card {

    private int value;
    private CardColor cardColor;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public CardColor getCardColor() {
        return cardColor;
    }

    public void setCardColor(CardColor cardColor) {
        this.cardColor = cardColor;
    }

    public Card() {
    }

    public Card(int value, CardColor cardColor) {
        this.value = value;
        this.cardColor = cardColor;
    }

    static final List<Card> ALL_CARD = Arrays.asList(
            new Card(3, CardColor.SPADE),
            new Card(3, CardColor.HEART),
            new Card(3, CardColor.PLUM),
            new Card(3, CardColor.BLOCK),
            new Card(4, CardColor.SPADE),
            new Card(4, CardColor.HEART),
            new Card(4, CardColor.PLUM),
            new Card(4, CardColor.BLOCK),
            new Card(5, CardColor.SPADE),
            new Card(5, CardColor.HEART),
            new Card(5, CardColor.PLUM),
            new Card(5, CardColor.BLOCK),
            new Card(6, CardColor.SPADE),
            new Card(6, CardColor.HEART),
            new Card(6, CardColor.PLUM),
            new Card(6, CardColor.BLOCK),
            new Card(7, CardColor.SPADE),
            new Card(7, CardColor.HEART),
            new Card(7, CardColor.PLUM),
            new Card(7, CardColor.BLOCK),
            new Card(8, CardColor.SPADE),
            new Card(8, CardColor.HEART),
            new Card(8, CardColor.PLUM),
            new Card(8, CardColor.BLOCK),
            new Card(9, CardColor.SPADE),
            new Card(9, CardColor.HEART),
            new Card(9, CardColor.PLUM),
            new Card(9, CardColor.BLOCK),
            new Card(10, CardColor.SPADE),
            new Card(10, CardColor.HEART),
            new Card(10, CardColor.PLUM),
            new Card(10, CardColor.BLOCK),
            new Card(11, CardColor.SPADE),
            new Card(11, CardColor.HEART),
            new Card(11, CardColor.PLUM),
            new Card(11, CardColor.BLOCK),
            new Card(12, CardColor.SPADE),
            new Card(12, CardColor.HEART),
            new Card(12, CardColor.PLUM),
            new Card(12, CardColor.BLOCK),
            new Card(13, CardColor.SPADE),
            new Card(13, CardColor.HEART),
            new Card(13, CardColor.PLUM),
            new Card(13, CardColor.BLOCK),
            new Card(14, CardColor.SPADE),
            new Card(14, CardColor.HEART),
            new Card(14, CardColor.PLUM),
            new Card(14, CardColor.BLOCK),
            new Card(15, CardColor.SPADE),
            new Card(15, CardColor.HEART),
            new Card(15, CardColor.PLUM),
            new Card(15, CardColor.BLOCK),
            new Card(16, CardColor.LITTLE_JOKER),
            new Card(16, CardColor.BIG_JOKER)
    );
}
