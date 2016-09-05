package chess.application.permission;


import chess.application.permission.command.CreatePermissionCommand;
import chess.application.permission.command.EditPermissionCommand;
import chess.application.permission.command.ListPermissionCommand;
import chess.application.permission.representation.PermissionRepresentation;
import chess.application.shared.command.SharedCommand;
import chess.infrastructure.persistence.hibernate.generic.Pagination;

import java.util.List;

/**
 * Created by YJH on 2016/3/30.
 */
public interface IPermissionAppService {

    Pagination<PermissionRepresentation> pagination(ListPermissionCommand command);

    Pagination<PermissionRepresentation> paginationJSON(ListPermissionCommand command);

    List<PermissionRepresentation> list(ListPermissionCommand command);

    PermissionRepresentation searchByID(String id);

    PermissionRepresentation searchByName(String name);

    PermissionRepresentation create(CreatePermissionCommand command);

    PermissionRepresentation edit(EditPermissionCommand command);

    void updateStatus(SharedCommand command);

}
