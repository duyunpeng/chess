package chess.core.enums;

/**
 * Created by YJH on 2016/3/14.
 */
public enum HandleStatus {

    ALL("全部", 0, Boolean.TRUE),
    WAIT_HANDLE("待处理", 1, Boolean.FALSE),
    IN_HANDLE("处理中", 2, Boolean.FALSE),
    OK_HANDLE("处理完成", 3, Boolean.FALSE);

    HandleStatus(String name, int value, Boolean onlyQuery) {
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
