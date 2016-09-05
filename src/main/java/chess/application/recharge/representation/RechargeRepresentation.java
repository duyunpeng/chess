package chess.application.recharge.representation;

import chess.application.user.representation.UserRepresentation;
import chess.core.enums.PayType;
import chess.core.enums.YesOrNoStatus;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by YJH
 * Date : 16-7-19.
 */
public class RechargeRepresentation {

    private String id;
    private Integer version;
    private Date createDate;

    private UserRepresentation user;              //用户
    private BigDecimal money;       //金额
    private PayType payType;        //支付方式
    private YesOrNoStatus isSuccess;//是否成功
    private String token_id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public UserRepresentation getUser() {
        return user;
    }

    public void setUser(UserRepresentation user) {
        this.user = user;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public PayType getPayType() {
        return payType;
    }

    public void setPayType(PayType payType) {
        this.payType = payType;
    }

    public YesOrNoStatus getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(YesOrNoStatus isSuccess) {
        this.isSuccess = isSuccess;
    }

    public String getToken_id() {
        return token_id;
    }

    public void setToken_id(String token_id) {
        this.token_id = token_id;
    }
}
