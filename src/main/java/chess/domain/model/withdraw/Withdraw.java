package chess.domain.model.withdraw;


import chess.core.enums.WithdrawStatus;
import chess.core.id.ConcurrencySafeEntity;
import chess.domain.model.user.User;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 提现
 * Created by pengyi on 2016/5/6.
 */
public class Withdraw extends ConcurrencySafeEntity {

    private User user;                      //提现人
    private BigDecimal money;                       //提现金额
    private WithdrawStatus status;                  //提现状态
    private Date finishTime;                        //完成时间

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public WithdrawStatus getStatus() {
        return status;
    }

    public void setStatus(WithdrawStatus status) {
        this.status = status;
    }

    public Date getFinishTime() {
        return finishTime;
    }

    public void setFinishTime(Date finishTime) {
        this.finishTime = finishTime;
    }

    public Withdraw() {
        super();
    }

    public Withdraw(User user, BigDecimal money, WithdrawStatus status, Date finishTime) {
        super();
        this.user = user;
        this.money = money;
        this.status = status;
        this.finishTime = finishTime;
    }

}
