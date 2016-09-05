package chess.application.area.representation.mapping;

import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import chess.application.area.representation.AreaRepresentation;
import chess.core.mapping.IMappingService;
import chess.domain.model.area.Area;

/**
 * Created by YJH on 2016/4/14.
 */
@Component
public class AreaRepresentationMapper extends CustomMapper<Area, AreaRepresentation> {

    @Autowired
    private IMappingService mappingService;

    public void mapAtoB(Area area, AreaRepresentation representation, MappingContext context) {
        if (null != area.getParent()) {
            AreaRepresentation data = mappingService.map(area.getParent(), AreaRepresentation.class, false);
            representation.setParent(data);
        }
    }

}
