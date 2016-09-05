package chess.application.recharge;

import chess.application.recharge.command.AlipayRechargeNotify;
import chess.application.recharge.command.CreateRechargeCommand;
import chess.application.recharge.command.ListRechargeCommand;
import chess.application.recharge.command.WechatRechargeNotify;
import chess.application.recharge.representation.RechargeRepresentation;
import chess.core.api.ApiResponse;
import chess.infrastructure.persistence.hibernate.generic.Pagination;

/**
 * Created by YJH
 * Date : 16-7-11.
 */
public interface IRechargeAppService {

    ApiResponse pay(CreateRechargeCommand command) throws Exception;

    Pagination<RechargeRepresentation> pagination(ListRechargeCommand command);

    boolean wechatPaySuccess(WechatRechargeNotify notify);

    boolean alipayPaySuccess(AlipayRechargeNotify notify);
}
