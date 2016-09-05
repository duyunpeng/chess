package chess.core.enums;

/**
 * Created by yjh on 16-6-23.
 */
public enum Sex {
    ALL("全部", 0, Boolean.FALSE),
    MAN("男", 1, Boolean.TRUE),
    WOMAN("女", 2, Boolean.TRUE);

    Sex(String name, int value, Boolean onlyQuery) {
        this.name = name;
        this.value = value;
        this.onlyQuery = onlyQuery;
    }

    private String name;

    private int value;

    private Boolean onlyQuery;                  // 仅用于页面查询和业务逻辑无关

    public String getName() {
        return name;
    }

    public Integer getValue() {
        return value;
    }

    public Boolean isOnlyQuery() {
        return onlyQuery;
    }
}
