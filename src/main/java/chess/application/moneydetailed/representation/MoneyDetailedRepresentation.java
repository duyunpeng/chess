package chess.application.moneydetailed.representation;

import chess.application.user.representation.UserRepresentation;
import chess.core.enums.FlowType;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by YJH
 * Date : 16-7-19.
 */
public class MoneyDetailedRepresentation {
    private String id;
    private Integer version;
    private Date createDate;

    private UserRepresentation user;      //用户
    private FlowType flowType;  //资金流向类型
    private BigDecimal money;   //金额
    private String description;     //说明（）
    private BigDecimal oldMoney;    //原有金额
    private BigDecimal newMoney;    //现有金额

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

    public FlowType getFlowType() {
        return flowType;
    }

    public void setFlowType(FlowType flowType) {
        this.flowType = flowType;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getOldMoney() {
        return oldMoney;
    }

    public void setOldMoney(BigDecimal oldMoney) {
        this.oldMoney = oldMoney;
    }

    public BigDecimal getNewMoney() {
        return newMoney;
    }

    public void setNewMoney(BigDecimal newMoney) {
        this.newMoney = newMoney;
    }
}
