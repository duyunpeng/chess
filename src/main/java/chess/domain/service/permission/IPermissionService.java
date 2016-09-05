package chess.domain.service.permission;


import chess.application.permission.command.CreatePermissionCommand;
import chess.application.permission.command.EditPermissionCommand;
import chess.application.permission.command.ListPermissionCommand;
import chess.application.shared.command.SharedCommand;
import chess.domain.model.permission.Permission;
import chess.infrastructure.persistence.hibernate.generic.Pagination;

import java.util.List;

/**
 * Created by YJH on 2016/3/30.
 */
public interface IPermissionService {

    Pagination<Permission> pagination(ListPermissionCommand command);

    List<Permission> list(ListPermissionCommand command);

    List<Permission> searchByIDs(List<String> ids);

    Permission searchByID(String id);

    Permission searchByName(String name);

    Permission create(CreatePermissionCommand command);

    Permission edit(EditPermissionCommand command);

    void updateStatus(SharedCommand command);
}
