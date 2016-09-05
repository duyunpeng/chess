package chess.domain.model.withhold;

import chess.core.id.ConcurrencySafeEntity;
import chess.domain.model.user.User;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 提现
 * Created by yjh on 16-7-9.
 */
public class Withhold extends ConcurrencySafeEntity {

    private User user;                      //扣款人
    private BigDecimal money;                       //扣款金额
    private String detail;                          //扣款说明

    private void setUser(User user) {
        this.user = user;
    }

    private void setMoney(BigDecimal money) {
        this.money = money;
    }

    private void setDetail(String detail) {
        this.detail = detail;
    }

    public User getUser() {
        return user;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public String getDetail() {
        return detail;
    }

    public Withhold() {
        super();
    }

    public Withhold(User user, BigDecimal money, String detail) {
        this.user = user;
        this.money = money;
        this.detail = detail;
        this.setCreateDate(new Date());
    }
}
