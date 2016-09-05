package chess.interfaces.robot;

import chess.application.robot.IRobotAppService;
import chess.application.robot.command.AddRobotCommand;
import chess.interfaces.shared.web.AlertMessage;
import chess.interfaces.shared.web.BaseController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Date;
import java.util.Locale;

/**
 * Created by pengyi on 2016/8/24.
 */
@Controller
@RequestMapping("/robot")
public class RobotController extends BaseController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final IRobotAppService robotAppService;

    @Autowired
    public RobotController(IRobotAppService robotAppService) {
        this.robotAppService = robotAppService;
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public ModelAndView add(@ModelAttribute("command") AddRobotCommand command) {
        return new ModelAndView("/robot/create", "command", command);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView add(AddRobotCommand command, BindingResult bindingResult,
                            RedirectAttributes redirectAttributes, Locale locale) {
        if (bindingResult.hasErrors()) {
            return new ModelAndView("/robot/create", "command", command);
        }
        AlertMessage alertMessage;
        try {
            robotAppService.add(command);
        } catch (Exception e) {
            logger.error(e.getMessage());
            alertMessage = new AlertMessage(AlertMessage.MessageType.DANGER,
                    this.getMessage("default.system.exception", new Object[]{e.getMessage()}, locale));
            return new ModelAndView("/error/500").addObject(AlertMessage.MODEL_ATTRIBUTE_KEY, alertMessage);
        }

        logger.info("创建robot[" + command.getGameType() + "]信息成功,时间:" + new Date());
        alertMessage = new AlertMessage(this.getMessage("default.create.success.messages", null, locale));
        redirectAttributes.addFlashAttribute(AlertMessage.MODEL_ATTRIBUTE_KEY, alertMessage);
        return new ModelAndView("redirect:/robot/create");
    }
}
