package chess.application.withdraw;

import chess.application.withdraw.command.CreateWithdrawCommand;
import chess.application.withdraw.command.EditWithdrawCommand;
import chess.application.withdraw.command.ListWithdrawCommand;
import chess.application.withdraw.representation.WithdrawRepresentation;
import chess.core.api.ApiResponse;
import chess.core.api.ApiReturnCode;
import chess.core.exception.MoneyNotEnoughException;
import chess.core.mapping.IMappingService;
import chess.domain.model.withdraw.Withdraw;
import chess.domain.service.withdraw.IWithdrawService;
import chess.infrastructure.persistence.hibernate.generic.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by pengyi on 2016/5/6.
 */
@Service("withdrawAppService")
@Transactional(propagation = Propagation.REQUIRED, readOnly = false, rollbackFor = Exception.class)
public class WithdrawAppService implements IWithdrawAppService {

    @Autowired
    private IWithdrawService withdrawService;

    @Autowired
    private IMappingService mappingService;

    @Override
    public ApiResponse apply(CreateWithdrawCommand command) {
        if (null == command.getMoney() || 0 == command.getMoney().intValue()) {
            return new ApiResponse(ApiReturnCode.ILLEGAL_ARGUMENT, ApiReturnCode.ILLEGAL_ARGUMENT.getName(), 0, null);
        }
        try {
            withdrawService.apply(command);
        } catch (MoneyNotEnoughException e) {
            return new ApiResponse(ApiReturnCode.ERROR_MONEY_NOT_ENOUGH, ApiReturnCode.ERROR_MONEY_NOT_ENOUGH.getName(), 0, null);
        }
        return new ApiResponse(ApiReturnCode.SUCCESS, ApiReturnCode.SUCCESS.getName(), 0, null);
    }

    @Override
    @Transactional(readOnly = true)
    public ApiResponse list(ListWithdrawCommand command) {
        command.verifyPage();
        command.verifyPageSize(20);
        Pagination<Withdraw> pagination = withdrawService.list(command);
        List<WithdrawRepresentation> representations = mappingService.mapAsList(pagination.getData(), WithdrawRepresentation.class);
        Pagination<WithdrawRepresentation> representationPagination = new Pagination<>(representations, pagination.getCount(), pagination.getPage(), pagination.getPageSize());
        return new ApiResponse(ApiReturnCode.SUCCESS, ApiReturnCode.SUCCESS.getName(), 0, representationPagination);
    }

    @Override
    @Transactional(readOnly = true)
    public Pagination<WithdrawRepresentation> pagination(ListWithdrawCommand command) {
        command.verifyPage();
        command.verifyPageSize(20);
        Pagination<Withdraw> pagination = withdrawService.pagination(command);
        List<WithdrawRepresentation> representations = mappingService.mapAsList(pagination.getData(), WithdrawRepresentation.class);
        return new Pagination<>(representations, pagination.getCount(), pagination.getPage(), pagination.getPageSize());
    }

    @Override
    public void finish(EditWithdrawCommand command) {
        withdrawService.finish(command);
    }

}
