package chess.game.threecard.mode;


/**
 * Author pengyi
 * Create date 16-6-21
 */
public enum CardType {

    LEOPARD("豹子", (short) 0),
    FLUSH("同花顺", (short) 1),
    SAME_COLOR("同花", (short) 2),
    STRAIGHT("顺子", (short) 3),
    DOUBLE("对子", (short) 4),
    SINGLE("单牌", (short) 5);
    private String name;
    private short value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public short getValue() {
        return value;
    }

    public void setValue(short value) {
        this.value = value;
    }

    CardType(String name, short value) {
        this.name = name;
        this.value = value;
    }
}
