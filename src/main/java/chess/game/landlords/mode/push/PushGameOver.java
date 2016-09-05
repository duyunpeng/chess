package chess.game.landlords.mode.push;

import chess.game.landlords.mode.GameBalance;

import java.util.ArrayList;
import java.util.List;

/**
 * 游戏结束
 * <p>
 * Created by yjh on 16-6-30.
 */
public class PushGameOver {

    private List<GameBalance> players = new ArrayList<GameBalance>();//玩家
    private String landlordsPlayer;//地主座位号

    public List<GameBalance> getPlayers() {
        return players;
    }

    public void setPlayers(List<GameBalance> players) {
        this.players = players;
    }

    public String getLandlordsPlayer() {
        return landlordsPlayer;
    }

    public void setLandlordsPlayer(String landlordsPlayer) {
        this.landlordsPlayer = landlordsPlayer;
    }
}
