package chess.domain.model.feedback;

import chess.core.enums.HandleStatus;
import chess.core.id.ConcurrencySafeEntity;

import java.util.Date;

/**
 * 意见反馈
 * Created by YJH on 2016/4/15.
 */
public class FeedBack extends ConcurrencySafeEntity {

    private String email;       //联系邮箱
    private String phone;       //联系电话
    private String qq;          //联系QQ
    private String content;     //意见内容
    private HandleStatus status;//处理状态

    public void changeEmail(String email){this.email=email;}
    public void changePhone(String phone){this.phone=phone;}
    public void changeQq(String qq){this.qq=qq;}
    public void changeContent(String content){this.content=content;}
    public void changeStatus(HandleStatus status){this.status=status;}


    private void setContent(String content) {this.content = content;}
    private void setEmail(String email) {this.email = email;}
    private void setPhone(String phone) {this.phone = phone;}
    private void setQq(String qq) {this.qq = qq;}
    private void setStatus(HandleStatus status) {this.status = status;}

    public String getContent() {return content;}
    public String getEmail() {return email;}
    public String getPhone() {return phone;}
    public String getQq() {return qq;}
    public HandleStatus getStatus() {return status;}

    public FeedBack() {super();}

    public FeedBack(String content, String phone, String email, String qq, HandleStatus status) {
        this.content = content;
        this.phone = phone;
        this.email = email;
        this.qq = qq;
        this.status = status;
        this.setCreateDate(new Date());
    }
}
