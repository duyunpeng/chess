package chess.domain.model.user;

/**
 * Author pengyi
 * Date 16-8-9.
 */
public class ThreeCard {

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

    public ThreeCard() {
        this.winCount = 0;
        this.loseCount = 0;
    }

    public ThreeCard(Integer winCount, Integer loseCount) {
        this.winCount = winCount;
        this.loseCount = loseCount;
    }
}
