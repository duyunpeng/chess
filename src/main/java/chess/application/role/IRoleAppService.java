package chess.application.role;


import chess.application.role.command.CreateRoleCommand;
import chess.application.role.command.EditRoleCommand;
import chess.application.role.command.ListRoleCommand;
import chess.application.role.representation.RoleRepresentation;
import chess.application.shared.command.SharedCommand;
import chess.infrastructure.persistence.hibernate.generic.Pagination;

import java.util.List;

/**
 * Created by YJH on 2016/3/30.
 */
public interface IRoleAppService {

    Pagination<RoleRepresentation> paginationJSON(ListRoleCommand command);

    Pagination<RoleRepresentation> pagination(ListRoleCommand command);

    List<RoleRepresentation> list(ListRoleCommand command);

    RoleRepresentation searchByID(String id);

    RoleRepresentation searchByName(String name);

    RoleRepresentation create(CreateRoleCommand command);

    RoleRepresentation edit(EditRoleCommand command);

    void updateStatus(SharedCommand command);
}
