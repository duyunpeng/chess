package chess.domain.model.user;

/**
 * Created by yjh on 16-6-22.
 */
public class Landlords {

    private Integer winCount;   //赢的次数
    private Integer loseCount;  //输的次数


    private void setWinCount(Integer winCount) {
        this.winCount = winCount;
    }

    private void setLoseCount(Integer loseCount) {
        this.loseCount = loseCount;
    }

    public Integer getWinCount() {
        return winCount;
    }

    public Integer getLoseCount() {
        return loseCount;
    }

    public void changeWinCount(Integer winCount) {
        this.winCount = winCount;
    }

    public void changeLoseCount(Integer loseCount) {
        this.loseCount = loseCount;
    }

    public Landlords() {
        this.winCount = 0;
        this.loseCount = 0;
    }

    public Landlords(Integer winCount, Integer loseCount) {
        this.winCount = winCount;
        this.loseCount = loseCount;
    }
}
