package chess.application.report.command;

import org.hibernate.validator.constraints.NotBlank;
import chess.application.shared.command.SharedCommand;

import java.util.Date;


/**
 * Created by lvdi on 2016/4/19.
 */
public class EditReportCommand  extends SharedCommand {

    @NotBlank(message = "{report.handleDate.NotBlank.message}")
    private String handleResult;                   //处理结果说明

    private Date handleDate;                        //处理时间

    public Date getHandleDate() {
        return handleDate;
    }

    public void setHandleDate(Date handleDate) {
        this.handleDate = handleDate;
    }

    public String getHandleResult() {
        return handleResult;
    }

    public void setHandleResult(String handleResult) {
        this.handleResult = handleResult;
    }


}
