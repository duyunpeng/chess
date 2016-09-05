package chess.core.exception;


import chess.core.api.ApiResponse;

/**
 * Created by YJH on 2016/4/22.
 */
public class ApiRemoteCallFailedException extends Exception {

    private ApiResponse response;

    public ApiRemoteCallFailedException() {
        super("API请求失败");
        this.response = null;
    }

    public ApiRemoteCallFailedException(ApiResponse response) {
        super("API请求失败");
        this.response = response;
    }

    public ApiRemoteCallFailedException(String message) {
        super(message);
        this.response = null;
    }

    public ApiRemoteCallFailedException(String message, ApiResponse response) {
        super(message);
        this.response = response;
    }

    public ApiRemoteCallFailedException(String message, Throwable throwable) {
        super(message, throwable);
        this.response = null;
    }

    public ApiRemoteCallFailedException(String message, Throwable throwable, ApiResponse response) {
        super(message, throwable);
        this.response = response;
    }

    public ApiRemoteCallFailedException(Throwable throwable) {
        super(throwable);
        this.response = null;
    }

    public ApiRemoteCallFailedException(Throwable throwable, ApiResponse response) {
        super(throwable);
        this.response = response;
    }

    public ApiResponse getResponse() {
        return response;
    }
}
