package chess.core.enums;

/**
 * Created by yjh on 16-7-5.
 */
public enum GameType {

    LANDLORDS("斗地主", 1),
    THREE_CARD("扎金花", 2),
    BULLFIGHT("斗牛", 3);

    GameType(String name, int value) {
        this.name = name;
        this.value = value;
    }

    private String name;

    private int value;

    public String getName() {
        return name;
    }

    public Integer getValue() {
        return value;
    }

}
