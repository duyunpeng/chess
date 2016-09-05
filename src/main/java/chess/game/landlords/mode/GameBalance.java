package chess.game.landlords.mode;

import java.math.BigDecimal;
import java.util.List;

/**
 * 玩家游戏结算数据
 * Created by YJH
 * Date : 16-7-2.
 */
public class GameBalance {

    private List<Card> cardList;//剩余的牌
    private String userName;//用户名
    private String name;//昵称
    private BigDecimal score;  //结算分
    private Action gameResult;//游戏结果 输还是赢

    public List<Card> getCardList() {
        return cardList;
    }

    public void setCardList(List<Card> cardList) {
        this.cardList = cardList;
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

    public BigDecimal getScore() {
        return score;
    }

    public void setScore(BigDecimal score) {
        this.score = score;
    }

    public Action getGameResult() {
        return gameResult;
    }

    public void setGameResult(Action gameResult) {
        this.gameResult = gameResult;
    }
}
