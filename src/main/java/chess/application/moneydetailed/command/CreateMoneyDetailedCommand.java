package chess.application.moneydetailed.command;

import chess.core.enums.FlowType;

import java.math.BigDecimal;

/**
 * Created by yjh on 16-7-11.
 */
public class CreateMoneyDetailedCommand {

    private String userName;
    private BigDecimal money;
    private FlowType flowType;  //资金流向类型
    private String description;     //说明

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public FlowType getFlowType() {
        return flowType;
    }

    public void setFlowType(FlowType flowType) {
        this.flowType = flowType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
