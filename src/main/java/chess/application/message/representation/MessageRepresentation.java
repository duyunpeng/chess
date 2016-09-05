package chess.application.message.representation;

import chess.application.user.representation.UserRepresentation;
import chess.core.enums.YesOrNoStatus;

import java.util.Date;

/**
 * Created by YJH
 * Date : 16-7-20.
 */
public class MessageRepresentation {

    private String id;
    private Integer version;
    private Date createDate;

    private UserRepresentation receiveUser;//接收用户
    private Date readDate;//阅读时间
    private YesOrNoStatus readStatus;//是否阅读
    private YesOrNoStatus delStatus;//是否删除
    private String content;//内容

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

    public UserRepresentation getReceiveUser() {
        return receiveUser;
    }

    public void setReceiveUser(UserRepresentation receiveUser) {
        this.receiveUser = receiveUser;
    }

    public Date getReadDate() {
        return readDate;
    }

    public void setReadDate(Date readDate) {
        this.readDate = readDate;
    }

    public YesOrNoStatus getReadStatus() {
        return readStatus;
    }

    public void setReadStatus(YesOrNoStatus readStatus) {
        this.readStatus = readStatus;
    }

    public YesOrNoStatus getDelStatus() {
        return delStatus;
    }

    public void setDelStatus(YesOrNoStatus delStatus) {
        this.delStatus = delStatus;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
