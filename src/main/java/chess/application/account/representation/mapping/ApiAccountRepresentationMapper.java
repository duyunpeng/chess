package chess.application.account.representation.mapping;

import chess.application.account.representation.ApiAccountRepresentation;
import chess.application.picture.representation.PictureRepresentation;
import chess.core.mapping.IMappingService;
import chess.domain.model.account.Account;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by YJH on 2016/5/18.
 */
@Component
public class ApiAccountRepresentationMapper extends CustomMapper<Account, ApiAccountRepresentation> {

    @Autowired
    private IMappingService mappingService;

    public void mapAtoB(Account account, ApiAccountRepresentation representation, MappingContext context) {
        if (null != account.getHeadPic()) {
            PictureRepresentation data = mappingService.map(account.getHeadPic(), PictureRepresentation.class, false);
            representation.setHeadPic(data);
        }
    }

}
