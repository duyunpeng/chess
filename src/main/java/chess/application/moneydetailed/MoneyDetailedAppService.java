package chess.application.moneydetailed;

import chess.application.moneydetailed.command.ListMoneyDetailedCommand;
import chess.application.moneydetailed.representation.MoneyDetailedRepresentation;
import chess.core.mapping.IMappingService;
import chess.domain.model.moneydetailed.MoneyDetailed;
import chess.domain.service.moneydetailed.IMoneyDetailedService;
import chess.infrastructure.persistence.hibernate.generic.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by YJH
 * Date : 16-7-11.
 */
@Service("moneyDetailedAppService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class MoneyDetailedAppService implements IMoneyDetailedAppService {

    private final IMoneyDetailedService moneyDetailedService;

    private final IMappingService mappingService;

    @Autowired
    public MoneyDetailedAppService(IMoneyDetailedService moneyDetailedService, IMappingService mappingService) {
        this.moneyDetailedService = moneyDetailedService;
        this.mappingService = mappingService;
    }

    @Override
    public Pagination<MoneyDetailedRepresentation> pagination(ListMoneyDetailedCommand command) {
        command.verifyPage();
        command.verifyPageSize(20);
        Pagination<MoneyDetailed> pagination = moneyDetailedService.pagination(command);
        List<MoneyDetailedRepresentation> data = mappingService.mapAsList(pagination.getData(), MoneyDetailedRepresentation.class);
        return new Pagination<>(data, pagination.getCount(), pagination.getPage(), pagination.getPageSize());
    }
}
