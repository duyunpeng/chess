package chess.application.article.representation;

import chess.core.enums.EnableStatus;

import java.util.Date;

/**
 * Created by YJH on 2016/4/19.
 */
public class ArticleTypeRepresentation {

    private String id;
    private Integer version;
    private Date createDate;

    private String name;
    private EnableStatus status;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public EnableStatus getStatus() {
        return status;
    }

    public void setStatus(EnableStatus status) {
        this.status = status;
    }
}
