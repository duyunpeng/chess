package chess.core.exception;

/**
 *
 * 身份证验证失败
 *
 * Created by YJH on 2016/5/20.
 */
public class IdentityCardVerifyException extends RuntimeException {

    public IdentityCardVerifyException() {
    }

    public IdentityCardVerifyException(String message) {
        super(message);
    }

    public IdentityCardVerifyException(String message, Throwable cause) {
        super(message, cause);
    }

    public IdentityCardVerifyException(Throwable cause) {
        super(cause);
    }

}
