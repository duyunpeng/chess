package chess.application.user.representation;

import chess.application.account.representation.AccountRepresentation;
import chess.application.area.representation.AreaRepresentation;
import chess.core.enums.AuthStatus;
import chess.core.enums.Sex;

import java.math.BigDecimal;

/**
 * Created by YJH on 2016/4/19.
 */
public class UserRepresentation extends AccountRepresentation {

    private String name;                //网名
    private AreaRepresentation area;                  //地区
    private BigDecimal money;           //金币
    private AuthStatus authStatus;      //认证状态
    private String payPassword;
    private Sex sex;
    private String deviceNo;            //设备号
    private Boolean isVip;              //是否vip
    private Integer landlordsWinCount;  //斗地主赢次数
    private Integer landlordsLoseCount; //斗地主输次数
    private Integer threecareWinCount;
    private Integer threecareLoseCount;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AreaRepresentation getArea() {
        return area;
    }

    public void setArea(AreaRepresentation area) {
        this.area = area;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public AuthStatus getAuthStatus() {
        return authStatus;
    }

    public void setAuthStatus(AuthStatus authStatus) {
        this.authStatus = authStatus;
    }

    public String getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(String payPassword) {
        this.payPassword = payPassword;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public String getDeviceNo() {
        return deviceNo;
    }

    public void setDeviceNo(String deviceNo) {
        this.deviceNo = deviceNo;
    }

    public Boolean getVip() {
        return isVip;
    }

    public void setVip(Boolean vip) {
        isVip = vip;
    }

    public Integer getLandlordsWinCount() {
        return landlordsWinCount;
    }

    public void setLandlordsWinCount(Integer landlordsWinCount) {
        this.landlordsWinCount = landlordsWinCount;
    }

    public Integer getLandlordsLoseCount() {
        return landlordsLoseCount;
    }

    public void setLandlordsLoseCount(Integer landlordsLoseCount) {
        this.landlordsLoseCount = landlordsLoseCount;
    }

    public Integer getThreecareWinCount() {
        return threecareWinCount;
    }

    public void setThreecareWinCount(Integer threecareWinCount) {
        this.threecareWinCount = threecareWinCount;
    }

    public Integer getThreecareLoseCount() {
        return threecareLoseCount;
    }

    public void setThreecareLoseCount(Integer threecareLoseCount) {
        this.threecareLoseCount = threecareLoseCount;
    }
}
