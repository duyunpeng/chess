package chess.application.gamemultiple;

import chess.application.gamemultiple.command.EditGameMultipleCommand;
import chess.application.gamemultiple.command.ListGameMultipleCommand;
import chess.domain.model.gamemultiple.GameMultiple;
import chess.domain.service.gamemultiple.IGameMultipleService;
import chess.infrastructure.persistence.hibernate.generic.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Author pengyi
 * Create date 16-7-19
 */
@Service("gameMultipleAppService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class GameMultipleAppService implements IGameMultipleAppService {

    private final IGameMultipleService gameMultipleService;

    @Autowired
    public GameMultipleAppService(IGameMultipleService gameMultipleService) {
        this.gameMultipleService = gameMultipleService;
    }

    @Override
    @Transactional(readOnly = true)
    public Pagination<GameMultiple> pagination(ListGameMultipleCommand command) {
        command.verifyPage();
        command.verifyPageSize(15);
        return gameMultipleService.pagination(command);
    }

    @Override
    public void create(EditGameMultipleCommand command) {
        gameMultipleService.create(command);
    }

    @Override
    @Transactional(readOnly = true)
    public GameMultiple searchById(String id) {
        return gameMultipleService.byId(id);
    }

    @Override
    public void edit(EditGameMultipleCommand command) {
        gameMultipleService.edit(command);
    }

    @Override
    public void delete(String id) {
        gameMultipleService.delete(id);
    }

    @Override
    public List<GameMultiple> list(ListGameMultipleCommand command) {
        return gameMultipleService.list(command);
    }
}
