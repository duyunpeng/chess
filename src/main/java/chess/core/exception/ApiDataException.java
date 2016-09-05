package chess.core.exception;

import chess.core.api.ApiResponse;

/**
 * Created by yjh on 16-6-3.
 */
public class ApiDataException extends RuntimeException {

    private ApiResponse response;

    public ApiDataException() {
        super("API鉴权失败");
        this.response = null;
    }

    public ApiDataException(ApiResponse response) {
        super("API鉴权失败");
        this.response = response;
    }

    public ApiDataException(String message) {
        super(message);
        this.response = null;
    }

    public ApiDataException(String message, ApiResponse response) {
        super(message);
        this.response = response;
    }

    public ApiDataException(String message, Throwable throwable) {
        super(message, throwable);
        this.response = null;
    }

    public ApiDataException(String message, Throwable throwable, ApiResponse response) {
        super(message, throwable);
        this.response = response;
    }

    public ApiDataException(Throwable throwable) {
        super(throwable);
        this.response = null;
    }

    public ApiDataException(Throwable throwable, ApiResponse response) {
        super(throwable);
        this.response = response;
    }

    public ApiResponse getResponse() {
        return response;
    }

}
