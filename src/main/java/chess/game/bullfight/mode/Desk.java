package chess.game.bullfight.mode;

import chess.application.user.representation.ApiUserRepresentation;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jm
 * Date : 16-6-20.
 */
public class Desk {

    private int baseScore; //基础分
    private String deskNo;  //桌号
    private List<Seat> seats = new ArrayList<>();//座位
    private List<Card> surplusCard = new ArrayList<>();//剩余下的牌
    public List<Card> getSurplusCard() {
        return surplusCard;
    }
    public void setSurplusCard(List<Card> surplusCard) {
        this.surplusCard = surplusCard;
    }

    public Desk() {
    }

    public Desk(String deskNo) {
        this.deskNo = deskNo;
    }

    public int getBaseScore() {
        return baseScore;
    }

    public void setBaseScore(int baseScore) {
        this.baseScore = baseScore;
    }

    public String getDeskNo() {
        return deskNo;
    }

    public void setDeskNo(String deskNo) {
        this.deskNo = deskNo;
    }

    public List<Seat> getSeats() {
        return seats;
    }

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }
}
