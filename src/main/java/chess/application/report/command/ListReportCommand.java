package chess.application.report.command;

import chess.core.common.BasicPaginationCommand;
import chess.core.enums.HandleStatus;


/**
 * Created by lvdi on 2016/4/19.
 */
public class ListReportCommand extends BasicPaginationCommand {

    private String reportUser;                        //举报人

    private String quiltReportUser;                   //被举报人

    private HandleStatus handleStatus;                    //处理状态

    public String getReportUser() {
        return reportUser;
    }

    public void setReportUser(String reportUser) {
        this.reportUser = reportUser;
    }

    public String getQuiltReportUser() {
        return quiltReportUser;
    }

    public void setQuiltReportUser(String quiltReportUser) {
        this.quiltReportUser = quiltReportUser;
    }

    public HandleStatus getHandleStatus() {
        return handleStatus;
    }

    public void setHandleStatus(HandleStatus handleStatus) {
        this.handleStatus = handleStatus;
    }
}
