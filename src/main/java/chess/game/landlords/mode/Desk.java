package chess.game.landlords.mode;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by yjh on 16-6-12.
 */
public class Desk {

    private String nextPlayer;      //下家操作玩家
    private GameStatus gameStatus;
    private String randomLandlordPlayer;//随机地主玩家
    private String landlordPlayer; //地主玩家
    private BigDecimal multiple = new BigDecimal(1);   //倍数
    private int callLandlordScore;//叫地主倍数
    private BigDecimal baseScore; //基础分
    private String deskNo;  //桌号
    private List<Seat> seats = new ArrayList<>(3);//座位
    private List<Card> landlordCard; //地主三张牌
    private PastCard pastCard;  //上一手牌的牌
    private List<LastRoundCard> lastRoundCards;//上一轮牌
    private Date landlordsDate;

    public Desk() {
        landlordsDate = new Date();
    }

    public String getNextPlayer() {
        return nextPlayer;
    }

    public void setNextPlayer(String nextPlayer) {
        this.nextPlayer = nextPlayer;
    }

    public GameStatus getGameStatus() {
        return gameStatus;
    }

    public void setGameStatus(GameStatus gameStatus) {
        this.gameStatus = gameStatus;
    }

    public String getRandomLandlordPlayer() {
        return randomLandlordPlayer;
    }

    public void setRandomLandlordPlayer(String randomLandlordPlayer) {
        this.randomLandlordPlayer = randomLandlordPlayer;
    }

    public String getLandlordPlayer() {
        return landlordPlayer;
    }

    public void setLandlordPlayer(String landlordPlayer) {
        this.landlordPlayer = landlordPlayer;
    }

    public BigDecimal getMultiple() {
        return multiple;
    }

    public void setMultiple(BigDecimal multiple) {
        this.multiple = multiple;
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

    public void setSeats(List<Seat> seats) {
        this.seats = seats;
    }

    public List<Card> getLandlordCard() {
        return landlordCard;
    }

    public void setLandlordCard(List<Card> landlordCard) {
        this.landlordCard = landlordCard;
    }

    public PastCard getPastCard() {
        return pastCard;
    }

    public void setPastCard(PastCard pastCard) {
        this.pastCard = pastCard;
    }

    public int getCallLandlordScore() {
        return callLandlordScore;
    }

    public void setCallLandlordScore(int callLandlordScore) {
        this.callLandlordScore = callLandlordScore;
    }

    public List<LastRoundCard> getLastRoundCards() {
        return lastRoundCards;
    }

    public void setLastRoundCards(List<LastRoundCard> lastRoundCards) {
        this.lastRoundCards = lastRoundCards;
    }

    public Date getLandlordsDate() {
        return landlordsDate;
    }

    public void setLandlordsDate(Date landlordsDate) {
        this.landlordsDate = landlordsDate;
    }
}
