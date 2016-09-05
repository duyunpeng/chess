package chess.interfaces.app.web;

import chess.application.account.command.ResetPasswordCommand;
import chess.application.shared.command.SharedCommand;
import chess.application.user.IApiUserAppService;
import chess.application.user.command.EditUserCommand;
import chess.core.api.ApiResponse;
import chess.core.api.ApiReturnCode;
import chess.core.api.command.ApiVerificationCommand;
import chess.core.api.controller.BaseApiController;
import chess.core.common.BasicPaginationCommand;
import chess.core.exception.ApiAuthenticationException;
import chess.core.exception.ApiUnknownException;
import chess.core.exception.NoFoundException;
import chess.core.exception.NoLoginException;
import chess.core.util.CoreHttpUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * Created by YJH on 2016/5/3.
 */
@Controller
@RequestMapping("/app/api/user")
public class ApiAppUserController extends BaseApiController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IApiUserAppService apiUserAppService;


    @RequestMapping(value = "/info")
    @ResponseBody
    public ApiResponse info(ApiVerificationCommand verificationCommand, HttpSession session) {
        long startTime = System.currentTimeMillis();
        ApiResponse apiResponse;
        try {
            this.authenticationAndConvert(verificationCommand);
            SharedCommand command = new SharedCommand();
            command.setId(CoreHttpUtils.getSessionAccount(session).getId());
            apiResponse = apiUserAppService.info(command);
        } catch (ApiAuthenticationException e) {
            logger.warn(e.getMessage());
            apiResponse = e.getResponse();
        } catch (NoLoginException e) {
            logger.warn(e.getMessage());
            apiResponse = new ApiResponse(ApiReturnCode.ERROR_NO_LOGIN);
        } catch (Exception e) {
            logger.error(e.getMessage());
            apiResponse = new ApiResponse(ApiReturnCode.ERROR_UNKNOWN);
        }
        apiResponse.setDebugTime(System.currentTimeMillis() - startTime);
        return apiResponse;
    }

    @RequestMapping(value = "/reset_pwd")
    @ResponseBody
    public ApiResponse resetPassword(ApiVerificationCommand verificationCommand, HttpSession session) {
        long startTime = System.currentTimeMillis();
        if (!session.getAttribute("verificationCode").toString().equals("true")) {
            return new ApiResponse(ApiReturnCode.ERROR_NO_CODE);
        }
        ApiResponse apiResponse;
        try {
            ResetPasswordCommand command = this.authenticationAndConvert(verificationCommand, ResetPasswordCommand.class);
            apiResponse = apiUserAppService.resetPassword(command);
            session.removeAttribute("verificationCode");
        } catch (ApiUnknownException e) {
            logger.warn(e.getMessage());
            apiResponse = e.getResponse();
        } catch (ApiAuthenticationException e) {
            logger.warn(e.getMessage());
            apiResponse = e.getResponse();
        } catch (NoFoundException e) {
            logger.warn(e.getMessage());
            apiResponse = new ApiResponse(ApiReturnCode.ERROR_NO_ACCOUNT);
        } catch (Exception e) {
            logger.error(e.getMessage());
            apiResponse = new ApiResponse(ApiReturnCode.ERROR_UNKNOWN);
        }
        apiResponse.setDebugTime(System.currentTimeMillis() - startTime);
        return apiResponse;
    }

    @RequestMapping(value = "/reset_pay_pwd")
    @ResponseBody
    public ApiResponse resetPayPassword(ApiVerificationCommand verificationCommand, HttpSession session) {
        long startTime = System.currentTimeMillis();
        if (!session.getAttribute("verificationCode").toString().equals("true")) {
            return new ApiResponse(ApiReturnCode.ERROR_NO_CODE);
        }
        ApiResponse apiResponse;
        try {
            ResetPasswordCommand command = this.authenticationAndConvert(verificationCommand, ResetPasswordCommand.class);
            apiResponse = apiUserAppService.resetPayPassword(command);
            session.removeAttribute("verificationCode");
        } catch (ApiUnknownException e) {
            logger.warn(e.getMessage());
            apiResponse = e.getResponse();
        } catch (ApiAuthenticationException e) {
            logger.warn(e.getMessage());
            apiResponse = e.getResponse();
        } catch (NoFoundException e) {
            logger.warn(e.getMessage());
            apiResponse = new ApiResponse(ApiReturnCode.ERROR_NO_ACCOUNT);
        } catch (Exception e) {
            logger.error(e.getMessage());
            apiResponse = new ApiResponse(ApiReturnCode.ERROR_UNKNOWN);
        }
        apiResponse.setDebugTime(System.currentTimeMillis() - startTime);
        return apiResponse;
    }

    @RequestMapping(value = "/update_head_pic")
    @ResponseBody
    public ApiResponse updateHeadPic(ApiVerificationCommand verificationCommand, HttpSession session) {
        long startTime = System.currentTimeMillis();
        ApiResponse apiResponse;
        try {
            EditUserCommand command = this.authenticationAndConvert(verificationCommand, EditUserCommand.class);
            command.setId(CoreHttpUtils.getSessionAccount(session).getId());
            apiResponse = apiUserAppService.updateHeadPic(command);
        } catch (ApiUnknownException e) {
            logger.warn(e.getMessage());
            apiResponse = e.getResponse();
        } catch (ApiAuthenticationException e) {
            logger.warn(e.getMessage());
            apiResponse = e.getResponse();
        } catch (NoLoginException e) {
            logger.warn(e.getMessage());
            apiResponse = new ApiResponse(ApiReturnCode.ERROR_NO_LOGIN);
        } catch (NoFoundException e) {
            logger.warn(e.getMessage());
            apiResponse = new ApiResponse(ApiReturnCode.ERROR_DATA_NOT_FOUND);
        } catch (Exception e) {
            logger.error(e.getMessage());
            apiResponse = new ApiResponse(ApiReturnCode.ERROR_UNKNOWN);
        }
        apiResponse.setDebugTime(System.currentTimeMillis() - startTime);
        return apiResponse;
    }

    @RequestMapping(value = "/update_info")
    @ResponseBody
    public ApiResponse updateInfo(ApiVerificationCommand verificationCommand, HttpSession session) {
        long startTime = System.currentTimeMillis();
        ApiResponse apiResponse;
        try {
            EditUserCommand command = this.authenticationAndConvert(verificationCommand, EditUserCommand.class);
            command.setId(CoreHttpUtils.getSessionAccount(session).getId());
            apiResponse = apiUserAppService.updateInfo(command);
        } catch (ApiUnknownException e) {
            logger.warn(e.getMessage());
            apiResponse = e.getResponse();
        } catch (ApiAuthenticationException e) {
            logger.warn(e.getMessage());
            apiResponse = e.getResponse();
        } catch (NoLoginException e) {
            logger.warn(e.getMessage());
            apiResponse = new ApiResponse(ApiReturnCode.ERROR_NO_LOGIN);
        } catch (NoFoundException e) {
            logger.warn(e.getMessage());
            apiResponse = new ApiResponse(ApiReturnCode.ERROR_DATA_NOT_FOUND);
        } catch (Exception e) {
            logger.error(e.getMessage());
            apiResponse = new ApiResponse(ApiReturnCode.ERROR_UNKNOWN);
        }
        apiResponse.setDebugTime(System.currentTimeMillis() - startTime);
        return apiResponse;
    }

    @RequestMapping(value = "/user_ranking")
    @ResponseBody
    public ApiResponse userRanking(ApiVerificationCommand verificationCommand) {
        long startTime = System.currentTimeMillis();
        ApiResponse apiResponse;
        try {
            BasicPaginationCommand command = this.authenticationAndConvert(verificationCommand, BasicPaginationCommand.class);
            apiResponse = apiUserAppService.userRanking(command);
        } catch (ApiUnknownException e) {
            logger.warn(e.getMessage());
            apiResponse = e.getResponse();
        } catch (ApiAuthenticationException e) {
            logger.warn(e.getMessage());
            apiResponse = e.getResponse();
        } catch (NoLoginException e) {
            logger.warn(e.getMessage());
            apiResponse = new ApiResponse(ApiReturnCode.ERROR_NO_LOGIN);
        } catch (NoFoundException e) {
            logger.warn(e.getMessage());
            apiResponse = new ApiResponse(ApiReturnCode.ERROR_DATA_NOT_FOUND);
        } catch (Exception e) {
            logger.error(e.getMessage());
            apiResponse = new ApiResponse(ApiReturnCode.ERROR_UNKNOWN);
        }
        apiResponse.setDebugTime(System.currentTimeMillis() - startTime);
        return apiResponse;
    }

}
