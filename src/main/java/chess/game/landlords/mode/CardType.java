package chess.game.landlords.mode;

/**
 * Created by YJH
 * Date : 16-6-20.
 */
public enum CardType {
    DANPAI("单牌", 1),
    DUIZI("对子", 2),
    SANZHANG("三张", 3),
    SANDAIYI("三带一", 4),
    SUNZI("顺子", 5),
    SHUANGSHUN("双顺", 6),
    SANSHUN("三顺", 7),
    FEIJI("飞机", 8),
    SIDAIER("四带二", 9),
    ZHADAN("炸弹", 10),
    HUOJIAN("火箭", 11),
    ERROR("牌型错误", 12);

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

    public void setValue(Integer value) {
        this.value = value;
    }
}
