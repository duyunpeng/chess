package chess.interfaces.message.web;

import chess.application.message.IMessageAppService;
import chess.application.message.command.ListMessageCommand;
import chess.interfaces.shared.web.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * Created by YJH
 * Date : 16-7-20.
 */
@Controller
@RequestMapping("/message")
public class MessageController extends BaseController {

    @Autowired
    private IMessageAppService messageAppService;

    @RequestMapping(value = "/pagination")
    public ModelAndView pagination(ListMessageCommand command) {
        return new ModelAndView("/message/list", "pagination", messageAppService.pagination(command))
                .addObject("command", command);
    }
}
