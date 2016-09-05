package chess.game.bullfight.push;

/**
 * Created by dyp
 * Date : 16-7-13.
 */
public class BetPushObject {

    private int SeatNo;//座位号

    private int multiple;//下注的倍数

    public int getSeatNo() {
        return SeatNo;
    }

    public void setSeatNo(int seatNo) {
        SeatNo = seatNo;
    }

    public int getMultiple() {
        return multiple;
    }

    public void setMultiple(int multiple) {
        this.multiple = multiple;
    }
}
