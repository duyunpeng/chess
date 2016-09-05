package chess.application.withdraw.representation.mapping;

import chess.application.withdraw.representation.WithdrawRepresentation;
import chess.domain.model.withdraw.Withdraw;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.stereotype.Component;

/**
 * Created by pengyi on 2016/5/6.
 */
@Component
public class WithdrawRepresentationMapper extends CustomMapper<Withdraw, WithdrawRepresentation> {

    public void mapAtoB(Withdraw withdraw, WithdrawRepresentation representation, MappingContext context) {
        representation.setUserId(withdraw.getUser().getId());
        representation.setUsername(withdraw.getUser().getUserName());
    }

}
