package chess.application.gamerecord;

import chess.game.landlords.mode.push.PushGameOver;
import chess.game.threecard.push.ScoreResult;

import java.util.List;

/**
 * Created by yjh on 16-7-5.
 */
public interface IGameRecordAppService {
    void createLandlords(PushGameOver data);

    void createThreecard(List<ScoreResult> data);
}
