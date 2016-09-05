package chess.interfaces.app.web;

import chess.application.recharge.IRechargeAppService;
import chess.application.recharge.command.CreateRechargeCommand;
import chess.core.api.ApiResponse;
import chess.core.api.ApiReturnCode;
import chess.core.api.command.ApiVerificationCommand;
import chess.core.api.controller.BaseApiController;
import chess.core.exception.ApiPayException;
import chess.core.util.CoreHttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * Author pengyi
 * Date 16-8-9.
 */
@Controller
@RequestMapping("/app/api/recharge")
public class ApiAppRechargeController extends BaseApiController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final IRechargeAppService rechargeAppService;

    @Autowired
    public ApiAppRechargeController(IRechargeAppService rechargeAppService) {
        this.rechargeAppService = rechargeAppService;
    }


    @RequestMapping(value = "/pay")
    @ResponseBody
    public ApiResponse pay(ApiVerificationCommand verificationCommand, HttpSession session, HttpServletRequest request) {
        long startTime = System.currentTimeMillis();
        ApiResponse apiResponse;
        try {
            CreateRechargeCommand command = this.authenticationAndConvert(verificationCommand, CreateRechargeCommand.class);
            command.setUserName(CoreHttpUtils.getSessionAccount(session).getUserName());
            command.setIp(CoreHttpUtils.getClientIP(request));
            apiResponse = rechargeAppService.pay(command);
        } catch (ApiPayException e) {
            logger.error(e.getMessage());
            apiResponse = new ApiResponse(ApiReturnCode.FAILURE, e.getMessage(), null);
        } catch (Exception e) {
            logger.error(e.getMessage());
            apiResponse = new ApiResponse(ApiReturnCode.ERROR_UNKNOWN);
        }
        apiResponse.setDebugTime(System.currentTimeMillis() - startTime);
        return apiResponse;
    }
}
