package chess.core.api;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by YJH on 2016/4/15.
 */
public enum ApiReturnCode {

    // 通用错误码
    ERROR_UNKNOWN("未知错误", 0, Boolean.FALSE),
    NO_FOUND("数据不存在", 1, Boolean.FALSE),
    SUCCESS("处理成功", 2, Boolean.FALSE),
    FAILURE("处理失败", 3, Boolean.FALSE),
    ERROR_DATA("数据异常", 4, Boolean.FALSE),


    //api 错误码
    AUTHENTICATION_FAILURE("鉴权失败", 1000, Boolean.FALSE),
    ILLEGAL_ARGUMENT("不合法参数", 1000, Boolean.FALSE),

    ERROR_EXIST_ACCOUNT("用户名已存在", 1100, Boolean.FALSE),
    ERROR_DATA_NOT_FOUND("相关数据没有找到", 1101, Boolean.FALSE),
    ERROR_NO_ACCOUNT("用户不存在", 1102, Boolean.FALSE),
    ERROR_CODE_NOT_EXPIRED("验证码未过期", 1103, Boolean.FALSE),
    ERROR_NO_CODE("请先发送验证码", 1104, Boolean.FALSE),
    ERROR_CODE_NOT_EQ("验证码错误", 1105, Boolean.FALSE),
    ERROR_ACCOUNT_PASSWORD_NOT_EQ("用户名或密码错误", 1106, Boolean.FALSE),
    ERROR_ACCOUNT_LOCKED("账户被禁用", 1107, Boolean.FALSE),
    ERROR_NO_LOGIN("没有登录", 1108, Boolean.FALSE),
    ERROR_MONEY_NOT_ENOUGH("余额不足", 1109, Boolean.FALSE),
    ERROR_EXIST_CLICK_LIKE("已经点赞", 1110, Boolean.FALSE),
    ERROR_EXIST_SIGN("已签到", 1111, Boolean.FALSE),
    ERROR_NOT_FRIEND("对方不是您的好友", 1112, Boolean.FALSE),
    ERROR_EXIST_FRIEND("对方已经是您的好友", 1113, Boolean.FALSE),
    ERROR_ID_CARD_NOT_EQ("身份证信息错误", 1114, Boolean.FALSE),
    ERROR_ID_CARD_FAILURE("身份证信息验证失败", 1115, Boolean.FALSE),
    ERROR_ID_CARD_REPEAT("已经认证或待审核中", 1116, Boolean.FALSE),
    ERROR_WAIT_FRIEND_ANSWER("已发送添加好友请求", 1117, Boolean.FALSE),
    ERROR_NOT_GROUP_ADMIN("不是群管理员", 1118, Boolean.FALSE);


    private ApiReturnCode(String name, int value, Boolean onlyQuery) {
        this.name = name;
        this.value = value;
        this.onlyQuery = onlyQuery;
    }

    private static Map<Integer, ApiReturnCode> CACHE = new HashMap<Integer, ApiReturnCode>(ApiReturnCode.values().length * 2);

    static {
        for (ApiReturnCode apiReturnCode : ApiReturnCode.values()) {
            CACHE.put(apiReturnCode.getValue(), apiReturnCode);
        }
    }

    private String name;

    private int value;

    private Boolean onlyQuery;                  // 仅用于页面查询和业务逻辑无关

    public String getName() {
        return name;
    }

    public Integer getValue() {
        return value;
    }

    public Boolean isOnlyQuery() {
        return onlyQuery;
    }

    public static ApiReturnCode valueOf(int value) {
        return CACHE.get(value);
    }
}
