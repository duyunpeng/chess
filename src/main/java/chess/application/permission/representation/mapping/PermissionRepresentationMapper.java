package chess.application.permission.representation.mapping;

import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import chess.application.permission.representation.PermissionRepresentation;
import chess.core.mapping.IMappingService;
import chess.domain.model.permission.Permission;

/**
 * Created by YJH on 2016/3/30 0030.
 */
@Component
public class PermissionRepresentationMapper extends CustomMapper<Permission, PermissionRepresentation> {

    @Autowired
    private IMappingService mappingService;
}
