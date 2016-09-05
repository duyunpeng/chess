package chess.application.permission;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import chess.application.permission.command.CreatePermissionCommand;
import chess.application.permission.command.EditPermissionCommand;
import chess.application.permission.command.ListPermissionCommand;
import chess.application.permission.representation.PermissionRepresentation;
import chess.application.shared.command.SharedCommand;
import chess.core.mapping.IMappingService;
import chess.domain.model.permission.Permission;
import chess.domain.service.permission.IPermissionService;
import chess.infrastructure.persistence.hibernate.generic.Pagination;

import java.util.List;

/**
 * Created by YJH on 2016/3/30.
 */
@Service("permissionAppService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class PermissionAppService implements IPermissionAppService {

    @Autowired
    private IPermissionService permissionService;

    @Autowired
    private IMappingService mappingService;

    @Override
    @Transactional(readOnly = true)
    public Pagination<PermissionRepresentation> pagination(ListPermissionCommand command) {
        command.verifyPage();
        command.verifyPageSize(15);
        Pagination<Permission> pagination = permissionService.pagination(command);
        List<PermissionRepresentation> data = mappingService.mapAsList(pagination.getData(), PermissionRepresentation.class);
        return new Pagination<PermissionRepresentation>(data, pagination.getCount(), pagination.getPage(), pagination.getPageSize());
    }

    @Override
    public Pagination<PermissionRepresentation> paginationJSON(ListPermissionCommand command) {
        command.verifyPage();
        command.setName(command.getPermissionName());
        Pagination<Permission> pagination = permissionService.pagination(command);
        List<PermissionRepresentation> data = mappingService.mapAsList(pagination.getData(), PermissionRepresentation.class);
        return new Pagination<PermissionRepresentation>(data, pagination.getCount(), pagination.getPage(), pagination.getPageSize());
    }

    @Override
    @Transactional(readOnly = true)
    public List<PermissionRepresentation> list(ListPermissionCommand command) {
        return mappingService.mapAsList(permissionService.list(command), PermissionRepresentation.class);
    }

    @Override
    @Transactional(readOnly = true)
    public PermissionRepresentation searchByID(String id) {
        return mappingService.map(permissionService.searchByID(id), PermissionRepresentation.class, false);
    }

    @Override
    @Transactional(readOnly = true)
    public PermissionRepresentation searchByName(String name) {
        return mappingService.map(permissionService.searchByName(name), PermissionRepresentation.class, false);
    }

    @Override
    public PermissionRepresentation create(CreatePermissionCommand command) {
        PermissionRepresentation permission = mappingService.map(permissionService.create(command), PermissionRepresentation.class, false);
        return permission;
    }

    @Override
    public PermissionRepresentation edit(EditPermissionCommand command) {
        PermissionRepresentation permission = mappingService.map(permissionService.edit(command), PermissionRepresentation.class, false);
        return permission;
    }

    @Override
    public void updateStatus(SharedCommand command) {
        permissionService.updateStatus(command);
    }
}
