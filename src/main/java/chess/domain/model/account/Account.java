package chess.domain.model.account;


import chess.core.enums.EnableStatus;
import chess.core.id.ConcurrencySafeEntity;
import chess.domain.model.picture.Picture;
import chess.domain.model.role.Role;

import java.util.Date;
import java.util.List;

/**
 * Created by YJH on 2016/3/30.
 */
public class Account extends ConcurrencySafeEntity {

    private String userName;        //用户名
    private String password;        //密码
    private String salt;            //密码加密盐
    private String lastLoginIP;     //最后登录ip
    private Date lastLoginDate;     //最后登录时间
    private String lastLoginPlatform;//最后登录平台
    private List<Role> roles;               //用户角色
    private String email;           //邮箱
    private EnableStatus status;     //状态
    private Picture headPic;        //头像
    private String areaString;          //地区

    public void changeUserName(String userName) {
        this.userName = userName;
    }

    public void changePassword(String password) {
        this.password = password;
    }

    public void changeSalt(String salt) {
        this.salt = salt;
    }

    public void changeLastLoginIP(String lastLoginIP) {
        this.lastLoginIP = lastLoginIP;
    }

    public void changeLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public void changeLastLoginPlatform(String lastLoginPlatform) {
        this.lastLoginPlatform = lastLoginPlatform;
    }

    public void changeRoles(List<Role> roles) {
        this.roles = roles;
    }

    public void changeStatus(EnableStatus status) {
        this.status = status;
    }

    public void changeHeadPic(Picture headPic) {
        this.headPic = headPic;
    }

    public void changeEmail(String email) {
        this.email = email;
    }

    private void setUserName(String userName) {
        this.userName = userName;
    }

    private void setPassword(String password) {
        this.password = password;
    }

    private void setSalt(String salt) {
        this.salt = salt;
    }

    private void setLastLoginIP(String lastLoginIP) {
        this.lastLoginIP = lastLoginIP;
    }

    private void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    private void setLastLoginPlatform(String lastLoginPlatform) {
        this.lastLoginPlatform = lastLoginPlatform;
    }

    private void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    private void setStatus(EnableStatus status) {
        this.status = status;
    }

    private void setHeadPic(Picture headPic) {
        this.headPic = headPic;
    }

    private void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public String getPassword() {
        return password;
    }

    public String getSalt() {
        return salt;
    }

    public String getLastLoginIP() {
        return lastLoginIP;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public String getLastLoginPlatform() {
        return lastLoginPlatform;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public EnableStatus getStatus() {
        return status;
    }

    public Picture getHeadPic() {
        return headPic;
    }

    public String getEmail() {
        return email;
    }

    public String getAreaString() {
        return areaString;
    }

    public void setAreaString(String areaString) {
        this.areaString = areaString;
    }

    public Account() {
        super();
    }

    public Account(String userName, String password, String salt, String lastLoginIP, Date lastLoginDate, String lastLoginPlatform,
                   List<Role> roles, String email, EnableStatus status, Picture headPic, String areaString) {
        this.userName = userName;
        this.password = password;
        this.salt = salt;
        this.lastLoginIP = lastLoginIP;
        this.lastLoginDate = lastLoginDate;
        this.lastLoginPlatform = lastLoginPlatform;
        this.roles = roles;
        this.email = email;
        this.status = status;
        this.headPic = headPic;
        this.areaString = areaString;
        this.setCreateDate(new Date());
    }
}
