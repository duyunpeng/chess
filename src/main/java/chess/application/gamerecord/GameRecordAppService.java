package chess.application.gamerecord;

import chess.core.mapping.IMappingService;
import chess.domain.service.gamerecord.IGameRecordService;
import chess.game.landlords.mode.push.PushGameOver;
import chess.game.threecard.push.ScoreResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by yjh on 16-7-5.
 */
@Service("gameRecordAppService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class GameRecordAppService implements IGameRecordAppService {

    @Autowired
    private IGameRecordService gameRecordService;

    @Autowired
    private IMappingService mappingService;


    @Override
    public void createLandlords(PushGameOver data) {
        gameRecordService.createLandlords(data);
    }

    @Override
    public void createThreecard(List<ScoreResult> data) {
        gameRecordService.createThreeCard(data);
    }
}
