package chess.application.user.representation.mapping;

import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import chess.application.area.representation.AreaRepresentation;
import chess.application.picture.representation.PictureRepresentation;
import chess.application.user.representation.ApiUserRepresentation;
import chess.application.user.representation.UserRepresentation;
import chess.core.common.Constants;
import chess.core.mapping.IMappingService;
import chess.domain.model.role.Role;
import chess.domain.model.user.User;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YJH on 2016/5/18.
 */
@Component
public class ApiUserRepresentationMapper extends CustomMapper<User, ApiUserRepresentation> {

    @Autowired
    private IMappingService mappingService;

    public void mapAtoB(User user, ApiUserRepresentation representation, MappingContext context) {
        if (null != user.getHeadPic()) {
            PictureRepresentation data = mappingService.map(user.getHeadPic(), PictureRepresentation.class, false);
            representation.setHeadPic(data);
        }
        if (null != user.getArea()) {
            AreaRepresentation data = mappingService.map(user.getArea(), AreaRepresentation.class, false);
            representation.setArea(data);
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
