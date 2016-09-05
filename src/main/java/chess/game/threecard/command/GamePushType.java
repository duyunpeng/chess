package chess.game.threecard.command;

/**
 * Created date 2016/6/22
 * Author pengyi
 */
public enum GamePushType {

    CONNECTION("进入或者离开", -1),
    READY("准备", 0),
    START("开始", 1),
    LOOK("看牌", 2),
    PLAY("下注", 3),
    COMPARE("比牌", 4),
    ABANDON("放弃", 5),
    SCORE("比分", 6),
    EXIT("退出", 7),
    RECONNECTION("断线重连", 8),
    OPERATION("操作", 9);

    private String name;
    private Integer value;

    GamePushType(String name, Integer value) {
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
