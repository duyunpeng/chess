package chess.game.landlords.command;

/**
 * 斗地主游戏推送code
 * Created by yjh on 16-6-14.
 */
public enum GamePushType {

    GAME_21001("开始游戏", 21001),
    GAME_21002("逃跑", 21002),
    GAME_21003("叫地主状态", 21003),
    GAME_21004("抢地主状态", 21004),
    GAME_21005("确认地主", 21005),
    GAME_21006("加倍状态", 21006),
    GAME_21007("出牌", 21007),
    GAME_21008("托管", 21008),
    GAME_21009("没人叫地主,洗牌重来", 21009),
    GAME_21010("断线重连", 21010),
    GAME_21011("牌型错误", 21011),
    GAME_21012("手中没有这牌", 21012),
    GAME_21013("金币不够", 21013),
    GAME_21014("不该你操作", 21014),
    GAME_21015("已经操作过了", 21015),
    GAME_21016("错误的操作", 21016),
    GAME_21017("获取上一轮牌", 21017),

    //斗地主游戏结束阶段22100
    GAME_OVER("游戏结束", 21101),                //结算:所有玩家当前money,输赢分数,输赢状态,座位号
    GAME_21102("离开桌", 21102);


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
