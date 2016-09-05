package chess.game.landlords.mode;

/**
 * Created by YJH
 * Date : 16-6-30.
 */
public enum GameStatus {

    CALL("叫地主", 1),
    ROB("抢地主", 2),
    DOUBLE("加倍", 3),
    PLAY("出牌", 4),
    OVER("结束", 5);

    private String name;
    private Integer values;

    GameStatus(String name, Integer values) {
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
