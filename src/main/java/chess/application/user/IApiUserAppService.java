package chess.application.user;

import chess.application.account.command.ResetPasswordCommand;
import chess.application.shared.command.SharedCommand;
import chess.application.user.command.*;
import chess.application.user.representation.ApiUserRepresentation;
import chess.core.api.ApiResponse;
import chess.core.common.BasicPaginationCommand;

/**
 * Created by YJH
 * Date : 2016/4/28.
 */
public interface IApiUserAppService {
    ApiUserRepresentation searchByUserName(String userName);

    ApiResponse register(RegisterCommand command);

    ApiResponse info(SharedCommand command);

    ApiResponse resetPassword(ResetPasswordCommand command);

    ApiResponse resetPayPassword(ResetPasswordCommand command);

    ApiResponse updateHeadPic(EditUserCommand command);

    ApiResponse updateInfo(EditUserCommand command);

    ApiResponse ranking(RankingListCommand command);

    ApiResponse userRanking(BasicPaginationCommand command);
}
