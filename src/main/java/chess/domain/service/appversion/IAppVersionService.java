package chess.domain.service.appversion;

import chess.application.appversion.command.CreateAppVersionCommand;
import chess.application.appversion.command.EditAppVersionCommand;
import chess.application.appversion.command.ListAppVersionCommand;
import chess.application.shared.command.SharedCommand;
import chess.domain.model.appversion.AppVersion;
import chess.infrastructure.persistence.hibernate.generic.Pagination;

import java.util.List;

/**
 * Created by LvDi on 2016/4/19.
 */
public interface IAppVersionService {
    Pagination<AppVersion> pagination(ListAppVersionCommand command);

    AppVersion searchByID(String id);

    AppVersion create(CreateAppVersionCommand command);

    AppVersion edit(EditAppVersionCommand command);

    void updateStatus(SharedCommand command);

    /***********Api 方法 **************/

    List<AppVersion> allList();

}
