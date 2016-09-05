package chess.application.recharge.representation.mapping;

import chess.application.recharge.representation.RechargeRepresentation;
import chess.application.user.representation.UserRepresentation;
import chess.core.mapping.IMappingService;
import chess.domain.model.recharge.Recharge;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by YJH
 * Date : 16-7-19.
 */
@Component
public class RechargeRepresentationMapper extends CustomMapper<Recharge, RechargeRepresentation> {

    private final IMappingService mappingService;

    @Autowired
    public RechargeRepresentationMapper(IMappingService mappingService) {
        this.mappingService = mappingService;
    }

    public void mapAtoB(Recharge recharge, RechargeRepresentation rechargeRepresentation, MappingContext context) {
        if (null != recharge.getUser()) {
            UserRepresentation data = mappingService.map(recharge.getUser(), UserRepresentation.class, false);
            rechargeRepresentation.setUser(data);
        }
    }
}
