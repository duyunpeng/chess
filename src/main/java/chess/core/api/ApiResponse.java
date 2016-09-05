package chess.core.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by YJH on 2016/4/15.
 */
public class ApiResponse {

    protected static Logger logger = LoggerFactory.getLogger(ApiResponse.class.getName());

    public static ApiResponse DEFAULT_FAILED = new ApiResponse(ApiReturnCode.FAILURE);

    private ApiReturnCode code;     // API执行结果

    private String message;         // 执行结果信息

    private long debugTime;       // 远程调用执行时间，仅在调试模式时有此返回值，单位：s

    private Object data;           //返回数据

    public static Logger getLogger() {
        return logger;
    }

    public static void setLogger(Logger logger) {
        ApiResponse.logger = logger;
    }

    public ApiReturnCode getCode() {
        return code;
    }

    public void setCode(ApiReturnCode code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public long getDebugTime() {
        return debugTime;
    }

    public void setDebugTime(long debugTime) {
        this.debugTime = debugTime;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ApiResponse() {
    }

    public ApiResponse(ApiReturnCode code) {
        this(code, code.getName(), null);
    }

    public ApiResponse(ApiReturnCode code, String message, Object data) {
        this(code, message, 0, data);
    }

    public ApiResponse(ApiReturnCode code, String message, long debugTime, Object data) {
        this.code = code;
        this.message = message;
        this.debugTime = debugTime;
        this.data = data;
    }

}
