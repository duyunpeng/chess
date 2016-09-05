package chess.application.user;

import chess.application.shared.command.SharedCommand;
import chess.application.user.command.MoneyCommand;
import chess.application.user.command.CreateUserCommand;
import chess.application.user.command.EditUserCommand;
import chess.application.user.command.ListUserCommand;
import chess.application.user.representation.UserRepresentation;
import chess.infrastructure.persistence.hibernate.generic.Pagination;

/**
 * Created by YJH
 * Date : 2016/4/19.
 */
public interface IUserAppService {

    Pagination<UserRepresentation> pagination(ListUserCommand command);

    UserRepresentation searchByID(String id);

    UserRepresentation create(CreateUserCommand command);

    UserRepresentation edit(EditUserCommand command);

    void addMoney(MoneyCommand command);

    void subtractMoney(MoneyCommand command);

    void updateVip(SharedCommand command);
}
