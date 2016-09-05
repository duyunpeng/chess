package chess.game.landlords.mode;

import chess.application.user.representation.ApiUserRepresentation;
import chess.core.enums.Sex;

import java.math.BigDecimal;

/**
 * Created by YJH
 * Date : 16-6-27.
 */
public class Player {

    private String name;//昵称
    private String userName;//用户名
    private String head;    //头像
    private Sex sex;        //性别
    private int winCount;//赢次数
    private int loseCount;//输次数
    private BigDecimal money;//积分
    private String areaString;

    public Player() {
    }

    public Player(ApiUserRepresentation user) {
        this.name = user.getName();
        this.userName = user.getUserName();
        this.head = (null == user.getHeadPic() ? "" : user.getHeadPic().getPicPath());
        this.sex = user.getSex();
        this.money = user.getMoney();
        this.areaString = user.getAreaString();
        this.winCount = user.getLandlordsWinCount();
        this.loseCount = user.getLandlordsLoseCount();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public int getWinCount() {
        return winCount;
    }

    public void setWinCount(int winCount) {
        this.winCount = winCount;
    }

    public int getLoseCount() {
        return loseCount;
    }

    public void setLoseCount(int loseCount) {
        this.loseCount = loseCount;
    }

    public BigDecimal getMoney() {
        return money;
    }

    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public String getAreaString() {
        return areaString;
    }

    public void setAreaString(String areaString) {
        this.areaString = areaString;
    }
}
