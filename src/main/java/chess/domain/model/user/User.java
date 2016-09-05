package chess.domain.model.user;

import chess.core.enums.AuthStatus;
import chess.core.enums.EnableStatus;
import chess.core.enums.Sex;
import chess.domain.model.account.Account;
import chess.domain.model.area.Area;
import chess.domain.model.picture.Picture;
import chess.domain.model.role.Role;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 用户
 * Created by YJH on 2016/4/15.
 */
public class User extends Account {

    private String name;                //网名
    private Area area;                  //地区
    private BigDecimal money;           //金币
    private AuthStatus authStatus;      //认证状态
    private String payPassword;
    private Sex sex;
    private Landlords landlords;        //斗地主游戏数据
    private ThreeCard threeCard;        //扎金花游戏数据
    private String deviceNo;            //设备号
    private Boolean isVip;              //是否vip

    private void setName(String name) {
        this.name = name;
    }

    private void setArea(Area area) {
        this.area = area;
    }

    private void setMoney(BigDecimal money) {
        this.money = money;
    }

    private void setAuthStatus(AuthStatus authStatus) {
        this.authStatus = authStatus;
    }

    private void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    private void setSex(Sex sex) {
        this.sex = sex;
    }

    private void setLandlords(Landlords landlords) {
        this.landlords = landlords;
    }

    private void setThreeCard(ThreeCard threeCard) {
        this.threeCard = threeCard;
    }

    private void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    private void setVip(Boolean vip) {
        isVip = vip;
    }

    public String getName() {
        return name;
    }

    public Area getArea() {
        return area;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public AuthStatus getAuthStatus() {
        return authStatus;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public Sex getSex() {
        return sex;
    }

    public Landlords getLandlords() {
        return landlords;
    }

    public ThreeCard getThreeCard() {
        return threeCard;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public Boolean getVip() {
        return isVip;
    }

    public User() {
        super();
    }

    public User(String userName, String password, String salt, List<Role> roles, Picture picture, EnableStatus status, String deviceNo) {
        super(userName, password, salt, null, null, null, roles, null, status, picture, "");
        this.money = new BigDecimal(8);
        this.authStatus = AuthStatus.NOT;
        this.landlords = new Landlords();
        this.threeCard = new ThreeCard();
        this.deviceNo = deviceNo;
        this.isVip = false;
        this.setCreateDate(new Date());
    }

    public User(String name, Area area, BigDecimal gold, AuthStatus authStatus, String payPassword, String deviceNo) {
        this.name = name;
        this.area = area;
        this.money = gold;
        this.authStatus = authStatus;
        this.payPassword = payPassword;
        this.landlords = new Landlords();
        this.threeCard = new ThreeCard();
        this.deviceNo = deviceNo;
        this.isVip = false;
        this.setCreateDate(new Date());
    }

    public User(String userName, String password, String salt, String lastLoginIP, Date lastLoginDate, String lastLoginPlatform,
                List<Role> roles, String email, EnableStatus status, Picture headPic, String name, Area area, BigDecimal money,
                AuthStatus authStatus, String payPassword, String deviceNo) {
        super(userName, password, salt, lastLoginIP, lastLoginDate, lastLoginPlatform, roles, email, status, headPic, "");
        this.name = name;
        this.area = area;
        this.money = money;
        this.authStatus = authStatus;
        this.payPassword = payPassword;
        this.landlords = new Landlords();
        this.threeCard = new ThreeCard();
        this.deviceNo = deviceNo;
        this.isVip = false;
        this.setCreateDate(new Date());
    }

    public void changeName(String name) {
        this.name = name;
    }

    public void changeArea(Area area) {
        this.area = area;
    }

    public void changePayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    public void changeMoney(BigDecimal money) {
        this.money = money;
    }

    public void changeLandlords(Landlords landlords) {
        this.landlords = landlords;
    }

    public void changeThreecard(ThreeCard threeCard) {
        this.threeCard = threeCard;
    }

    public void changeDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public void changeVip(Boolean vip) {
        isVip = vip;
    }
}
