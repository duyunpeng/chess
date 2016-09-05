package chess.application.feedback.command;

import chess.core.common.BasicPaginationCommand;
import chess.core.enums.HandleStatus;

/**
 * Created by Administrator on 2016/4/19.
 */
public class ListFeedbackCommand extends BasicPaginationCommand {
    private String email;       //联系邮箱
    private String phone;       //联系电话
    private String qq;          //联系QQ
    private HandleStatus status;//处理状态

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {this.email = email;}

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public HandleStatus getStatus() {
        return status;
    }

    public void setStatus(HandleStatus status) {
        this.status = status;
    }
}
