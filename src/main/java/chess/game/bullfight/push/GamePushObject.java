package chess.game.bullfight.push;

/**
 * Created by dyp
 * Date : 16-6-20.
 */
public enum GamePushObject {

    ONE("重新连接", 10008),
    TWO("已经在游戏中", 2),
    THREE("配桌完成", 10014),
    FOUR("下注", 10005),
    FIVE("没人抢庄，随机选择庄家", 5),
    SIX("是否抢庄", 6),
    SEVEN("庄家出来了", 10004),
    EIGHT("进来或者离开一个玩家", 10002),
    NINE("发牌", 10003),
    TEN("游戏结束", 10006),
    ELEVEN("离开成功", 10010),
    TWELVE("断线", 10007),
    THIRTEEN("当有玩家离开后,所获得的金币", 10009),
    FOURTEEN("取消配桌成功", 10011),
    FIFTEEN("金币不足,不能进入游戏",10012),
    SIXTEEN("金币足够,可以进入游戏",10013);

    private String name;
    private Integer value;

    GamePushObject(String name, Integer value) {
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
