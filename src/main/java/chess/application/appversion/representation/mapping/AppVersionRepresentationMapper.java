package chess.application.appversion.representation.mapping;

import ma.glasnost.orika.CustomMapper;
import org.springframework.stereotype.Component;
import chess.application.area.representation.AreaRepresentation;
import chess.domain.model.area.Area;

/**
 * Created by lvdi on 2016/4/14.
 */
@Component
public class AppVersionRepresentationMapper extends CustomMapper<Area, AreaRepresentation> {


}
