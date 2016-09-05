package chess.core.enums;

/**
 * Created by YJH on 2016/4/15.
 */
public enum AuthStatus {

    ALL("全部", 0, Boolean.TRUE),
    NOT("未认证", 1, Boolean.FALSE),
    IN("认证中", 2, Boolean.FALSE),
    SUCCESS("认证成功", 3, Boolean.FALSE),
    FAILURE("认证失败", 4, Boolean.FALSE);

    AuthStatus(String name, int value, Boolean onlyQuery) {
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
