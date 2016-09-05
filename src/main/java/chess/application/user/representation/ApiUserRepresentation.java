package chess.application.user.representation;

import chess.application.account.representation.ApiAccountRepresentation;
import chess.application.area.representation.AreaRepresentation;
import chess.core.enums.AuthStatus;
import chess.core.enums.Sex;

import java.math.BigDecimal;

/**
 * Created by YJH on 2016/5/18.
 */
public class ApiUserRepresentation extends ApiAccountRepresentation {

    private String name;                //网名
    private AreaRepresentation area;                  //地区
    private BigDecimal money;           //金币
    private Sex sex;
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

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
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
}
