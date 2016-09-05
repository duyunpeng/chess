package chess.core.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import chess.core.api.ApiResponse;
import chess.core.api.ApiReturnCode;
import chess.core.api.command.ApiVerificationCommand;
import chess.core.exception.ApiAuthenticationException;
import chess.core.exception.ApiUnknownException;

/**
 * Created by YJH on 2016/4/15.
 */
public abstract class BaseApiController {

    @Autowired
    protected ApiRequestVerifyConfig config;

    protected void authenticationAndConvert(ApiVerificationCommand command) throws ApiAuthenticationException {
        if (!command.verify(config.getKey(), config.getSecret())) {
            throw new ApiAuthenticationException(new ApiResponse(ApiReturnCode.AUTHENTICATION_FAILURE));
        }
    }

    protected <T> T authenticationAndConvert(ApiVerificationCommand command, Class<T> clz) throws ApiUnknownException, ApiAuthenticationException {
        T requestCommand = command.convertJsonTo(clz);
        if (command.verify(config.getKey(), config.getSecret())) {
            if (null == requestCommand) {
                throw new ApiUnknownException(new ApiResponse(ApiReturnCode.ILLEGAL_ARGUMENT));
            }
        } else {
            throw new ApiAuthenticationException(new ApiResponse(ApiReturnCode.AUTHENTICATION_FAILURE));
        }
        return requestCommand;
    }

}
