package chess.game.landlords.mode.push;

import chess.game.landlords.mode.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 断线重连
 * <p>
 * Created by yjh on 16-6-29.
 */
public class PushReconnectLandlords {

    private GameStatus gameStatus;
    private String nextPlay;//下一个操作玩家
    private String randomLandlordPlayer;
    private String landlordPlayer; //地主用户
    private BigDecimal multiple;   //倍数
    private int callLandlordScore;//叫地主倍数
    private BigDecimal baseScore; //基础分
    private String deskNo;  //桌号
    private List<Seat> seats = new ArrayList<>(3);//座位
    private List<Card> landlordCard; //地主三张牌
    private PastCard pastCard;  //上一手牌的牌

    public PushReconnectLandlords() {
    }

    public PushReconnectLandlords(Game game, String user) {
        if (null != game.getDesk().getNextPlayer()) {
            this.nextPlay = game.getDesk().getNextPlayer();
        }
        if (null != game.getDesk().getGameStatus()) {
            this.gameStatus = game.getDesk().getGameStatus();
        }
        if (null != game.getDesk().getDeskNo()) {
            this.deskNo = game.getDesk().getDeskNo();
        }
        if (null != game.getDesk().getRandomLandlordPlayer()) {
            this.randomLandlordPlayer = game.getDesk().getRandomLandlordPlayer();
        }
        if (null != game.getDesk().getLandlordPlayer()) {
            this.landlordPlayer = game.getDesk().getLandlordPlayer();
        }
        if (0 != game.getDesk().getMultiple().compareTo(new BigDecimal(0))) {
            this.multiple = game.getDesk().getMultiple();
        }
        this.callLandlordScore = game.getDesk().getCallLandlordScore();
        if (0 != game.getDesk().getBaseScore().compareTo(new BigDecimal(0))) {
            this.baseScore = game.getDesk().getBaseScore();
        }
        if (null != game.getDesk().getLandlordCard() && game.getDesk().getLandlordCard().size() > 0) {
            this.landlordCard = game.getDesk().getLandlordCard();
        }
        if (null != game.getDesk().getPastCard()) {
            this.pastCard = game.getDesk().getPastCard();
        }
        if (null != game.getDesk().getPastCard()) {
            this.pastCard = game.getDesk().getPastCard();
        }
        if (null != game.getDesk().getSeats() && game.getDesk().getSeats().size() > 0) {
            this.seats = game.getDesk().getSeats();
            seats.stream().filter(seat -> null != seat.getCards() && !seat.getPlayer().getUserName().equals(user)).forEachOrdered(seat -> {
                int cardSize = seat.getCards().size();
                seat.setCards(null);
                seat.setCardsSize(cardSize);
            });
        }
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

    public String getNextPlay() {
        return nextPlay;
    }

    public void setNextPlay(String nextPlay) {
        this.nextPlay = nextPlay;
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
}
