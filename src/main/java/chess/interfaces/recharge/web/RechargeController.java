package chess.interfaces.recharge.web;

import chess.application.recharge.IRechargeAppService;
import chess.application.recharge.command.AlipayRechargeNotify;
import chess.application.recharge.command.ListRechargeCommand;
import chess.application.recharge.command.WechatRechargeNotify;
import chess.interfaces.shared.web.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by YJH
 * Date : 16-7-19.
 */
@Controller
@RequestMapping("/recharge")
public class RechargeController extends BaseController {

    private final IRechargeAppService rechargeAppService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public RechargeController(IRechargeAppService rechargeAppService) {
        this.rechargeAppService = rechargeAppService;
    }

    @RequestMapping(value = "/pagination")
    public ModelAndView pagination(ListRechargeCommand command) {
        return new ModelAndView("/recharge/list", "pagination", rechargeAppService.pagination(command))
                .addObject("command", command);
    }

    @RequestMapping(value = "/wechat_notify")
    @ResponseBody
    public String wechatNotify(WechatRechargeNotify notify) {
        logger.info("支付结果异步通知，外部订单号【" + notify.getJnet_bill_no() + "】,内部订单号【"
                + notify.getAgent_bill_id() + "】订单金额【" + notify.getPay_amt() + "】,订单结果【" + notify.getResult() + "】");
        return rechargeAppService.wechatPaySuccess(notify) ? "ok" : "error";
    }

    @RequestMapping(value = "/alipay_notify")
    @ResponseBody
    public String alipayNotify(AlipayRechargeNotify notify) {
        logger.info("支付结果异步通知，外部订单号【" + notify.getR2_TrxId() + "】,内部订单号【" + notify.getR6_Order()
                + "】订单金额【" + notify.getR3_Amt() + "】,订单结果【" + notify.getR1_Code() + "】");
        return rechargeAppService.alipayPaySuccess(notify) ? "ok" : "error";
    }
}
