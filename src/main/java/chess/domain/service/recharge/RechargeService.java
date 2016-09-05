package chess.domain.service.recharge;

import chess.application.recharge.command.AlipayRechargeNotify;
import chess.application.recharge.command.CreateRechargeCommand;
import chess.application.recharge.command.ListRechargeCommand;
import chess.application.recharge.command.WechatRechargeNotify;
import chess.application.recharge.representation.AlipayInfo;
import chess.core.enums.PayType;
import chess.core.enums.YesOrNoStatus;
import chess.core.exception.ApiPayException;
import chess.core.util.*;
import chess.domain.model.recharge.IRechargeRepository;
import chess.domain.model.recharge.Recharge;
import chess.domain.model.user.User;
import chess.domain.service.user.IUserService;
import chess.infrastructure.persistence.hibernate.generic.Pagination;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.RoundingMode;
import java.net.URLEncoder;
import java.util.*;

/**
 * Created by YJH
 * Date : 16-7-9.
 */
@Service("rechargeService")
public class RechargeService implements IRechargeService {

    private final IRechargeRepository<Recharge, String> rechargeRepository;

    private final IUserService userService;

    @Autowired
    public RechargeService(IRechargeRepository<Recharge, String> rechargeRepository, IUserService userService) {
        this.rechargeRepository = rechargeRepository;
        this.userService = userService;
    }

    @Override
    public Pagination<Recharge> pagination(ListRechargeCommand command) {
        List<Criterion> criterionList = new ArrayList<>();
        Map<String, String> aliasMap = new HashMap<>();
        if (!CoreStringUtils.isEmpty(command.getUserName())) {
            criterionList.add(Restrictions.like("user.userName", command.getUserName()));
            aliasMap.put("user", "user");
        }
        if (!CoreStringUtils.isEmpty(command.getStartDate()) && null != CoreDateUtils.parseDate(command.getStartDate(), "yyyy/MM/dd HH:mm")) {
            criterionList.add(Restrictions.ge("createDate", CoreDateUtils.parseDate(command.getStartDate(), "yyyy/MM/dd HH:mm")));
        }
        if (!CoreStringUtils.isEmpty(command.getEndDate()) && null != CoreDateUtils.parseDate(command.getEndDate(), "yyyy/MM/dd HH:mm")) {
            criterionList.add(Restrictions.le("createDate", CoreDateUtils.parseDate(command.getEndDate(), "yyyy/MM/dd HH:mm")));
        }
        if (null != command.getIsSuccess() && command.getIsSuccess() != YesOrNoStatus.ALL) {
            criterionList.add(Restrictions.eq("isSuccess", command.getIsSuccess()));
        }
        List<Order> orderList = new ArrayList<>();
        orderList.add(Order.desc("createDate"));
        return rechargeRepository.pagination(command.getPage(), command.getPageSize(), criterionList, aliasMap, orderList, null);
    }

    @Override
    public Recharge wechatPay(CreateRechargeCommand command) throws Exception {
        User user = userService.searchByName(command.getUserName());
        Recharge recharge = new Recharge(user, command.getMoney(), YesOrNoStatus.NO, command.getPayType());
        rechargeRepository.save(recharge);
        String signString = "version=1" +
                "&agent_id=2071778" +
                "&agent_bill_id=" + recharge.getId() +
                "&agent_bill_time=" + CoreDateUtils.formatDate(recharge.getCreateDate(), "yyyyMMddHHmmss") +
                "&pay_type=" + (command.getPayType().compareTo(PayType.ALIPAY) == 0 ? 22 : 30) +
                "&pay_amt=" + command.getMoney().setScale(2, RoundingMode.HALF_UP) +
                "&notify_url=http://w.173600.com/recharge/wechat_notify" +
//                "&notify_url=http://58.17.133.6:90/recharge/wechat_notify" +
                "&user_ip=" + command.getIp() +
                "&key=DA6EABB2DA054B399C3B8AD0";

        String parm = "version=1" +
                "&agent_id=2071778" +
                "&agent_bill_id=" + recharge.getId() +
                "&agent_bill_time=" + CoreDateUtils.formatDate(recharge.getCreateDate(), "yyyyMMddHHmmss") +
                "&pay_type=" + (command.getPayType().compareTo(PayType.ALIPAY) == 0 ? 22 : 30) +
                "&pay_amt=" + command.getMoney().setScale(2, RoundingMode.HALF_UP) +
                "&notify_url=http://w.173600.com/recharge/wechat_notify" +
//                "&notify_url=http://58.17.133.6:90/recharge/wechat_notify" +
                "&user_ip=" + command.getIp() +
                "&goods_name=" + URLEncoder.encode("充值" + recharge.getMoney(), "gb2312") +
                "&return_url=http://w.173600.com" +
//                "&return_url=http://58.17.133.6:90" +
                "&goods_note=" + URLEncoder.encode("充值" + recharge.getMoney() + ",商户订单号" + recharge.getId(), "gb2312") +
                "&remark=" + recharge.getId() +
                "&sign=" + Md5Tools.MD5(signString);

        String token = CoreHttpUtils.urlConnection("http://pay.heepay.com/Phone/SDK/PayInit.aspx", parm);

        Document document = DocumentHelper.parseText(token.substring(39));
        if ("token_id".equals(document.getRootElement().getName())) {
            recharge.setToken_id(document.getStringValue());
        } else if ("error".equals(document.getRootElement().getName())) {
            throw new ApiPayException(document.getRootElement().getStringValue());
        }
        rechargeRepository.save(recharge);
        return recharge;
    }

