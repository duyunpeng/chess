package chess.application.withdraw;

import chess.application.withdraw.command.CreateWithdrawCommand;
import chess.application.withdraw.command.EditWithdrawCommand;
import chess.application.withdraw.command.ListWithdrawCommand;
import chess.application.withdraw.representation.WithdrawRepresentation;
import chess.core.api.ApiResponse;
import chess.infrastructure.persistence.hibernate.generic.Pagination;


/**
 * Created by pengyi on 2016/5/6.
 */
public interface IWithdrawAppService {
    ApiResponse apply(CreateWithdrawCommand command);

    ApiResponse list(ListWithdrawCommand command);

    Pagination<WithdrawRepresentation> pagination(ListWithdrawCommand command);

    void finish(EditWithdrawCommand command);

}
