package chess.core.exception;

import chess.core.api.ApiResponse;

/**
 *
 * Api鉴权失败
 * Created by YJH on 2016/4/15.
 */
public class ApiAuthenticationException extends Exception {

    private ApiResponse response;

    public ApiAuthenticationException() {
        super("API鉴权失败");
        this.response = null;
    }

    public ApiAuthenticationException(ApiResponse response) {
        super("API鉴权失败");
        this.response = response;
    }

    public ApiAuthenticationException(String message) {
        super(message);
        this.response = null;
    }

    public ApiAuthenticationException(String message, ApiResponse response) {
        super(message);
        this.response = response;
    }

    public ApiAuthenticationException(String message, Throwable throwable) {
        super(message, throwable);
        this.response = null;
    }

    public ApiAuthenticationException(String message, Throwable throwable, ApiResponse response) {
        super(message, throwable);
        this.response = response;
    }

    public ApiAuthenticationException(Throwable throwable) {
        super(throwable);
        this.response = null;
    }

    public ApiAuthenticationException(Throwable throwable, ApiResponse response) {
        super(throwable);
        this.response = response;
    }

    public ApiResponse getResponse() {
        return response;
    }

}
