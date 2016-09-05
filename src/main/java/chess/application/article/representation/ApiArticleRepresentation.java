package chess.application.article.representation;


import chess.core.enums.EnableStatus;

import java.util.Date;

/**
 * Created by YJH on 2016/5/18.
 */
public class ApiArticleRepresentation {

    private String id;
    private Integer version;
    private Date createDate;

    private String title;           //文章标题
    private String account;        //发布人
    private String userName;        //发布人账号
    private String content;         //文章内容
    private EnableStatus status;    //文章状态

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public EnableStatus getStatus() {
        return status;
    }

    public void setStatus(EnableStatus status) {
        this.status = status;
    }
}
