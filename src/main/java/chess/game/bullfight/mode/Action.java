package chess.game.bullfight.mode;

/**
 * Created by jm
 * Date : 16-6-20.
 */
public enum Action {

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
