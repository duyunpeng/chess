package chess.game.bullfight.push;

import chess.game.bullfight.command.GameOver;
import chess.game.bullfight.command.PushData;
import chess.game.bullfight.mode.Seat;

import java.util.List;

/**
 * Created by dyp
 * Date : 16-6-20.
 */
public class PushObject {

    private int type;
    private Seat seat;//座位
    private BankerPushObject bankerPushObject;//庄家
    private PushData data;
    private Boolean isCome;
    private List<BetPushObject> betPushObjects;//玩家下注
    private List<List<Integer>> list;
    private List<GameOver> gameOverPushType;//游戏结束推送
    private BetPushObject betPushObject;//下注推送
    private PairCardPushObject pairCardPushObject;//发牌推送
    private ReconnectPushObject reconnectPushObject;//断线推送
    private LeavePushObject leavePushObject;//离开推送
    private RobBullfightPushObject robBullfightPushObject;//玩家断线重连，推送该玩家的抢庄倍数
    private PlayerLeavePushObject playerLeavePushObject;//闲家离开推送给闲家座位号

    public PushObject() {
    }

    public RobBullfightPushObject getRobBullfightPushObject() {
        return robBullfightPushObject;
    }

    public void setRobBullfightPushObject(RobBullfightPushObject robBullfightPushObject) {
        this.robBullfightPushObject = robBullfightPushObject;
    }

    public List<BetPushObject> getBetPushObjects() {
        return betPushObjects;
    }

    public void setBetPushObjects(List<BetPushObject> betPushObjects) {
        this.betPushObjects = betPushObjects;
    }

    public PairCardPushObject getPairCardPushObject() {
        return pairCardPushObject;
    }

    public void setPairCardPushObject(PairCardPushObject pairCardPushObject) {
        this.pairCardPushObject = pairCardPushObject;
    }

    public ReconnectPushObject getReconnectPushObject() {
        return reconnectPushObject;
    }

    public void setReconnectPushObject(ReconnectPushObject reconnectPushObject) {
        this.reconnectPushObject = reconnectPushObject;
    }

    public BetPushObject getBetPushObject() {
        return betPushObject;
    }

    public void setBetPushObject(BetPushObject betPushObject) {
        this.betPushObject = betPushObject;
    }

    public List<List<Integer>> getList() {
        return list;
    }

    public void setList(List<List<Integer>> list) {
        this.list = list;
    }

    public BankerPushObject getBankerPushObject() {
        return bankerPushObject;
    }

    public void setBankerPushObject(BankerPushObject bankerPushObject) {
        this.bankerPushObject = bankerPushObject;
    }

    public PushData getData() {
        return data;
    }

    public void setData(PushData data) {
        this.data = data;
    }

    public Seat getSeat() {
        return seat;
    }

    public void setSeat(Seat seat) {
        this.seat = seat;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public List<GameOver> getGameOverPushType() {
        return gameOverPushType;
    }

    public void setGameOverPushType(List<GameOver> gameOverPushType) {
        this.gameOverPushType = gameOverPushType;
    }

    public LeavePushObject getLeavePushObject() {
        return leavePushObject;
    }

    public void setLeavePushObject(LeavePushObject leavePushObject) {
        this.leavePushObject = leavePushObject;
    }

    public PlayerLeavePushObject getPlayerLeavePushObject() {
        return playerLeavePushObject;
    }

    public void setPlayerLeavePushObject(PlayerLeavePushObject playerLeavePushObject) {
        this.playerLeavePushObject = playerLeavePushObject;
    }

    public Boolean getCome() {
        return isCome;
    }

    public void setCome(Boolean come) {
        isCome = come;
    }
}




