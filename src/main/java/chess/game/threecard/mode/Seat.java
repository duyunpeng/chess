package chess.game.threecard.mode;

import java.math.BigDecimal;
import java.util.List;

/**
 * Author pengyi
 * Create date 16-6-21
 */
public class Seat {

    private int seatNo;                         //座位号
    private String userName;                    //用户名
    private String name;                        //网名
    private String headPic;                     //头像
    private BigDecimal gold;                    //金币
    private List<Card> cards;                   //牌
    private boolean look;                       //是否看牌
    private boolean ready;                      //是否准备
    private boolean end;                        //是否结束
    private BigDecimal score;                   //下注
    private String areaString;                  //地区
//    private boolean exit;                       //是否退出

    public int getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(int seatNo) {
        this.seatNo = seatNo;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public BigDecimal getGold() {
        return gold;
    }

    public void setGold(BigDecimal gold) {
        this.gold = gold;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public boolean isLook() {
        return look;
    }

    public void setLook(boolean look) {
        this.look = look;
    }

    public boolean isReady() {
        return ready;
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    public boolean isEnd() {
        return end;
    }

    public void setEnd(boolean end) {
        this.end = end;
    }

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public String getAreaString() {
        return areaString;
    }

    public void setAreaString(String areaString) {
        this.areaString = areaString;
    }
    //    public boolean isExit() {
//        return exit;
//    }
//
//    public void setExit(boolean exit) {
//        this.exit = exit;
//    }

    public Seat() {
        look = false;
        ready = false;
        end = false;
//        exit = false;
    }
}
