package chess.domain.service.bullfightrecord;

import chess.game.bullfight.command.GameOver;
import chess.game.bullfight.push.LeavePushObject;
import chess.game.bullfight.take.Game;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by dyp on 2016/7/20.
 */
public interface IBullfightRecordService {

    List<GameOver> gameSettlement(List<GameOver> over, Game game);

    //闲家强制退出后的惩罚
    BigDecimal leaveCompensate(Game game, String userName, BigDecimal s_sum);

    //庄家强制退出后的惩罚
    List<LeavePushObject> bankerLeaveCompensate(List<LeavePushObject> leavePushObjectList, Game game);


    Boolean charge(String userName, int baseScore);
}
