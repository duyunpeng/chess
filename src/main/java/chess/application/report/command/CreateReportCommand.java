package chess.application.report.command;

import org.hibernate.validator.constraints.NotBlank;
import chess.domain.model.picture.Picture;

import java.util.List;

/**
 * Created by lvdi on 2016/4/19.
 */
public class CreateReportCommand {

    @NotBlank(message = "{report.name.NotBlank.message}")
    private String reportUser;                        //举报人

    @NotBlank(message = "{report.quiltReportUser.NotBlank.message}")
    private String quiltReportUser;                   //被举报人

    @NotBlank(message = "{report.title.NotBlank.message}")
    private String title;                           //举报标题

    @NotBlank(message = "{report.content.NotBlank.message}")
    private String content;                         //举报内容

    @NotBlank(message = "{report.pictures.NotBlank.message}")
    private List<Picture> pictures;                 //图片

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

    public List<Picture> getPictures() {
        return pictures;
    }

    public void setPictures(List<Picture> pictures) {
        this.pictures = pictures;
    }
}
