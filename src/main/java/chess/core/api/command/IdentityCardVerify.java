package chess.core.api.command;

/**
 * Created by dyp on 2016/5/11.
 */
public class IdentityCardVerify extends BasicApiCommand{

    private Integer status;

    private Integer code;

    private String message;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public IdentityCardVerify(Integer code, String message, Integer status) {
        this.code = code;
        this.message = message;
        this.status = status;
    }
}
