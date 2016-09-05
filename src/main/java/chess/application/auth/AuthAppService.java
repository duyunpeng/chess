package chess.application.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import chess.application.account.IAccountAppService;
import chess.application.account.representation.AccountRepresentation;
import chess.application.auth.command.LoginCommand;
import chess.application.permission.IPermissionAppService;
import chess.application.permission.command.ListPermissionCommand;
import chess.application.permission.representation.PermissionRepresentation;
import chess.core.common.Constants;
import chess.core.enums.EnableStatus;

import java.util.List;

/**
 * Created by YJH on 2016/4/5.
 */
@Service("authAppService")
public class AuthAppService implements IAuthAppService {

    @Autowired
    private IAccountAppService accountAppService;

    @Autowired
    private IPermissionAppService permissionAppService;

    @Override
    public AccountRepresentation searchByAccountName(String userName) {
        return accountAppService.searchByAccountName(userName);
    }

    @Override
    public List<PermissionRepresentation> findAllPermission() {
        ListPermissionCommand command = new ListPermissionCommand();
        command.setStatus(EnableStatus.ENABLE);
        return permissionAppService.list(command);
    }

    @Override
    public AccountRepresentation login(LoginCommand command) {
        return accountAppService.login(command);
    }
}
