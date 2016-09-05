package chess.domain.service.gamerecord;

import chess.game.landlords.mode.push.PushGameOver;
import chess.game.threecard.push.ScoreResult;

import java.util.List;

/**
 * Created by yjh on 16-7-5.
 */
public interface IGameRecordService {
    void createLandlords(PushGameOver data);

    void createThreeCard(List<ScoreResult> data);
}
