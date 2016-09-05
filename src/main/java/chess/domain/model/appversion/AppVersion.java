package chess.domain.model.appversion;

import chess.core.enums.EnableStatus;
import chess.core.id.ConcurrencySafeEntity;

import java.util.Date;

/**
 * app版本
 * Created by YJH on 2016/4/15.
 */
public class AppVersion extends ConcurrencySafeEntity {

    private String appVersion;  //app版本号
    private Date updateDate;    //更新时间
    private String updateContent;   //更新内容
    private EnableStatus status;    //状态

    public void changeStatus(EnableStatus status) {
        this.status = status;
    }

    public void changeUpdateContent(String updateContent) {
        this.updateContent =updateContent;
    }

    public void changeAppVersion(String appVersion){this.appVersion=appVersion;}

    private void setAppVersion(String appVersion) {
        this.appVersion = appVersion;
    }

    private void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    private void setUpdateContent(String updateContent) {
        this.updateContent = updateContent;
    }

    private void setStatus(EnableStatus status) {
        this.status = status;
    }

    public String getAppVersion() {
        return appVersion;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public String getUpdateContent() {
        return updateContent;
    }

    public EnableStatus getStatus() {
        return status;
    }

    public AppVersion() {
        super();
    }

    public AppVersion(String appVersion, Date updateDate, String updateContent, EnableStatus status) {
        this.appVersion = appVersion;
        this.updateDate = updateDate;
        this.updateContent = updateContent;
        this.status = status;
        this.setCreateDate(new Date());
    }
}
