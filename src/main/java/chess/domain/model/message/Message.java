package chess.domain.model.message;

import chess.core.enums.YesOrNoStatus;
import chess.core.id.ConcurrencySafeEntity;
import chess.domain.model.user.User;

import java.util.Date;

/**
 * Created by YJH
 * Date : 16-7-20.
 */
public class Message extends ConcurrencySafeEntity {

    private User receiveUser;//接收用户
    private Date readDate;//阅读时间
    private YesOrNoStatus readStatus;//是否阅读
    private YesOrNoStatus delStatus;//是否删除
    private String content;//内容

    private void setReceiveUser(User receiveUser) {
        this.receiveUser = receiveUser;
    }

    private void setReadDate(Date readDate) {
        this.readDate = readDate;
    }

    private void setReadStatus(YesOrNoStatus readStatus) {
        this.readStatus = readStatus;
    }

    private void setDelStatus(YesOrNoStatus delStatus) {
        this.delStatus = delStatus;
    }

    private void setContent(String content) {
        this.content = content;
    }

    public User getReceiveUser() {
        return receiveUser;
    }

    public Date getReadDate() {
        return readDate;
    }

    public YesOrNoStatus getReadStatus() {
        return readStatus;
    }

    public YesOrNoStatus getDelStatus() {
        return delStatus;
    }

    public String getContent() {
        return content;
    }

    public Message() {
        super();
    }

    public Message(User receiveUser, Date readDate, YesOrNoStatus readStatus, YesOrNoStatus delStatus, String content) {
        this.receiveUser = receiveUser;
        this.readDate = readDate;
        this.readStatus = readStatus;
        this.delStatus = delStatus;
        this.content = content;
        this.setCreateDate(new Date());
    }
}
