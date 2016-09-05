package chess.application.feedback.command;

import org.hibernate.validator.constraints.NotBlank;
import chess.core.enums.HandleStatus;

import javax.validation.constraints.NotNull;

/**
 * Created by dyp on 2016/4/19.
 */
public class CreateFeedBackCommand {
    @NotBlank(message = "{feedback.email.NotBlank.message}")
    private String email;       //联系邮箱
    @NotBlank(message = "{feedback.phone.NotBlank.message}")
    private String phone;       //联系电话
    @NotBlank(message = "{feedback.qq.NotBlank.message}")
    private String qq;          //联系QQ
    @NotBlank(message = "{feedback.content.NotBlank.message}")
    private String content;     //意见内容
    @NotNull(message = "{feedback.status.NotNull.messages}")
    private HandleStatus status;//处理状态

    public String getPhone() {return phone;}

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public HandleStatus getStatus() {
        return status;
    }

    public void setStatus(HandleStatus status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
