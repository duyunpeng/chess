package chess.application.appversion.command;

import chess.core.common.BasicPaginationCommand;
import chess.core.enums.EnableStatus;

/**
 * Created by lvdi on 2016/4/19.
 */
public class ListAppVersionCommand extends BasicPaginationCommand {


    private String appVersion;  //app版本号
    private EnableStatus status;    //状态

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public EnableStatus getStatus() {
        return status;
    }

    public void setStatus(EnableStatus status) {
        this.status = status;
    }
}
