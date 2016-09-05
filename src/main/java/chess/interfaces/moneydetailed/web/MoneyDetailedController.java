package chess.interfaces.moneydetailed.web;

import chess.application.moneydetailed.IMoneyDetailedAppService;
import chess.application.moneydetailed.command.ListMoneyDetailedCommand;
import chess.interfaces.shared.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by YJH
 * Date : 16-7-19.
 */
@Controller
@RequestMapping("/money_detailed")
public class MoneyDetailedController extends BaseController {

    private final IMoneyDetailedAppService moneyDetailedAppService;

    @Autowired
    public MoneyDetailedController(IMoneyDetailedAppService moneyDetailedAppService) {
        this.moneyDetailedAppService = moneyDetailedAppService;
    }

    @RequestMapping(value = "/pagination")
    public ModelAndView pagination(ListMoneyDetailedCommand command) {
        return new ModelAndView("/moneyDetailed/list", "pagination", moneyDetailedAppService.pagination(command))
                .addObject("command", command);
    }
}
