package chess.game.bullfight.mode;

import chess.application.user.representation.ApiUserRepresentation;

import java.util.List;

/**
 * Created by jm
 * Date : 16-6-20.
 */
public class Seat {

    private ApiUserRepresentation user;  //用户
    private int multiple;   //下注的倍数
    private int bankerMultiple; //抢庄的倍数 1 2 3 4倍 0 为不抢
    private Action isTheBanker; //是否是庄家
    private Action isRobBanker;  //是否抢庄
    private Action betYesOrNo;  //是否下注
    private Action isOldOrNew; //新旧玩家
    private Action isExit;  //退出标示
    private List<Card> cards;   //牌
    private Player player;  //玩家
    private Integer seatNo;//桌位号
    private Action isPlaying;//是否正在游戏
    private Action isConnect;//是否连接
    private String name; //昵称
    private Boolean isWinOrLose;//玩家的输赢

    public Seat() {
    }

    public Seat(ApiUserRepresentation user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Action getIsConnect() {
        return isConnect;
    }

    public void setIsConnect(Action isConnect) {
        this.isConnect = isConnect;
    }

    public Action getIsPlaying() {
        return isPlaying;
    }

    public void setIsPlaying(Action isPlaying) {
        this.isPlaying = isPlaying;
    }

    public Action getIsOldOrNew() {
        return isOldOrNew;
    }

    public void setIsOldOrNew(Action isOldOrNew) {
        this.isOldOrNew = isOldOrNew;
    }

    public int getBankerMultiple() {
        return bankerMultiple;
    }

    public void setBankerMultiple(int bankerMultiple) {
        this.bankerMultiple = bankerMultiple;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public Action getIsTheBanker() {
        return isTheBanker;
    }

    public void setIsTheBanker(Action isTheBanker) {
        this.isTheBanker = isTheBanker;
    }

    public int getMultiple() {
        return multiple;
    }

    public void setMultiple(int multiple) {
        this.multiple = multiple;
    }

    public ApiUserRepresentation getUser() {
        return user;
    }

    public void setUser(ApiUserRepresentation user) {
        this.user = user;
    }

    public Action getBetYesOrNo() {
        return betYesOrNo;
    }

    public void setBetYesOrNo(Action betYesOrNo) {
        this.betYesOrNo = betYesOrNo;
    }

    public Action getIsRobBanker() {
        return isRobBanker;
    }

    public void setIsRobBanker(Action isRobBanker) {
        this.isRobBanker = isRobBanker;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Action getIsExit() {
        return isExit;
    }

    public void setIsExit(Action isExit) {
        this.isExit = isExit;
    }

    public Integer getSeatNo() {
        return seatNo;
    }

    public void setSeatNo(Integer seatNo) {
        this.seatNo = seatNo;
    }

    public Boolean getWinOrLose() {
        return isWinOrLose;
    }

    public void setWinOrLose(Boolean winOrLose) {
        isWinOrLose = winOrLose;
    }

}
