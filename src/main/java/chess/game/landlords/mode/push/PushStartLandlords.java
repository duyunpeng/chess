package chess.game.landlords.mode.push;

import chess.game.landlords.mode.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 推送玩家数据类
 * <p>
 * Created by yjh on 16-6-28.
 */
public class PushStartLandlords {

    private String nextPlayer;      //下家操作玩家
    private GameStatus gameStatus;
    private String randomLandlordPlayer;//随机地主玩家
    private BigDecimal multiple;   //倍数
    private BigDecimal baseScore; //基础分
    private String deskNo;  //桌号
    private List<Seat> seats = new ArrayList<Seat>(3);//座位
    private List<Card> landlordCard; //地主三张牌

    public PushStartLandlords() {
    }

    public PushStartLandlords(Game game, String user) {
        this.nextPlayer = game.getDesk().getRandomLandlordPlayer();
        this.gameStatus = GameStatus.CALL;
        this.randomLandlordPlayer = game.getDesk().getRandomLandlordPlayer();
        this.multiple = game.getDesk().getMultiple();
        this.baseScore = game.getDesk().getBaseScore();
        this.deskNo = game.getDesk().getDeskNo();
        this.seats = game.getDesk().getSeats();
        game.getDesk().getSeats().stream().filter(seat -> !seat.getPlayer().getUserName().equals(user)).forEachOrdered(seat -> {
            seat.setCards(null);
        });
        this.landlordCard = game.getDesk().getLandlordCard();

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

}
