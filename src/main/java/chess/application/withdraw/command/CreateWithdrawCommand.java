package chess.application.withdraw.command;

import java.math.BigDecimal;

/**
 * Created by pengyi on 2016/5/6.
 */
public class CreateWithdrawCommand {

    private String userId;
    private BigDecimal money;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }
}
