package chess.application.shared.command;

/**
 * Created by YJH on 2016/4/28.
 */
public class SmsCommand {

    private String phone;

    private String verificationCode;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }
}
