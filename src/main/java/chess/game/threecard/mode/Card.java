package chess.game.threecard.mode;

import java.util.Arrays;
import java.util.List;

/**
 * Author pengyi
 * Create date 16-6-12
 */
public class Card {

    private CardColor cardColor;
    private short value;

    public short getValue() {
        return value;
    }

    public void setValue(short value) {
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

    public Card(CardColor cardColor, short value) {
        this.cardColor = cardColor;
        this.value = value;
    }

    public static List<Card> getAllCard() {
        return Arrays.asList(
                new Card(CardColor.SPADE, (short) 2),
                new Card(CardColor.HEART, (short) 2),
                new Card(CardColor.PLUM, (short) 2),
                new Card(CardColor.BLOCK, (short) 2),
                new Card(CardColor.SPADE, (short) 3),
                new Card(CardColor.HEART, (short) 3),
                new Card(CardColor.PLUM, (short) 3),
                new Card(CardColor.BLOCK, (short) 3),
                new Card(CardColor.SPADE, (short) 4),
                new Card(CardColor.HEART, (short) 4),
                new Card(CardColor.PLUM, (short) 4),
                new Card(CardColor.BLOCK, (short) 4),
                new Card(CardColor.SPADE, (short) 5),
                new Card(CardColor.HEART, (short) 5),
                new Card(CardColor.PLUM, (short) 5),
                new Card(CardColor.BLOCK, (short) 5),
                new Card(CardColor.SPADE, (short) 6),
                new Card(CardColor.HEART, (short) 6),
                new Card(CardColor.PLUM, (short) 6),
                new Card(CardColor.BLOCK, (short) 6),
                new Card(CardColor.SPADE, (short) 7),
                new Card(CardColor.HEART, (short) 7),
                new Card(CardColor.PLUM, (short) 7),
                new Card(CardColor.BLOCK, (short) 7),
                new Card(CardColor.SPADE, (short) 8),
                new Card(CardColor.HEART, (short) 8),
                new Card(CardColor.PLUM, (short) 8),
                new Card(CardColor.BLOCK, (short) 8),
                new Card(CardColor.SPADE, (short) 9),
                new Card(CardColor.HEART, (short) 9),
                new Card(CardColor.PLUM, (short) 9),
                new Card(CardColor.BLOCK, (short) 9),
                new Card(CardColor.SPADE, (short) 10),
                new Card(CardColor.HEART, (short) 10),
                new Card(CardColor.PLUM, (short) 10),
                new Card(CardColor.BLOCK, (short) 10),
                new Card(CardColor.SPADE, (short) 11),
                new Card(CardColor.HEART, (short) 11),
                new Card(CardColor.PLUM, (short) 11),
                new Card(CardColor.BLOCK, (short) 11),
                new Card(CardColor.SPADE, (short) 12),
                new Card(CardColor.HEART, (short) 12),
                new Card(CardColor.PLUM, (short) 12),
                new Card(CardColor.BLOCK, (short) 12),
                new Card(CardColor.SPADE, (short) 13),
                new Card(CardColor.HEART, (short) 13),
                new Card(CardColor.PLUM, (short) 13),
                new Card(CardColor.BLOCK, (short) 13),
                new Card(CardColor.SPADE, (short) 14),
                new Card(CardColor.HEART, (short) 14),
                new Card(CardColor.PLUM, (short) 14),
                new Card(CardColor.BLOCK, (short) 14)
        );
    }

}
