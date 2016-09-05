package chess.game.landlords.mode;

/**
 * Created by YJH
 * Date : 16-6-16.
 */
public enum Action {

    NONE("没操作", 0),
    TRUE("真", 1),
    FALSE("假", 2);

    private String name;
    private Integer value;

    Action(String name, Integer value) {
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