    @Override
    public AlipayInfo alipayPay(CreateRechargeCommand command) throws Exception {
        User user = userService.searchByName(command.getUserName());
        Recharge recharge = new Recharge(user, command.getMoney(), YesOrNoStatus.NO, command.getPayType());
        rechargeRepository.save(recharge);
        AlipayInfo info = new AlipayInfo();
        info.setAmt(recharge.getMoney());
        info.setAppId("sk7ibygnavsce17yrd");
        info.setDesc(URLEncoder.encode("充值" + recharge.getMoney() + ",商户订单号" + recharge.getId(), "utf-8"));
        info.setFrpId("ALL-SDK");
        info.setGoodsName(URLEncoder.encode("充值" + recharge.getMoney(), "utf-8"));
        info.setMerchId("800108");
        info.setOrderNo(recharge.getId());
        info.setTradeType("APP-JUHE");
        info.setUrl("http://w.173600.com/recharge/alipay_notify");
//        info.setUrl("http://58.17.133.6:90/recharge/alipay_notify");
        String signString = info.getMerchId() + info.getAmt() + info.getGoodsName() + info.getDesc() + info.getUrl()
                + info.getAppId() + info.getTradeType() + info.getFrpId() + info.getOrderNo();
        info.setSign(DigestUtil.hmacSign(signString, "skappdvjWsEhSNlo2cYKm5MDyjCgVKOdDseTnLlebXIAC"));
        return info;
    }

    @Override
    public Recharge getById(String agent_bill_id) {
        return rechargeRepository.getById(agent_bill_id);
    }

    @Override
    public boolean weChatPaySuccess(WechatRechargeNotify notify) {
        if (notify.getSign().equals(Md5Tools.MD5(notify.signStr()))) {
            Recharge recharge = this.getById(notify.getAgent_bill_id());
            if (notify.getResult() == 1 && null != recharge && recharge.getIsSuccess() == YesOrNoStatus.NO && recharge.getMoney().compareTo(notify.getPay_amt()) == 0) {
                recharge.setPayType(PayType.WECHAT);
                recharge.setIsSuccess(YesOrNoStatus.YES);
                recharge.setPayNo(notify.getJnet_bill_no());
                recharge.setPayTime(new Date());
                rechargeRepository.save(recharge);
                User user = userService.searchByID(recharge.getUser().getId());
                user.changeMoney(user.getMoney().add(recharge.getMoney()));
                userService.update(user);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean alipayPaySuccess(AlipayRechargeNotify notify) {
        if (DigestUtil.hmacSign(notify.signStr(), "dvjWsEhSNlo2cYKm5MDyjCgVKOdDseTnLlebXIAC").equals(notify.getHmac())) {
            Recharge recharge = this.getById(notify.getR6_Order());
            if (notify.getR1_Code() == 1 && null != recharge && recharge.getIsSuccess() == YesOrNoStatus.NO && recharge.getMoney().compareTo(notify.getR3_Amt()) == 0) {
                recharge.setPayType(PayType.ALIPAY);
                recharge.setIsSuccess(YesOrNoStatus.YES);
                recharge.setPayNo(notify.getR2_TrxId());
                recharge.setPayTime(CoreDateUtils.parseDate(notify.getRp_PayDate(), "yyyyMMddHHmmss"));
                rechargeRepository.save(recharge);
                User user = userService.searchByID(recharge.getUser().getId());
                user.changeMoney(user.getMoney().add(recharge.getMoney()));
                userService.update(user);
                return true;
            }
        }
        return false;
    }
}
