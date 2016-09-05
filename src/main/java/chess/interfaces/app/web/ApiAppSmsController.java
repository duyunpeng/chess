package chess.interfaces.app.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import chess.application.account.IAccountAppService;
import chess.application.shared.command.SmsCommand;
import chess.core.api.ApiResponse;
import chess.core.api.ApiReturnCode;
import chess.core.api.command.ApiVerificationCommand;
import chess.core.api.controller.BaseApiController;
import chess.core.common.Constants;
import chess.core.exception.ApiAuthenticationException;
import chess.core.exception.ApiUnknownException;
import chess.core.redis.RedisService;
import chess.core.sms.obj.SmsTemplate;
import chess.core.sms.service.SmsSender;
import chess.core.util.CoreStringUtils;

import javax.servlet.http.HttpSession;

/**
 * Created by YJH on 2016/4/28.
 */
@Controller
@RequestMapping("/app/api/sms")
public class ApiAppSmsController extends BaseApiController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private RedisService redisService;

    @Autowired
    private IAccountAppService accountAppService;

    @Autowired
    private SmsSender smsSender;

    @RequestMapping(value = "/sms_register")
    @ResponseBody
    public ApiResponse registerSms(ApiVerificationCommand verificationCommand) {
        long startTime = System.currentTimeMillis();
        ApiResponse apiResponse;
        try {
            SmsCommand command = this.authenticationAndConvert(verificationCommand, SmsCommand.class);
            if (null == accountAppService.searchByAccountName(command.getPhone())) {
                if (redisService.exists(command.getPhone())) {
                    apiResponse = new ApiResponse(ApiReturnCode.ERROR_CODE_NOT_EXPIRED);
                } else {
                    String code = CoreStringUtils.randomNum(6);

                    //发送短信
                    smsSender.send(command.getPhone(), code, SmsTemplate.REGISTER);

                    redisService.addCache(command.getPhone(), code, Constants.REDIS_SMS_TIME_OUT);
                    apiResponse = new ApiResponse(ApiReturnCode.SUCCESS);
                }
            } else {
                apiResponse = new ApiResponse(ApiReturnCode.ERROR_EXIST_ACCOUNT);
            }
        } catch (ApiUnknownException e) {
            logger.warn(e.getMessage());
            apiResponse = e.getResponse();
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


    @RequestMapping(value = "/sms_password")
    @ResponseBody
    public ApiResponse passwordSms(ApiVerificationCommand verificationCommand) {
        long startTime = System.currentTimeMillis();
        ApiResponse apiResponse;
        try {
            SmsCommand command = this.authenticationAndConvert(verificationCommand, SmsCommand.class);
            if (null != accountAppService.searchByAccountName(command.getPhone())) {
                if (redisService.exists(command.getPhone())) {
                    apiResponse = new ApiResponse(ApiReturnCode.ERROR_CODE_NOT_EXPIRED);
                } else {
                    String code = CoreStringUtils.randomNum(6);

                    //发送短信
                    smsSender.send(command.getPhone(), code, SmsTemplate.RESETPWD);

                    redisService.addCache(command.getPhone(), code, Constants.REDIS_SMS_TIME_OUT);
                    apiResponse = new ApiResponse(ApiReturnCode.SUCCESS);
                }
            } else {
                apiResponse = new ApiResponse(ApiReturnCode.ERROR_NO_ACCOUNT);
            }
        } catch (ApiUnknownException e) {
            logger.warn(e.getMessage());
            apiResponse = e.getResponse();
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

    @RequestMapping(value = "/sms_pay_password")
    @ResponseBody
    public ApiResponse payPasswordSms(ApiVerificationCommand verificationCommand) {
        long startTime = System.currentTimeMillis();
        ApiResponse apiResponse;
        try {
            SmsCommand command = this.authenticationAndConvert(verificationCommand, SmsCommand.class);
            if (null != accountAppService.searchByAccountName(command.getPhone())) {
                if (redisService.exists(command.getPhone())) {
                    apiResponse = new ApiResponse(ApiReturnCode.ERROR_CODE_NOT_EXPIRED);
                } else {
                    //TODO 发送支付密码短信

                    String code = CoreStringUtils.randomNum(6);
                    redisService.addCache(command.getPhone(), code, Constants.REDIS_SMS_TIME_OUT);
                    apiResponse = new ApiResponse(ApiReturnCode.SUCCESS);
                }
            } else {
                apiResponse = new ApiResponse(ApiReturnCode.ERROR_NO_ACCOUNT);
            }
        } catch (ApiUnknownException e) {
            logger.warn(e.getMessage());
            apiResponse = e.getResponse();
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

    @RequestMapping(value = "/verification_code")
    @ResponseBody
    public ApiResponse verificationCode(ApiVerificationCommand verificationCommand, HttpSession session) {
        long startTime = System.currentTimeMillis();
        ApiResponse apiResponse;
        try {
            SmsCommand command = this.authenticationAndConvert(verificationCommand, SmsCommand.class);
            if (redisService.exists(command.getPhone())) {
                if (redisService.getCache(command.getPhone()).equals(command.getVerificationCode())) {
                    apiResponse = new ApiResponse(ApiReturnCode.SUCCESS);
                    session.setAttribute("verificationCode", "true");
                    redisService.delete(command.getPhone());
                } else {
                    apiResponse = new ApiResponse(ApiReturnCode.ERROR_CODE_NOT_EQ);
                }
            } else {
                apiResponse = new ApiResponse(ApiReturnCode.ERROR_NO_CODE);
            }
        } catch (ApiUnknownException e) {
            logger.warn(e.getMessage());
            apiResponse = e.getResponse();
        } catch (ApiAuthenticationException e) {
            logger.warn(e.getMessage());
            apiResponse = e.getResponse();
        }
        apiResponse.setDebugTime(System.currentTimeMillis() - startTime);
        return apiResponse;
    }

    @RequestMapping(value = "/sms_change_user_name")
    @ResponseBody
    public ApiResponse changeUserNameSms(ApiVerificationCommand verificationCommand) {
        long startTime = System.currentTimeMillis();
        ApiResponse apiResponse;
        try {
            SmsCommand command = this.authenticationAndConvert(verificationCommand, SmsCommand.class);
            if (null == accountAppService.searchByAccountName(command.getPhone())) {
                if (redisService.exists(command.getPhone())) {
                    apiResponse = new ApiResponse(ApiReturnCode.ERROR_CODE_NOT_EXPIRED);
                } else {
                    String code = CoreStringUtils.randomNum(6);

                    //发送短信
                    smsSender.send(command.getPhone(), code, SmsTemplate.CHANGE_USERNAME);

                    redisService.addCache(command.getPhone(), code, Constants.REDIS_SMS_TIME_OUT);
                    apiResponse = new ApiResponse(ApiReturnCode.SUCCESS);
                }
            } else {
                apiResponse = new ApiResponse(ApiReturnCode.ERROR_EXIST_ACCOUNT);
            }
        } catch (ApiUnknownException e) {
            logger.warn(e.getMessage());
            apiResponse = e.getResponse();
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
