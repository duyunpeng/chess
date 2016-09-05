package chess.domain.service.moneydetailed;

import chess.application.moneydetailed.command.CreateMoneyDetailedCommand;
import chess.application.moneydetailed.command.ListMoneyDetailedCommand;
import chess.core.enums.FlowType;
import chess.core.util.CoreDateUtils;
import chess.core.util.CoreStringUtils;
import chess.domain.model.moneydetailed.IMoneyDetailedRepository;
import chess.domain.model.moneydetailed.MoneyDetailed;
import chess.domain.model.user.User;
import chess.domain.service.user.IUserService;
import chess.infrastructure.persistence.hibernate.generic.Pagination;
import org.hibernate.LockOptions;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by YJH
 * Date : 16-7-9.
 */
@Service("moneyDetailedService")
public class MoneyDetailedService implements IMoneyDetailedService {

    private final IMoneyDetailedRepository<MoneyDetailed, String> moneyDetailedRepository;

    private final IUserService userService;

    @Autowired
    public MoneyDetailedService(IUserService userService, IMoneyDetailedRepository<MoneyDetailed, String> moneyDetailedRepository) {
        this.userService = userService;
        this.moneyDetailedRepository = moneyDetailedRepository;
    }

    @Override
    public void create(CreateMoneyDetailedCommand command) {
        User user = userService.searchByName(command.getUserName(), LockOptions.READ);

        if (command.getFlowType() == FlowType.IN_FLOW) {
            BigDecimal oldMoney = user.getMoney();

            user.changeMoney(user.getMoney().add(command.getMoney()));

            userService.update(user);

            MoneyDetailed moneyDetailed = new MoneyDetailed(user, command.getFlowType(), command.getMoney(), command.getDescription(), oldMoney, user.getMoney());
            moneyDetailedRepository.save(moneyDetailed);
        } else {
            BigDecimal oldMoney = user.getMoney();

            user.changeMoney(user.getMoney().subtract(command.getMoney()));

            userService.update(user);

            MoneyDetailed moneyDetailed = new MoneyDetailed(user, command.getFlowType(), command.getMoney(), command.getDescription(), oldMoney, user.getMoney());
            moneyDetailedRepository.save(moneyDetailed);
        }
    }

    @Override
    public Pagination<MoneyDetailed> pagination(ListMoneyDetailedCommand command) {
        List<Criterion> criterionList = new ArrayList<>();
        Map<String, String> aliasMap = new HashMap<>();
        if (!CoreStringUtils.isEmpty(command.getUserName())) {
            criterionList.add(Restrictions.like("user.userName", command.getUserName()));
            aliasMap.put("user", "user");
        }
        if (!CoreStringUtils.isEmpty(command.getStartDate()) && null != CoreDateUtils.parseDate(command.getStartDate(),"yyyy/MM/dd HH:mm")) {
            criterionList.add(Restrictions.ge("createDate", CoreDateUtils.parseDate(command.getStartDate(),"yyyy/MM/dd HH:mm")));
        }
        if (!CoreStringUtils.isEmpty(command.getEndDate()) && null != CoreDateUtils.parseDate(command.getEndDate(),"yyyy/MM/dd HH:mm")) {
            criterionList.add(Restrictions.le("createDate", CoreDateUtils.parseDate(command.getEndDate(),"yyyy/MM/dd HH:mm")));
        }
        if (null != command.getFlowType() && command.getFlowType() != FlowType.ALL) {
            criterionList.add(Restrictions.eq("flowType", command.getFlowType()));
        }
        List<Order> orderList = new ArrayList<>();
        orderList.add(Order.desc("createDate"));
        return moneyDetailedRepository.pagination(command.getPage(), command.getPageSize(), criterionList, aliasMap, orderList, null);
    }
}
