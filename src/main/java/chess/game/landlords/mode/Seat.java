package chess.game.landlords.mode;

import java.util.List;

/**
 * Created by YJH
 * Date : 16-6-12.
 */
public class Seat {

    private Player player;  //玩家
    private int seatNo;//座位号
    private Action isReady = Action.NONE;//准备
    private Action isJoin = Action.NONE;  //退出标示
    private Action isDouble = Action.NONE;   //是否加倍
    //private Action isRobLandlord = Action.NONE;  //是否抢地主
    private Action isCallLandlord = Action.NONE; //是否叫地主
    private int callLandlordScore;  //叫地主分数
    private Action isOffLine = Action.NONE;       //是否掉线
    private Action isEscape = Action.NONE; //是否逃跑
    private Action isRobot = Action.NONE;  //是否托管
    private List<Card> cards;   //牌
    private int cardsSize;  //牌数量
    private int index;

    public Seat() {
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(int seatNo) {
        this.seatNo = seatNo;
    }

    public Action getIsReady() {
        return isReady;
    }

    public void setIsReady(Action isReady) {
        this.isReady = isReady;
    }

    public Action getIsJoin() {
        return isJoin;
    }

    public void setIsJoin(Action isJoin) {
        this.isJoin = isJoin;
    }

    public Action getIsDouble() {
        return isDouble;
    }

    public void setIsDouble(Action isDouble) {
        this.isDouble = isDouble;
    }

    //public Action getIsRobLandlord() {
    //    return isRobLandlord;
    //}

    //public void setIsRobLandlord(Action isRobLandlord) {
    //   this.isRobLandlord = isRobLandlord;
    //}

    public Action getIsCallLandlord() {
        return isCallLandlord;
    }

    public void setIsCallLandlord(Action isCallLandlord) {
        this.isCallLandlord = isCallLandlord;
    }

    public int getCallLandlordScore() {
        return callLandlordScore;
    }

    public void setCallLandlordScore(int callLandlordScore) {
        this.callLandlordScore = callLandlordScore;
    }

    public Action getIsOffLine() {
        return isOffLine;
    }

    public void setIsOffLine(Action isOffLine) {
        this.isOffLine = isOffLine;
    }

    public Action getIsEscape() {
        return isEscape;
    }

    public void setIsEscape(Action isEscape) {
        this.isEscape = isEscape;
    }

    public Action getIsRobot() {
        return isRobot;
    }

    public void setIsRobot(Action isRobot) {
        this.isRobot = isRobot;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        if (null != cards) {
            this.cards = cards;
            this.cardsSize = cards.size();
        } else {
            this.cardsSize = 0;
        }
    }

    public int getCardsSize() {
        return cardsSize;
    }

    public void setCardsSize(int cardsSize) {
        this.cardsSize = cardsSize;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }
}
