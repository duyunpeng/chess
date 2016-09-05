package chess.domain.service.role;

import chess.application.role.command.CreateRoleCommand;
import chess.application.role.command.EditRoleCommand;
import chess.application.role.command.ListRoleCommand;
import chess.application.shared.command.SharedCommand;
import chess.domain.model.role.Role;
import chess.infrastructure.persistence.hibernate.generic.Pagination;

import java.util.List;

/**
 * Created by YJH on 2016/3/30.
 */
public interface IRoleService {

    Pagination<Role> pagination(ListRoleCommand command);

    List<Role> list(ListRoleCommand command);

    Role searchByID(String id);

    Role searchByName(String name);

    Role create(CreateRoleCommand command);

    Role edit(EditRoleCommand command);

    void updateStatus(SharedCommand command);

    List<Role> searchByIDs(List<String> ids);
}
