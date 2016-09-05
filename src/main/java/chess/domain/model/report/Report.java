package chess.domain.model.report;

import chess.core.enums.HandleStatus;
import chess.core.id.ConcurrencySafeEntity;
import chess.domain.model.picture.Picture;
import chess.domain.model.user.User;

import java.util.Date;
import java.util.List;

/**
 * 举报
 * Created by YJH on 2016/4/15.
 */
public class Report extends ConcurrencySafeEntity {

    private User reportUser;                        //举报人
    private User quiltReportUser;                   //被举报人
    private String title;                           //举报标题
    private String content;                         //举报内容
    private List<Picture> pictures;                 //图片
    private HandleStatus handleStatus;                    //处理状态
    private String handleResult;                   //处理结果说明
    private Date handleDate;                        //处理时间

    public void changeHandleStatus(HandleStatus handleStatus) {
        this.handleStatus = handleStatus;
    }

    public void changeHandleDate(Date handleDate) {
        this.handleDate = handleDate;
    }

    public void changeHandleResult(String handleResult) {
        this.handleResult = handleResult;
    }

    private void setReportUser(User reportUser) {
        this.reportUser = reportUser;
    }

    private void setQuiltReportUser(User quiltReportUser) {
        this.quiltReportUser = quiltReportUser;
    }

    private void setTitle(String title) {
        this.title = title;
    }

    private void setContent(String content) {
        this.content = content;
    }

    private void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
    }

    private void setHandleStatus(HandleStatus handleStatus) {
        this.handleStatus = handleStatus;
    }

    private void setHandleResult(String handleResult) {
        this.handleResult = handleResult;
    }

    private void setHandleDate(Date handleDate) {
        this.handleDate = handleDate;
    }

    public User getReportUser() {
        return reportUser;
    }

    public User getQuiltReportUser() {
        return quiltReportUser;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public List<Picture> getPictures() {
        return pictures;
    }

    public HandleStatus getHandleStatus() {
        return handleStatus;
    }

    public String getHandleResult() {
        return handleResult;
    }

    public Date getHandleDate() {
        return handleDate;
    }

    public Report() {
        super();
    }

    public Report(User reportUser, User quiltReportUser, String title, String content, List<Picture> pictures, HandleStatus handleStatus, String handleResult, Date handleDate) {
        this.reportUser = reportUser;
        this.quiltReportUser = quiltReportUser;
        this.title = title;
        this.content = content;
        this.pictures = pictures;
        this.handleStatus = handleStatus;
        this.handleResult = handleResult;
        this.handleDate = handleDate;
        this.setCreateDate(new Date());
    }
}
