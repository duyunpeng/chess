package chess.game.bullfight.mode;

/**
 * Created by jm
 * Date : 16-6-20.
 */
public enum CardType {

    NoCattle("无牛", 0),//1
    CattleOne("牛一", 1),//1
    CattleTwo("牛二", 2),//1
    CattleThree("牛三", 3),//1
    CattleFour("牛四", 4),//1
    CattleFive("牛五", 5),//1
    CattleSix("牛六", 6),//1
    CattleSeven("牛七", 7),//2
    CattleEight("牛八", 8),//2
    CattleNine("牛九", 9),//2
    CattleCattle("牛牛", 10),//3
    SilverCattle("银牛", 11),//4
    GoldCattle("金牛", 12),//5
    Bomb("炸弹", 13),//7
    FiveLittleCattle("五小牛", 14);//10

    private String name;

    private Integer value;

    CardType(String name, Integer value) {
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

    public void setValues(Integer value) {
        this.value = value;
    }
}
