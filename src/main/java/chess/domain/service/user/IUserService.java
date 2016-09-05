package chess.domain.service.user;

import chess.application.account.command.ResetPasswordCommand;
import chess.application.shared.command.SharedCommand;
import chess.application.user.command.*;
import chess.application.user.representation.UserRepresentation;
import chess.core.common.BasicPaginationCommand;
import chess.domain.model.user.User;
import chess.infrastructure.persistence.hibernate.generic.Pagination;
import org.hibernate.LockOptions;

import java.util.List;

/**
 * Created by YJH
 * Date : 2016/4/19.
 */
public interface IUserService {
    Pagination<User> pagination(ListUserCommand command);

    User searchByName(String userName);

    boolean checkDeviceNo(String deviceNo);

    User searchByName(String userName, LockOptions lockOptions);

    User searchByID(String id);

    User searchByID(String id, LockOptions lockOptions);

    User create(CreateUserCommand command);

    User edit(EditUserCommand command);

    void update(User user);

    void save(User user);

    void addMoney(MoneyCommand command);

    void subtractMoney(MoneyCommand command);

    //api方法
    void apiCreate(RegisterCommand command);

    void apiResetPassword(ResetPasswordCommand command);

    void apiResetPayPassword(ResetPasswordCommand command);

    void apiUpdateHeadPic(EditUserCommand command);

    void apiUpdateInfo(EditUserCommand command);

    Pagination<User> apiRanking(RankingListCommand command);

    Pagination<User> userRanking(BasicPaginationCommand command);

    void updateVip(SharedCommand command);
}
