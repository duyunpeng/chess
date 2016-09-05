package chess.core.exception;

/**
 * Created by YJH on 2016/5/26.
 */
public class NotAdminException extends RuntimeException {

    public NotAdminException() {
    }

    public NotAdminException(String message) {
        super(message);
    }

    public NotAdminException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotAdminException(Throwable cause) {
        super(cause);
    }

}
