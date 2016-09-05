package chess.domain.service.account;


import chess.application.account.command.*;
import chess.application.auth.command.LoginCommand;
import chess.application.shared.command.SharedCommand;
import chess.domain.model.account.Account;
import chess.infrastructure.persistence.hibernate.generic.Pagination;

import java.util.List;

/**
 * Created by YJH on 2016/3/30.
 */
public interface IAccountService {

    Pagination<Account> pagination(ListAccountCommand command);

    List<Account> list(ListAccountCommand command);

    Account searchByID(String id);

    Account searchByAccountName(String userName);

    Account create(CreateAccountCommand command);

    Account edit(EditAccountCommand command);

    void updateStatus(SharedCommand command);

    void resetPassword(ResetPasswordCommand command);

    void authorized(AuthorizeAccountCommand command);

    Account login(LoginCommand command);

    List<Account> searchByIDs(List<String> ids);

    List<Account> searchByRoleIDs(List<String> ids);

    void updateHeadPic(UpdateHeadPicCommand command);
}
