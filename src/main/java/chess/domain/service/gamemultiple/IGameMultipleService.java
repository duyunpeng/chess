package chess.domain.service.gamemultiple;

import chess.application.gamemultiple.command.EditGameMultipleCommand;
import chess.application.gamemultiple.command.ListGameMultipleCommand;
import chess.domain.model.gamemultiple.GameMultiple;
import chess.infrastructure.persistence.hibernate.generic.Pagination;

import java.util.List;

/**
 * Author pengyi
 * Create date 16-7-19
 */
public interface IGameMultipleService {
    Pagination<GameMultiple> pagination(ListGameMultipleCommand command);

    void create(EditGameMultipleCommand command);

    GameMultiple byId(String id);

    void edit(EditGameMultipleCommand command);

    void delete(String id);

    List<GameMultiple> list(ListGameMultipleCommand command);
}
