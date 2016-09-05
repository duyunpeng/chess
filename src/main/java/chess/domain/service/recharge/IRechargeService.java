package chess.domain.service.recharge;

import chess.application.recharge.command.AlipayRechargeNotify;
import chess.application.recharge.command.CreateRechargeCommand;
import chess.application.recharge.command.ListRechargeCommand;
import chess.application.recharge.command.WechatRechargeNotify;
import chess.application.recharge.representation.AlipayInfo;
import chess.domain.model.recharge.Recharge;
import chess.infrastructure.persistence.hibernate.generic.Pagination;

/**
 * Created by YJH
 * Date : 16-7-9.
 */
public interface IRechargeService {
    Pagination<Recharge> pagination(ListRechargeCommand command);

    Recharge wechatPay(CreateRechargeCommand command) throws Exception;

    AlipayInfo alipayPay(CreateRechargeCommand command) throws Exception;

    Recharge getById(String agent_bill_id);

    boolean weChatPaySuccess(WechatRechargeNotify notify);

    boolean alipayPaySuccess(AlipayRechargeNotify notify);
}
