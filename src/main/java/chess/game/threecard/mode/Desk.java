package chess.game.threecard.mode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Author pengyi
 * Create date 16-6-21
 */
public class Desk {

    private int banker; //庄家
    private BigDecimal totalScore;   //总分数
    private BigDecimal baseScore; //基础分
    private String deskNo;  //桌号
    private List<Seat> seats = new ArrayList<Seat>();//座位
    private BigDecimal lastScore;
    private boolean lastLook;
    private int lastSeatNo;
    private List<Integer> seatsNo;
    private int operationSeat;
    private Date date;
    private Date startDate;

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

    public List<Seat> getSeats() {
        return seats;
    }

    private void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public void addSeat(Seat seat) {
        this.seats.add(seat);
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

    public List<Integer> getSeatsNo() {
        return seatsNo;
    }

    public void setSeatsNo(List<Integer> seatsNo) {
        this.seatsNo = seatsNo;
    }

    public int getLastSeatNo() {
        return lastSeatNo;
    }

    public void setLastSeatNo(int lastSeatNo) {
        this.lastSeatNo = lastSeatNo;
    }

    public int getOperationSeat() {
        return operationSeat;
    }

    public void setOperationSeat(int operationSeat) {
        this.operationSeat = operationSeat;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public void reset(int banker) {
        this.banker = banker;
        this.totalScore = new BigDecimal(0);
        this.lastScore = new BigDecimal(0);
        this.lastLook = false;
        operationSeat = 0;
        List<Seat> seats = this.seats;
        for (Seat seat : seats) {
            seat.setScore(new BigDecimal(0));
            seat.setLook(false);
            seat.setCards(null);
            seat.setReady(false);
        }
        this.setSeats(seats);
    }

    public boolean exists(String username) {
        for (Seat seat : seats) {
            if (seat.getUserName().equals(username)) {
                return true;
            }
        }
        return false;
    }

}
