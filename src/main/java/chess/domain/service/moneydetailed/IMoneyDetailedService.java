package chess.domain.service.moneydetailed;

import chess.application.moneydetailed.command.CreateMoneyDetailedCommand;
import chess.application.moneydetailed.command.ListMoneyDetailedCommand;
import chess.domain.model.moneydetailed.MoneyDetailed;
import chess.domain.model.user.User;
import chess.infrastructure.persistence.hibernate.generic.Pagination;

/**
 * Created by YJH
 * Date : 16-7-9.
 */
public interface IMoneyDetailedService {

    void create(CreateMoneyDetailedCommand command);

    Pagination<MoneyDetailed> pagination(ListMoneyDetailedCommand command);
}
