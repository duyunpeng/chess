package chess.domain.service.area;


import chess.application.area.command.CreateAreaCommand;
import chess.application.area.command.EditAreaCommand;
import chess.application.area.command.ListAreaCommand;
import chess.application.shared.command.SharedCommand;
import chess.domain.model.area.Area;
import chess.infrastructure.persistence.hibernate.generic.Pagination;

import java.util.List;

/**
 * Created by YJH on 2016/4/14.
 */
public interface IAreaService {
    Pagination<Area> pagination(ListAreaCommand command);

    Area searchByID(String id);

    Area create(CreateAreaCommand command);

    Area edit(EditAreaCommand command);

    void updateStatus(SharedCommand command);

    List<Area> list(ListAreaCommand command);
}
