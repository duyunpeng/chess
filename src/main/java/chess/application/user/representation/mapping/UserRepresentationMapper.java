package chess.application.user.representation.mapping;

import chess.application.area.representation.AreaRepresentation;
import chess.application.picture.representation.PictureRepresentation;
import chess.application.user.representation.UserRepresentation;
import chess.core.mapping.IMappingService;
import chess.domain.model.user.User;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by YJH on 2016/4/19.
 */
@Component
public class UserRepresentationMapper extends CustomMapper<User, UserRepresentation> {

    @Autowired
    private IMappingService mappingService;

    public void mapAtoB(User user, UserRepresentation representation, MappingContext context) {
        if (null != user.getArea()) {
            AreaRepresentation data = mappingService.map(user.getArea(), AreaRepresentation.class, false);
            representation.setArea(data);
        }
        if (null != user.getHeadPic()) {
            PictureRepresentation data = mappingService.map(user.getHeadPic(), PictureRepresentation.class, false);
            representation.setHeadPic(data);
        }
        if (null != user.getLandlords()) {
            representation.setLandlordsWinCount(user.getLandlords().getWinCount());
            representation.setLandlordsLoseCount(user.getLandlords().getLoseCount());
        }
        if (null != user.getThreeCard()) {
            representation.setThreecareWinCount(user.getThreeCard().getWinCount());
            representation.setThreecareWinCount(user.getThreeCard().getLoseCount());
        }
    }

}
