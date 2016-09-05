package chess.game.landlords.mode.push;

import chess.game.landlords.mode.*;

/**
 * 叫地主
 * <p>
 * Created by yjh on 16-6-28.
 */
public class PushCallLandlords {

    private String player;//玩家
    private Action isCallLandlords;//是否叫地主
    private int callLandlordScore;//叫地主分数

    public PushCallLandlords() {
    }

    public PushCallLandlords(Game game, String user) {
        game.getDesk().getSeats().stream().filter(seat -> seat.getPlayer().getUserName().equals(user)).forEach(seat -> {
            this.player = seat.getPlayer().getUserName();
            this.isCallLandlords = seat.getIsCallLandlord();
            this.callLandlordScore = seat.getCallLandlordScore();
        });
    }

    public String getPlayer() {
        return player;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public Action getIsCallLandlords() {
        return isCallLandlords;
    }

    public void setIsCallLandlords(Action isCallLandlords) {
        this.isCallLandlords = isCallLandlords;
    }

    public int getCallLandlordScore() {
        return callLandlordScore;
    }

    public void setCallLandlordScore(int callLandlordScore) {
        this.callLandlordScore = callLandlordScore;
    }
}
