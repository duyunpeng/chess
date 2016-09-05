package chess.application.recharge.command;

import chess.core.common.BasicPaginationCommand;
import chess.core.enums.FlowType;
import chess.core.enums.YesOrNoStatus;

/**
 * Created by YJH
 * Date : 16-7-19.
 */
public class ListRechargeCommand extends BasicPaginationCommand {

    private String userName;
    private YesOrNoStatus isSuccess;
    private String startDate;
    private String endDate;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public YesOrNoStatus getIsSuccess() {
        return isSuccess;
    }

    public void setIsSuccess(YesOrNoStatus isSuccess) {
        this.isSuccess = isSuccess;
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
