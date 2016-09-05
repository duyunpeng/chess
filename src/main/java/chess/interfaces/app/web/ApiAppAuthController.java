package chess.interfaces.app.web;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import chess.application.account.representation.AccountRepresentation;
import chess.application.auth.IAuthAppService;
import chess.application.auth.command.LoginCommand;
import chess.application.user.IApiUserAppService;
import chess.application.user.command.RegisterCommand;
import chess.core.api.ApiResponse;
import chess.core.api.ApiReturnCode;
import chess.core.api.command.ApiVerificationCommand;
import chess.core.api.controller.BaseApiController;
import chess.core.common.Constants;
import chess.core.exception.ApiAuthenticationException;
import chess.core.exception.ApiUnknownException;
import chess.core.exception.ExistException;
import chess.core.exception.NoFoundException;
import chess.core.util.CoreHttpUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * Created by YJH on 2016/4/28.
 */
@Controller
@RequestMapping("/app/api")
public class ApiAppAuthController extends BaseApiController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private IApiUserAppService apiUserAppService;

    @Autowired
    private IAuthAppService authAppService;

    @RequestMapping(value = "/register")
    @ResponseBody
    public ApiResponse register(ApiVerificationCommand verificationCommand) {
        long startTime = System.currentTimeMillis();
        ApiResponse apiResponse;
        try {
            RegisterCommand command = this.authenticationAndConvert(verificationCommand, RegisterCommand.class);
            apiResponse = apiUserAppService.register(command);
        } catch (ApiUnknownException e) {
            logger.warn(e.getMessage());
            apiResponse = e.getResponse();
        } catch (ApiAuthenticationException e) {
            logger.warn(e.getMessage());
            apiResponse = e.getResponse();
        } catch (NoFoundException e) {
            logger.warn(e.getMessage());
            apiResponse = new ApiResponse(ApiReturnCode.ERROR_DATA_NOT_FOUND);
        } catch (ExistException e) {
            logger.warn(e.getMessage());
            apiResponse = new ApiResponse(ApiReturnCode.ERROR_EXIST_ACCOUNT);
        } catch (Exception e) {
            logger.error(e.getMessage());
            apiResponse = new ApiResponse(ApiReturnCode.ERROR_UNKNOWN);
        }
        apiResponse.setDebugTime(System.currentTimeMillis() - startTime);
        return apiResponse;
    }

    @RequestMapping(value = "/login")
    @ResponseBody
    public ApiResponse login(ApiVerificationCommand verificationCommand, HttpServletRequest request, HttpSession session) {
        long startTime = System.currentTimeMillis();
        ApiResponse apiResponse;
        try {
            LoginCommand command = this.authenticationAndConvert(verificationCommand, LoginCommand.class);
            String loginIP = CoreHttpUtils.getClientIP(request);
            String loginPlatform = CoreHttpUtils.getLoginPlatform(request);
            command.setLoginIP(loginIP);
            command.setLoginPlatform(loginPlatform);
            AccountRepresentation account = authAppService.login(command);
            Subject subject = SecurityUtils.getSubject();
            if (subject.hasRole("user")) {

                logger.info(subject.getPrincipal() + "登录成功！时间:" + new Date());
                session.setAttribute(Constants.SESSION_USER, account);
                apiResponse = new ApiResponse(ApiReturnCode.SUCCESS);
            } else {
                apiResponse = new ApiResponse(ApiReturnCode.ERROR_NO_ACCOUNT);
            }
        } catch (ApiUnknownException e) {
            logger.warn(e.getMessage());
            apiResponse = e.getResponse();
        } catch (ApiAuthenticationException e) {
            logger.warn(e.getMessage());
            apiResponse = e.getResponse();
        } catch (UnknownAccountException e) {
            logger.warn(e.getMessage());
            apiResponse = new ApiResponse(ApiReturnCode.ERROR_NO_ACCOUNT);
        } catch (IncorrectCredentialsException e) {
            logger.warn(e.getMessage());
            apiResponse = new ApiResponse(ApiReturnCode.ERROR_ACCOUNT_PASSWORD_NOT_EQ);
        } catch (LockedAccountException e) {
            logger.warn(e.getMessage());
            apiResponse = new ApiResponse(ApiReturnCode.ERROR_ACCOUNT_LOCKED);
        } catch (Exception e) {
            logger.error(e.getMessage());
            apiResponse = new ApiResponse(ApiReturnCode.ERROR_UNKNOWN);
        }
        apiResponse.setDebugTime(System.currentTimeMillis() - startTime);
        return apiResponse;
    }

    @RequestMapping(value = "/logout")
    @ResponseBody
    public ApiResponse logout(ApiVerificationCommand verificationCommand) {
        long startTime = System.currentTimeMillis();
        ApiResponse apiResponse;
        try {
            this.authenticationAndConvert(verificationCommand);
            Subject subject = SecurityUtils.getSubject();
            subject.logout();
            apiResponse = new ApiResponse(ApiReturnCode.SUCCESS);
        } catch (ApiAuthenticationException e) {
            logger.warn(e.getMessage());
            apiResponse = e.getResponse();
        } catch (Exception e) {
            logger.error(e.getMessage());
            apiResponse = new ApiResponse(ApiReturnCode.ERROR_UNKNOWN);
        }
        apiResponse.setDebugTime(System.currentTimeMillis() - startTime);
        return apiResponse;
    }

}
