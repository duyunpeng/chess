package chess.domain.service.withdraw;

import chess.application.user.command.MoneyCommand;
import chess.application.withdraw.command.CreateWithdrawCommand;
import chess.application.withdraw.command.EditWithdrawCommand;
import chess.application.withdraw.command.ListWithdrawCommand;
import chess.core.enums.WithdrawStatus;
import chess.core.exception.MoneyNotEnoughException;
import chess.core.util.CoreDateUtils;
import chess.core.util.CoreStringUtils;
import chess.domain.model.user.User;
import chess.domain.model.withdraw.IWithdrawRepository;
import chess.domain.model.withdraw.Withdraw;
import chess.domain.service.user.IUserService;
import chess.infrastructure.persistence.hibernate.generic.Pagination;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by pengyi on 2016/5/6.
 */
@Service("withdrawService")
public class WithdrawService implements IWithdrawService {

    @Autowired
    private IUserService userService;
    @Autowired
    private IWithdrawRepository<Withdraw, String> withdrawRepository;

    @Override
    public void apply(CreateWithdrawCommand command) throws MoneyNotEnoughException {

        User user = userService.searchByID(command.getUserId());

        if (user.getMoney().doubleValue() < command.getMoney().doubleValue()) {
            throw new MoneyNotEnoughException();
        }

        Withdraw withdraw = new Withdraw(user, command.getMoney(), WithdrawStatus.PENDING, new Date());
        withdrawRepository.save(withdraw);

        MoneyCommand moneyCommand = new MoneyCommand();
        moneyCommand.setId(command.getUserId());
        moneyCommand.setMoney(command.getMoney());
        moneyCommand.setDescribe("提现" + command.getMoney() + ",单号" + withdraw.getId());
        userService.subtractMoney(moneyCommand);
    }

    @Override
    public Pagination<Withdraw> list(ListWithdrawCommand command) {

        List<Criterion> criterionList = new ArrayList<Criterion>();
        criterionList.add(Restrictions.eq("user.id", command.getUserName()));

        List<Order> orderList = new ArrayList<Order>();
        orderList.add(Order.desc("createTime"));

        Map<String, String> aliasMap = new HashMap<String, String>();
        aliasMap.put("user", "user");
        return withdrawRepository.pagination(command.getPage(), command.getPageSize(), criterionList, aliasMap, orderList, null);
    }

    @Override
    public Pagination<Withdraw> pagination(ListWithdrawCommand command) {
        List<Criterion> criterionList = new ArrayList<Criterion>();
        if (!CoreStringUtils.isEmpty(command.getUserName())) {
            criterionList.add(Restrictions.like("user.userName", command.getUserName(), MatchMode.ANYWHERE));
        }
        if (null != command.getStatus()) {
            criterionList.add(Restrictions.eq("status", command.getStatus()));
        }
        if (!CoreStringUtils.isEmpty(command.getEndTime()) && !CoreStringUtils.isEmpty(command.getStartTime())) {
            criterionList.add(Restrictions.between("createDate", CoreDateUtils.parseDate(command.getStartTime(), "yyyy/MM/dd HH:mm"), CoreDateUtils.parseDate(command.getEndTime(), "yyyy/MM/dd HH:mm")));
        }

        List<Order> orderList = new ArrayList<Order>();
        orderList.add(Order.desc("createDate"));

        Map<String, String> aliasMap = new HashMap<String, String>();
        aliasMap.put("user", "user");
        return withdrawRepository.pagination(command.getPage(), command.getPageSize(), criterionList, aliasMap, orderList, null);
    }

    @Override
    public void finish(EditWithdrawCommand command) {
        Withdraw withdraw = withdrawRepository.getById(command.getId());
        withdraw.setStatus(WithdrawStatus.FINISH);
        withdrawRepository.save(withdraw);
    }

    @Override
    public List<Withdraw> exportExcel(ListWithdrawCommand command) {
        List<Criterion> criterionList = new ArrayList<>();
        Map<String, String> alias = new HashMap<>();
        if (!CoreStringUtils.isEmpty(command.getUserName())) {
            criterionList.add(Restrictions.like("user.userName", command.getUserName(), MatchMode.ANYWHERE));
            alias.put("user", "user");
        }

        if (null != command.getStatus()) {
            criterionList.add(Restrictions.eq("status", command.getStatus()));
        }

        if (!CoreStringUtils.isEmpty(command.getEndTime()) && !CoreStringUtils.isEmpty(command.getStartTime())) {
            criterionList.add(Restrictions.between("createTime", CoreDateUtils.parseDateStart(command.getStartTime()), CoreDateUtils.parseDateEnd(command.getEndTime())));
        }
        List<Order> orderList = new ArrayList<>();
        orderList.add(Order.desc("createTime"));
        return withdrawRepository.list(criterionList, orderList, null, null, alias);
    }
}
