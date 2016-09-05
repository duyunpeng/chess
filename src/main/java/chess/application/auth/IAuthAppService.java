package chess.application.auth;


import chess.application.account.representation.AccountRepresentation;
import chess.application.auth.command.LoginCommand;
import chess.application.permission.representation.PermissionRepresentation;

import java.util.List;

/**
 * Created by YJH on 2016/4/5.
 */
public interface IAuthAppService {
    AccountRepresentation searchByAccountName(String userName);

    List<PermissionRepresentation> findAllPermission();

    AccountRepresentation login(LoginCommand command);
}
