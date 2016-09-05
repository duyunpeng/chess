package chess.application.recharge;

import chess.application.recharge.command.AlipayRechargeNotify;
import chess.application.recharge.command.CreateRechargeCommand;
import chess.application.recharge.command.ListRechargeCommand;
import chess.application.recharge.command.WechatRechargeNotify;
import chess.application.recharge.representation.AlipayInfo;
import chess.application.recharge.representation.RechargeRepresentation;
import chess.core.api.ApiResponse;
import chess.core.api.ApiReturnCode;
import chess.core.enums.PayType;
import chess.core.mapping.IMappingService;
import chess.domain.model.recharge.Recharge;
import chess.domain.service.recharge.IRechargeService;
import chess.infrastructure.persistence.hibernate.generic.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by YJH
 * Date : 16-7-11.
 */
@Service("rechargeAppService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class RechargeAppService implements IRechargeAppService {

    private final IRechargeService rechargeService;

    private final IMappingService mappingService;

    @Autowired
    public RechargeAppService(IMappingService mappingService, IRechargeService rechargeService) {
        this.mappingService = mappingService;
        this.rechargeService = rechargeService;
    }

    @Override
    public ApiResponse pay(CreateRechargeCommand command) throws Exception {
        if (null == command.getMoney() || command.getMoney().compareTo(new BigDecimal(20)) < 0 || null == command.getPayType()) {
            return new ApiResponse(ApiReturnCode.ILLEGAL_ARGUMENT, ApiReturnCode.ILLEGAL_ARGUMENT.getName(), 0, null);
        }
        if (command.getPayType().compareTo(PayType.ALIPAY) == 0) {
            return new ApiResponse(ApiReturnCode.SUCCESS, ApiReturnCode.SUCCESS.getName(), 0,
                    mappingService.map(rechargeService.alipayPay(command), AlipayInfo.class, false));
        } else {
            return new ApiResponse(ApiReturnCode.SUCCESS, ApiReturnCode.SUCCESS.getName(), 0,
                    mappingService.map(rechargeService.wechatPay(command), RechargeRepresentation.class, false));
        }
    }

    @Override
    public Pagination<RechargeRepresentation> pagination(ListRechargeCommand command) {
        command.verifyPage();
        command.verifyPageSize(20);
        Pagination<Recharge> pagination = rechargeService.pagination(command);
        List<RechargeRepresentation> data = mappingService.mapAsList(pagination.getData(), RechargeRepresentation.class);
        return new Pagination<>(data, pagination.getCount(), pagination.getPage(), pagination.getPageSize());
    }

    @Override
    public boolean wechatPaySuccess(WechatRechargeNotify notify) {
        return rechargeService.weChatPaySuccess(notify);
    }

    @Override
    public boolean alipayPaySuccess(AlipayRechargeNotify notify) {
        return rechargeService.alipayPaySuccess(notify);
    }
}
