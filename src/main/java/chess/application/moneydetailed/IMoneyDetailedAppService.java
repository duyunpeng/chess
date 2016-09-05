package chess.application.moneydetailed;

import chess.application.moneydetailed.command.ListMoneyDetailedCommand;
import chess.application.moneydetailed.representation.MoneyDetailedRepresentation;
import chess.infrastructure.persistence.hibernate.generic.Pagination;

/**
 * Created by YJH
 * Date : 16-7-11.
 */
public interface IMoneyDetailedAppService {
    Pagination<MoneyDetailedRepresentation> pagination(ListMoneyDetailedCommand command);
}
