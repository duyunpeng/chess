package chess.application.bullfightrecord;

import chess.domain.service.bullfightrecord.IBullfightRecordService;
import chess.game.bullfight.command.GameOver;
import chess.game.bullfight.push.LeavePushObject;
import chess.game.bullfight.take.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by dyp on 2016/7/20.
 */
@Service("bullfightRecordAppService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class BullfightRecordAppService implements IBullfightRecordAppService {

    @Autowired
    private IBullfightRecordService bullfightRecordService;


    //游戏账目结算
    @Override
    public List<GameOver> gameSettlement(List<GameOver> over, Game game) {
        return bullfightRecordService.gameSettlement(over, game);
    }

    //闲家强制退出后的惩罚
    @Override
    public BigDecimal leaveCompensate(Game game, String userName, BigDecimal s_sum) {
        return bullfightRecordService.leaveCompensate(game, userName, s_sum);
    }

    //庄家强制退出后的惩罚
    @Override
    public List<LeavePushObject> bankerLeaveCompensate(List<LeavePushObject> leavePushObjectList, Game game) {
        return bullfightRecordService.bankerLeaveCompensate(leavePushObjectList, game);
    }

    @Override
    public Boolean charge(String userName, int baseScore) {
        return bullfightRecordService.charge(userName, baseScore);
    }


}
