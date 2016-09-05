package chess.application.withdraw.representation;


import chess.core.enums.WithdrawStatus;
import chess.core.id.ConcurrencySafeEntity;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 提现
 * Created by pengyi on 2016/5/6.
 */
public class WithdrawRepresentation extends ConcurrencySafeEntity {

    private String username;                        //提现人姓名
    private String userId;                          //提现人id
    private Date createTime;                        //提现时间
    private BigDecimal money;                       //提现金额
    private WithdrawStatus status;                  //提现状态
    private Date finishTime;                        //完成时间

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
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

}
