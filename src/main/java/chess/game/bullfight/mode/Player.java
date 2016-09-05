package chess.game.bullfight.mode;

import chess.application.user.representation.ApiUserRepresentation;
import chess.core.enums.Sex;

/**
 * Created by dyp
 * Date : 16-7-1.
 */
public class Player {
    private String name;//昵称
    private String userName;//用户名
    private String head;    //头像
    private Sex sex;        //性别
    private Integer winCount;//赢次数
    private Integer loseCount;//输次数
    private Integer money;//积分
    private int seatNo;//座位号

    public Player() {
    }

    public Player(ApiUserRepresentation user, Seat seat) {
        this.seatNo = seat.getSeatNo();
        this.userName = user.getUserName();
        this.name = user.getName();
        this.head = (null == user.getHeadPic() ? "" : user.getHeadPic().getPicPath());
        this.sex = user.getSex();
        if (user.getMoney() != null) {
            if (user.getMoney().intValue() == 0) {
                this.money = 1;
            } else {
                this.money = user.getMoney().intValue();
            }
        } else {
            this.money = 1;
        }
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

    public Integer getWinCount() {
        return winCount;
    }

    public void setWinCount(Integer winCount) {
        this.winCount = winCount;
    }

    public Integer getLoseCount() {
        return loseCount;
    }

    public void setLoseCount(Integer loseCount) {
        this.loseCount = loseCount;
    }

    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    public int getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(int seatNo) {
        this.seatNo = seatNo;
    }
}
