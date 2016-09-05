package chess.core.enums;

/**
 * Created by pengyi on 2016/5/6.
 */
public enum WithdrawStatus {

    PENDING("待处理", 0, Boolean.FALSE),
    FINISH("处理完成", 1, Boolean.FALSE);
    private String name;
    private int value;
    private Boolean onlyQuery;

    WithdrawStatus(String name, int value, Boolean onlyQuery) {
        this.name = name;
        this.value = value;
        this.onlyQuery = onlyQuery;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public Boolean isOnlyQuery() {
        return onlyQuery;
    }


}
