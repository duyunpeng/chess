package chess.core.exception;

/**
 *
 * 身份证验证不相等异常
 *
 * Created by YJH on 2016/5/20.
 */
public class IdentityCardVerifyNotEqualException extends RuntimeException {

    public IdentityCardVerifyNotEqualException() {
    }

    public IdentityCardVerifyNotEqualException(String message) {
        super(message);
    }

    public IdentityCardVerifyNotEqualException(String message, Throwable cause) {
        super(message, cause);
    }

    public IdentityCardVerifyNotEqualException(Throwable cause) {
        super(cause);
    }

}
