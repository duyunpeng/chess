package chess.interfaces.app.web;

import chess.application.user.representation.UserRepresentation;
import chess.application.withdraw.IWithdrawAppService;
import chess.application.withdraw.command.CreateWithdrawCommand;
import chess.application.withdraw.command.ListWithdrawCommand;
import chess.core.api.ApiResponse;
import chess.core.api.ApiReturnCode;
import chess.core.api.command.ApiVerificationCommand;
import chess.core.api.controller.BaseApiController;
import chess.core.common.Constants;
import chess.core.exception.ApiAuthenticationException;
import chess.core.exception.ApiUnknownException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by pengyi on 2016/5/6.
 */
@Controller
@RequestMapping("/app/api/withdraw")
public class ApiAppWithdrawController extends BaseApiController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IWithdrawAppService withdrawAppService;

    @RequestMapping(value = "/apply")
    @ResponseBody
    public ApiResponse apply(ApiVerificationCommand verificationCommand, HttpSession session) {
        long startTime = System.currentTimeMillis();
        ApiResponse apiResponse = null;
        CreateWithdrawCommand command = null;
        UserRepresentation user = (UserRepresentation) session.getAttribute(Constants.SESSION_USER);
        if (null == user) {
            return new ApiResponse(ApiReturnCode.ERROR_NO_LOGIN, ApiReturnCode.ERROR_NO_LOGIN.getName(),
                    System.currentTimeMillis() - startTime, null);
        }
        try {
            command = this.authenticationAndConvert(verificationCommand, CreateWithdrawCommand.class);
            command.setUserId(user.getId());
            apiResponse = withdrawAppService.apply(command);
        } catch (ApiUnknownException e) {
            logger.warn(e.getMessage());
            apiResponse = e.getResponse();
        } catch (ApiAuthenticationException e) {
            logger.warn(e.getMessage());
            apiResponse = e.getResponse();
        } catch (Exception e) {
            logger.warn(e.getMessage());
            apiResponse = new ApiResponse(ApiReturnCode.ERROR_UNKNOWN, ApiReturnCode.ERROR_UNKNOWN.getName(), 0, null);
        }
        apiResponse.setDebugTime(System.currentTimeMillis() - startTime);
        return apiResponse;
    }

    @RequestMapping(value = "/list")
    @ResponseBody
    public ApiResponse list(ApiVerificationCommand verificationCommand, HttpSession session) {
        long startTime = System.currentTimeMillis();
        ApiResponse apiResponse = null;
        UserRepresentation user = (UserRepresentation) session.getAttribute(Constants.SESSION_USER);
        if (null == user) {
            return new ApiResponse(ApiReturnCode.ERROR_NO_LOGIN, ApiReturnCode.ERROR_NO_LOGIN.getName(),
                    System.currentTimeMillis() - startTime, null);
        }
        try {
            ListWithdrawCommand command = this.authenticationAndConvert(verificationCommand, ListWithdrawCommand.class);
            command.setUserName(user.getUserName());
            apiResponse = withdrawAppService.list(command);
        } catch (ApiUnknownException e) {
            logger.warn(e.getMessage());
            apiResponse = e.getResponse();
        } catch (ApiAuthenticationException e) {
            logger.warn(e.getMessage());
            apiResponse = e.getResponse();
        } catch (Exception e) {
            logger.warn(e.getMessage());
            apiResponse = new ApiResponse(ApiReturnCode.ERROR_UNKNOWN, ApiReturnCode.ERROR_UNKNOWN.getName(), 0, null);
        }
        apiResponse.setDebugTime(System.currentTimeMillis() - startTime);
        return apiResponse;
    }

}
