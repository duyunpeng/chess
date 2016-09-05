package chess.domain.model.recharge;

import chess.core.enums.PayType;
import chess.core.enums.YesOrNoStatus;
import chess.core.id.ConcurrencySafeEntity;
import chess.domain.model.user.User;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 充值
 * <p>
 * Created by yjh on 16-7-9.
 */
public class Recharge extends ConcurrencySafeEntity {

    private User user;              //用户
    private BigDecimal money;       //金额
    private YesOrNoStatus isSuccess;//是否成功
    private PayType payType;        //支付类型
    private String token_id;
    private String payNo;
    private Date payTime;

    private void setUser(User user) {
        this.user = user;
    }

    private void setMoney(BigDecimal money) {
        this.money = money;
    }

    public void setIsSuccess(YesOrNoStatus isSuccess) {
        this.isSuccess = isSuccess;
    }

    public User getUser() {
        return user;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public YesOrNoStatus getIsSuccess() {
        return isSuccess;
    }

    public PayType getPayType() {
        return payType;
    }

    public void setPayType(PayType payType) {
        this.payType = payType;
    }

    public String getToken_id() {
        return token_id;
    }

    public void setToken_id(String token_id) {
        this.token_id = token_id;
    }

    public String getPayNo() {
        return payNo;
    }

    public void setPayNo(String payNo) {
        this.payNo = payNo;
    }

    public Date getPayTime() {
        return payTime;
    }

    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    public Recharge() {
        super();
    }

    public Recharge(User user, BigDecimal money, YesOrNoStatus isSuccess, PayType payType) {
        this.user = user;
        this.money = money;
        this.isSuccess = isSuccess;
        this.payType = payType;
        this.setCreateDate(new Date());
    }
}
