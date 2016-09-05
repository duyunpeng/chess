package chess.application.moneydetailed.representation.mapping;

import chess.application.moneydetailed.representation.MoneyDetailedRepresentation;
import chess.application.user.representation.UserRepresentation;
import chess.core.mapping.IMappingService;
import chess.domain.model.moneydetailed.MoneyDetailed;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by YJH
 * Date : 16-7-19.
 */
@Component
public class MoneyDetailedRepresentationMapper extends CustomMapper<MoneyDetailed, MoneyDetailedRepresentation> {

    private final IMappingService mappingService;

    @Autowired
    public MoneyDetailedRepresentationMapper(IMappingService mappingService) {
        this.mappingService = mappingService;
    }

    public void mapAtoB(MoneyDetailed moneyDetailed, MoneyDetailedRepresentation representation, MappingContext context) {
        if (null != moneyDetailed.getUser()) {
            UserRepresentation data = mappingService.map(moneyDetailed.getUser(), UserRepresentation.class, false);
            representation.setUser(data);
        }
    }
}
