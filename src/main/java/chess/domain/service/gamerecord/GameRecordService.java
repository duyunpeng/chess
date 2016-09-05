package chess.domain.service.gamerecord;

import chess.application.moneydetailed.command.CreateMoneyDetailedCommand;
import chess.application.procedures.command.CreateProceduresCommand;
import chess.core.enums.FlowType;
import chess.core.enums.GameResult;
import chess.core.enums.GameType;
import chess.domain.model.gamerecord.GameRecord;
import chess.domain.model.gamerecord.IGameRecordRepository;
import chess.domain.model.user.Landlords;
import chess.domain.model.user.ThreeCard;
import chess.domain.model.user.User;
import chess.domain.service.moneydetailed.IMoneyDetailedService;
import chess.domain.service.procedures.IProceduresService;
import chess.domain.service.user.IUserService;
import chess.game.landlords.mode.Action;
import chess.game.landlords.mode.GameBalance;
import chess.game.landlords.mode.push.PushGameOver;
import chess.game.threecard.push.ScoreResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by YJH
 * Date : 16-7-5.
 */
@Service("gameRecordService")
public class GameRecordService implements IGameRecordService {

    private final IGameRecordRepository<GameRecord, String> gameRecordRepository;

    private final IUserService userService;

    private final IMoneyDetailedService moneyDetailedService;

    private final IProceduresService proceduresService;

    @Autowired
    public GameRecordService(IGameRecordRepository<GameRecord, String> gameRecordRepository, IMoneyDetailedService moneyDetailedService, IUserService userService, IProceduresService proceduresService) {
        this.gameRecordRepository = gameRecordRepository;
        this.moneyDetailedService = moneyDetailedService;
        this.userService = userService;
        this.proceduresService = proceduresService;
    }

    @Override
    public void createLandlords(PushGameOver data) {
        for (GameBalance play : data.getPlayers()) {
            User user = userService.searchByName(play.getUserName());
            if (play.getGameResult() == Action.TRUE) {
                GameRecord gameRecord = new GameRecord(user, play.getScore().subtract(play.getScore().multiply(new BigDecimal(0.05))).setScale(2, BigDecimal.ROUND_UP), GameType.LANDLORDS, GameResult.WIN);
                gameRecordRepository.save(gameRecord);

                Landlords landlords = user.getLandlords();
                landlords.changeWinCount(landlords.getWinCount() + 1);
                user.changeLandlords(landlords);
                userService.update(user);

                CreateMoneyDetailedCommand command = new CreateMoneyDetailedCommand();
                command.setUserName(play.getUserName());
                command.setFlowType(FlowType.IN_FLOW);
                command.setDescription("斗地主");
                command.setMoney(play.getScore().subtract(play.getScore().multiply(new BigDecimal(0.05)).setScale(2, BigDecimal.ROUND_UP)));
                moneyDetailedService.create(command);

                CreateProceduresCommand proceduresCommand = new CreateProceduresCommand();
                proceduresCommand.setGameType(GameType.LANDLORDS);
                proceduresCommand.setUser(user);
                proceduresCommand.setProcedures(play.getScore().subtract(play.getScore().multiply(new BigDecimal(0.05))));
                proceduresService.create(proceduresCommand);
            } else {
                GameRecord gameRecord = new GameRecord(user, play.getScore(), GameType.LANDLORDS, GameResult.LOSE);
                gameRecordRepository.save(gameRecord);

                Landlords landlords = user.getLandlords();
                landlords.changeLoseCount(landlords.getLoseCount() + 1);
                user.changeLandlords(landlords);
                userService.update(user);

                CreateMoneyDetailedCommand command = new CreateMoneyDetailedCommand();
                command.setUserName(play.getUserName());
                command.setFlowType(FlowType.OUT_FLOW);
                command.setDescription("斗地主");
                command.setMoney(play.getScore());
                moneyDetailedService.create(command);
            }
        }
    }

    @Override
    public void createThreeCard(List<ScoreResult> data) {
        for (ScoreResult score : data) {
            User user = userService.searchByName(score.getUsername());
            if (score.getScore().compareTo(new BigDecimal(0)) > 0) {
                GameRecord gameRecord = new GameRecord(user, score.getScore(), GameType.THREE_CARD, GameResult.WIN);
                gameRecordRepository.save(gameRecord);

                ThreeCard threeCard = user.getThreeCard();
                threeCard.changeWinCount(threeCard.getWinCount() + 1);
                user.changeThreecard(threeCard);
                userService.update(user);

                CreateMoneyDetailedCommand command = new CreateMoneyDetailedCommand();
                command.setUserName(score.getUsername());
                command.setFlowType(FlowType.IN_FLOW);
                command.setDescription("扎金花");
                command.setMoney(score.getScore());
                moneyDetailedService.create(command);
            } else {
                GameRecord gameRecord = new GameRecord(user, score.getScore(), GameType.THREE_CARD, GameResult.LOSE);
                gameRecordRepository.save(gameRecord);

                ThreeCard threeCard = user.getThreeCard();
                threeCard.changeLoseCount(threeCard.getLoseCount() + 1);
                user.changeThreecard(threeCard);
                userService.update(user);

                CreateMoneyDetailedCommand command = new CreateMoneyDetailedCommand();
                command.setUserName(score.getUsername());
                command.setFlowType(FlowType.OUT_FLOW);
                command.setDescription("扎金花");
                command.setMoney(new BigDecimal(0).subtract(score.getScore()));
                moneyDetailedService.create(command);
            }
        }
    }
}
