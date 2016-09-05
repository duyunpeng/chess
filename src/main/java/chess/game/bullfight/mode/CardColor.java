package chess.game.bullfight.mode;

/**
 * Created by jm
 * Date : 16-6-20.
 */
public enum CardColor {

    SPADE("黑桃", 1),
    HEART("红桃", 2),
    PLUM("梅花", 3),
    BLOCK("方块", 4),;

    private String name;

    private Integer value;

    CardColor(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
