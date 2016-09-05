package chess.application.account;


import chess.application.account.command.*;
import chess.application.account.representation.AccountRepresentation;
import chess.application.auth.command.LoginCommand;
import chess.application.shared.command.SharedCommand;
import chess.infrastructure.persistence.hibernate.generic.Pagination;

import java.util.List;

/**
 * Created by YJH on 2016/3/30.
 */
public interface IAccountAppService {

    Pagination<AccountRepresentation> pagination(ListAccountCommand command);

    List<AccountRepresentation> list(ListAccountCommand command);

    AccountRepresentation searchByID(String id);

    AccountRepresentation searchByAccountName(String userName);

    AccountRepresentation create(CreateAccountCommand command);

    AccountRepresentation edit(EditAccountCommand command);

    void updateStatus(SharedCommand command);

    void resetPassword(ResetPasswordCommand command);

    void authorized(AuthorizeAccountCommand command);

    AccountRepresentation login(LoginCommand command);

    Pagination<AccountRepresentation> paginationJSON(ListAccountCommand command);

    void updateHeadPic(UpdateHeadPicCommand command);
}
