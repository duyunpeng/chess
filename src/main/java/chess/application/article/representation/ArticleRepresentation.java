package chess.application.article.representation;

import chess.application.account.representation.AccountRepresentation;
import chess.core.enums.EnableStatus;

import java.util.Date;

/**
 * Created by YJH on 2016/4/19.
 */
public class ArticleRepresentation {

    private String id;
    private Integer version;
    private Date createDate;

    private String title;           //文章标题
    private AccountRepresentation account;        //发布人
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

    public AccountRepresentation getAccount() {
        return account;
    }

    public void setAccount(AccountRepresentation account) {
        this.account = account;
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
