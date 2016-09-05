package chess.application.appversion.command;

import org.hibernate.validator.constraints.NotBlank;
import chess.application.shared.command.SharedCommand;
import chess.core.enums.EnableStatus;

import javax.validation.constraints.NotNull;


/**
 * Created by lvdi on 2016/4/19.
 */
public class EditAppVersionCommand extends SharedCommand {

    @NotBlank(message = "{appVersion.appVersion.NotBlank.message}")
    private String appVersion;  //app版本号

    @NotBlank(message = "{appVersion.updateContent.NotBlank.message}")
    private String updateContent;   //更新内容

    @NotNull(message = "{appVersion.status.NotBlank.message}")
    private EnableStatus status;    //状态

    public EnableStatus getStatus() {
        return status;
    }

    public void setStatus(EnableStatus status) {
        this.status = status;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    public String getUpdateContent() {
        return updateContent;
    }

    public void setUpdateContent(String updateContent) {
        this.updateContent = updateContent;
    }
}
