package chess.application.report.representation;

import chess.application.account.representation.AccountRepresentation;
import chess.application.picture.representation.PictureRepresentation;
import chess.core.enums.HandleStatus;

import java.util.Date;
import java.util.List;

/**
 * Created by LvDi on 2016/4/19.
 */
public class ReportRepresentation {

    private String id;
    private Integer version;
    private Date createDate;

    private AccountRepresentation reportUser;                        //举报人
    private AccountRepresentation quiltReportUser;                   //被举报人
    private String title;                           //举报标题
    private String content;                         //举报内容
    private List<PictureRepresentation> pictures;                 //图片
    private HandleStatus handleStatus;                    //处理状态
    private String handleResult;                   //处理结果说明
    private Date handleDate;                        //处理时间



    public void setQuiltReportUser(AccountRepresentation quiltReportUser) {
        this.quiltReportUser = quiltReportUser;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<PictureRepresentation> getPictures() {
        return pictures;
    }

    public void setPictures(List<PictureRepresentation> pictures) {
        this.pictures = pictures;
    }

    public HandleStatus getHandleStatus() {
        return handleStatus;
    }

    public void setHandleStatus(HandleStatus handleStatus) {
        this.handleStatus = handleStatus;
    }

    public String getHandleResult() {
        return handleResult;
    }

    public void setHandleResult(String handleResult) {
        this.handleResult = handleResult;
    }

    public Date getHandleDate() {
        return handleDate;
    }

    public void setHandleDate(Date handleDate) {
        this.handleDate = handleDate;
    }


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

    public AccountRepresentation getReportUser() {
        return reportUser;
    }

    public void setReportUser(AccountRepresentation reportUser) {
        this.reportUser = reportUser;
    }

    public AccountRepresentation getQuiltReportUser() {
        return quiltReportUser;
    }
}
