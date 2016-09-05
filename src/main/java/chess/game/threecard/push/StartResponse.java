package chess.game.threecard.push;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by pengyi on 16-8-3.
 */
public class StartResponse {

    private int banker; //庄家
    private BigDecimal totalScore;   //总分数
    private BigDecimal baseScore; //基础分
    private String deskNo;  //桌号
    private List<SeatResponse> seats;
    private BigDecimal lastScore;
    private boolean lastLook;
    private int operationSeat;

    public int getBanker() {
        return banker;
    }

    public void setBanker(int banker) {
        this.banker = banker;
    }

    public BigDecimal getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(BigDecimal totalScore) {
        this.totalScore = totalScore;
    }

    public BigDecimal getBaseScore() {
        return baseScore;
    }

    public void setBaseScore(BigDecimal baseScore) {
        this.baseScore = baseScore;
    }

    public String getDeskNo() {
        return deskNo;
    }

    public void setDeskNo(String deskNo) {
        this.deskNo = deskNo;
    }

    public List<SeatResponse> getSeats() {
        return seats;
    }

    public void setSeats(List<SeatResponse> seats) {
        this.seats = seats;
    }

    public BigDecimal getLastScore() {
        return lastScore;
    }

    public void setLastScore(BigDecimal lastScore) {
        this.lastScore = lastScore;
    }

    public boolean isLastLook() {
        return lastLook;
    }

    public void setLastLook(boolean lastLook) {
        this.lastLook = lastLook;
    }

    public int getOperationSeat() {
        return operationSeat;
    }

    public void setOperationSeat(int operationSeat) {
        this.operationSeat = operationSeat;
    }
}
