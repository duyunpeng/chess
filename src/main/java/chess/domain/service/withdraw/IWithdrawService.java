package chess.domain.service.withdraw;


import chess.application.withdraw.command.CreateWithdrawCommand;
import chess.application.withdraw.command.EditWithdrawCommand;
import chess.application.withdraw.command.ListWithdrawCommand;
import chess.core.exception.MoneyNotEnoughException;
import chess.domain.model.withdraw.Withdraw;
import chess.infrastructure.persistence.hibernate.generic.Pagination;

import java.util.List;

/**
 * Created by pengyi on 2016/5/6.
 */
public interface IWithdrawService {
    void apply(CreateWithdrawCommand command) throws MoneyNotEnoughException;

    Pagination<Withdraw> list(ListWithdrawCommand command);

    Pagination<Withdraw> pagination(ListWithdrawCommand command);

    void finish(EditWithdrawCommand command);

    List<Withdraw> exportExcel(ListWithdrawCommand command);
}
