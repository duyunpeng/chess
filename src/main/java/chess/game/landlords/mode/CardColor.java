package chess.game.landlords.mode;

/**
 * Created by YJH
 * Date : 16-6-12.
 */
public enum CardColor {

    SPADE("黑桃", 1),
    HEART("红桃", 2),
    PLUM("梅花", 3),
    BLOCK("方块", 4),
    LITTLE_JOKER("小王", 5),
    BIG_JOKER("大王", 6);

    private String name;
    private Integer values;

    CardColor(String name, Integer values) {
        this.name = name;
        this.values = values;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValues() {
        return values;
    }

    public void setValues(Integer values) {
        this.values = values;
    }
}
