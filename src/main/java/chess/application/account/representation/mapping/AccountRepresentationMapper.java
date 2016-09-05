package chess.application.account.representation.mapping;

import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import chess.application.account.representation.AccountRepresentation;
import chess.application.picture.representation.PictureRepresentation;
import chess.application.role.representation.RoleRepresentation;
import chess.core.mapping.IMappingService;
import chess.domain.model.account.Account;

import java.util.List;

/**
 * Created by YJH on 2016/3/30 0030.
 */
@Component
public class AccountRepresentationMapper extends CustomMapper<Account, AccountRepresentation> {

    @Autowired
    private IMappingService mappingService;

    public void mapAtoB(Account account, AccountRepresentation representation, MappingContext context) {
        if (null != account.getRoles()) {
            List<RoleRepresentation> data = mappingService.mapAsList(account.getRoles(), RoleRepresentation.class);
            representation.setRoles(data);
        }
        if (null != account.getHeadPic()) {
            PictureRepresentation data = mappingService.map(account.getHeadPic(), PictureRepresentation.class, false);
            representation.setHeadPic(data);
        }
    }

}
