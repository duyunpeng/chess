package chess.interfaces.withdraw.web;

import chess.application.withdraw.IWithdrawAppService;
import chess.application.withdraw.command.EditWithdrawCommand;
import chess.application.withdraw.command.ListWithdrawCommand;
import chess.interfaces.shared.web.AlertMessage;
import chess.interfaces.shared.web.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * Created by pengyi on 2016/5/6.
 */
@Controller
@RequestMapping("/withdraw")
public class WithdrawController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IWithdrawAppService withdrawAppService;

    @RequestMapping(value = "/finish")
    public ModelAndView finish(EditWithdrawCommand command, RedirectAttributes redirectAttributes) {
        AlertMessage alertMessage;
        try {
            withdrawAppService.finish(command);
        } catch (Exception e) {
            logger.warn(e.getMessage());
            alertMessage = new AlertMessage(AlertMessage.MessageType.WARNING, e.getMessage());
            redirectAttributes.addFlashAttribute(AlertMessage.MODEL_ATTRIBUTE_KEY, alertMessage);
            return new ModelAndView("redirect:/withdraw/pagination").addObject(AlertMessage.MODEL_ATTRIBUTE_KEY, alertMessage);
        }
        alertMessage = new AlertMessage(AlertMessage.MessageType.SUCCESS, AlertMessage.MessageType.SUCCESS.getName());
        redirectAttributes.addFlashAttribute(AlertMessage.MODEL_ATTRIBUTE_KEY, alertMessage);
        return new ModelAndView("redirect:/withdraw/pagination").addObject(AlertMessage.MODEL_ATTRIBUTE_KEY, alertMessage);
    }

    @RequestMapping(value = "/pagination")
    public ModelAndView list(ListWithdrawCommand command) {
        return new ModelAndView("/withdraw/list", "pagination", withdrawAppService.pagination(command))
                .addObject("command", command);
    }

}
