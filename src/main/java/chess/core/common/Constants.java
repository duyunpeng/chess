package chess.core.common;

/**
 * Created by YJH on 2016/4/10 0010.
 */
public class Constants {

    public static final String SESSION_USER = "sessionUser";
    public static final String COOKIE_USER = "cookieUser";
    public static final String PASSWORD_ENCRYP_KEY = "HTML5";
    public static final String WEB_SOCKET_USER = "webSocketUser";

    //短信redis缓存过期时间  单位秒
    public static final Integer REDIS_SMS_TIME_OUT = 300;
    //游戏redis缓存过期时间  单位秒
    public static final Integer REDIS_GAME_TIME_OUT = 600;

}
