package chess.core.exception;

/**
 * Created by pengyi on 2016/1/21.
 */
public class SocketException extends Exception {

    private int code;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public SocketException(int code) {
        super();
        this.code = code;
    }

    public SocketException(String message, int code) {
        super(message);
        this.code = code;
    }

    public SocketException(String message, Throwable cause, int code) {
        super(message, cause);
        this.code = code;
    }

    public SocketException(Throwable cause, int code) {
        super(cause);
        this.code = code;
    }

    public SocketException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, int code) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.code = code;
    }
}
