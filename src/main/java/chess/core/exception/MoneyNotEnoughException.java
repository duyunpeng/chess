package chess.core.exception;

/**
 * Created by YJH on 2016/4/27.
 */
public class MoneyNotEnoughException extends RuntimeException {

    public MoneyNotEnoughException() {
    }

    public MoneyNotEnoughException(String message) {
        super(message);
    }

    public MoneyNotEnoughException(String message, Throwable cause) {
        super(message, cause);
    }

    public MoneyNotEnoughException(Throwable cause) {
        super(cause);
    }

}
