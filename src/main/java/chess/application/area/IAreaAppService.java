package chess.application.area;

import chess.application.area.command.CreateAreaCommand;
import chess.application.area.command.EditAreaCommand;
import chess.application.area.command.ListAreaCommand;
import chess.application.area.representation.AreaRepresentation;
import chess.application.shared.command.SharedCommand;
import chess.infrastructure.persistence.hibernate.generic.Pagination;

import java.util.List;

/**
 * Created by YJH on 2016/4/14.
 */
public interface IAreaAppService {

    Pagination<AreaRepresentation> pagination(ListAreaCommand command);

    AreaRepresentation searchByID(String id);

    AreaRepresentation create(CreateAreaCommand command);

    AreaRepresentation edit(EditAreaCommand command);

    void updateStatus(SharedCommand command);

    List<AreaRepresentation> listJSON(ListAreaCommand command);
}
