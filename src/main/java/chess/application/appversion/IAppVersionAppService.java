package chess.application.appversion;


import chess.application.appversion.command.CreateAppVersionCommand;
import chess.application.appversion.command.EditAppVersionCommand;
import chess.application.appversion.command.ListAppVersionCommand;
import chess.application.appversion.representation.AppVersionRepresentation;
import chess.application.shared.command.SharedCommand;
import chess.infrastructure.persistence.hibernate.generic.Pagination;

/**
 * Created by LvDi on 2016/4/19.
 */
public interface IAppVersionAppService {
    Pagination<AppVersionRepresentation> pagination(ListAppVersionCommand command);

    AppVersionRepresentation searchByID(String id);

    AppVersionRepresentation create(CreateAppVersionCommand command);

    AppVersionRepresentation edit(EditAppVersionCommand command);

    void updateStatus(SharedCommand command);


}
