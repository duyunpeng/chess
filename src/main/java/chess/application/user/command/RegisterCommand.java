package chess.application.user.command;

/**
 * Created by YJH on 2016/4/28.
 */
public class RegisterCommand {

    private String userName;        //用户名
    private String password;        //密码
    private String verificationCode;//验证码
    private String deviceNo;        //设备号

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(String verificationCode) {
        this.verificationCode = verificationCode;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }
}
