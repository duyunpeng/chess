package chess.application.moneydetailed.command;

import chess.core.common.BasicPaginationCommand;
import chess.core.enums.FlowType;

/**
 * Created by YJH
 * Date : 16-7-19.
 */
public class ListMoneyDetailedCommand extends BasicPaginationCommand {

    private String userName;
    private FlowType flowType;
    private String startDate;
    private String endDate;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public FlowType getFlowType() {
        return flowType;
    }

    public void setFlowType(FlowType flowType) {
        this.flowType = flowType;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
