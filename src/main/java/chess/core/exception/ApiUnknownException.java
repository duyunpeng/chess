package chess.core.exception;

import chess.core.api.ApiResponse;

/**
 * Api错误
 * Created by YJH on 2016/4/15.
 */
public class ApiUnknownException extends Exception {

    private ApiResponse response;

    public ApiUnknownException() {
        super("API请求失败");
        this.response = null;
    }

    public ApiUnknownException(ApiResponse response) {
        super("API请求失败");
        this.response = response;
    }

    public ApiUnknownException(String message) {
        super(message);
        this.response = null;
    }

    public ApiUnknownException(String message, ApiResponse response) {
        super(message);
        this.response = response;
    }

    public ApiUnknownException(String message, Throwable throwable) {
        super(message, throwable);
        this.response = null;
    }

    public ApiUnknownException(String message, Throwable throwable, ApiResponse response) {
        super(message, throwable);
        this.response = response;
    }

    public ApiUnknownException(Throwable throwable) {
        super(throwable);
        this.response = null;
    }

    public ApiUnknownException(Throwable throwable, ApiResponse response) {
        super(throwable);
        this.response = response;
    }

    public ApiResponse getResponse() {
        return response;
    }
}
