package chess.core.util;

import chess.application.account.representation.AccountRepresentation;
import chess.application.auth.command.LoginCommand;
import chess.core.common.CharsetConstant;
import chess.core.common.Constants;
import chess.core.exception.NoLoginException;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by YJH on 2016/4/5 0005.
 */
public class CoreHttpUtils {


    private static String[] HTTP_PROXY_HEADER_NAME = new String[]{
            "CLIENTIP",
            "X-FORWARDED-FOR"
    };

    private enum LoginPlatform {
        LINUX("PC", 0),
        WINDOW("PC", 1),
        IPHONE("iPhone", 2),
        ANDROID("Android", 3);

        private String name;
        private int value;

        LoginPlatform(String name, int value) {
            this.name = name;
            this.value = value;
        }

        public String getName() {
            return name;
        }

        public int getValue() {
            return value;
        }
    }

    public static String getClientIP(HttpServletRequest request) {
        for (String headerName : HTTP_PROXY_HEADER_NAME) {
            String clientIP = request.getHeader(headerName);
            if (StringUtils.isNotBlank(clientIP)) {
                return clientIP;
            }
        }
        return request.getRemoteAddr();
    }

    public static String getLoginPlatform(HttpServletRequest request) {
        String userAgent = request.getHeader("User-Agent").toUpperCase();
        if (userAgent.contains(LoginPlatform.WINDOW.name())) {
            return LoginPlatform.WINDOW.getName();
        } else if (userAgent.contains(LoginPlatform.IPHONE.name())) {
            return LoginPlatform.IPHONE.getName();
        } else if (userAgent.contains(LoginPlatform.ANDROID.name())) {
            return LoginPlatform.ANDROID.getName();
        } else if (userAgent.contains(LoginPlatform.LINUX.name())) {
            return LoginPlatform.LINUX.getName();
        }
        return null;
    }

    /**
     * 保存用户cookie
     *
     * @param command
     * @param request
     * @param response
     */
    public static void writeCookie(LoginCommand command, HttpServletRequest request, HttpServletResponse response) {
        if (command.isRememberMe()) {
            String encryptStr = command.getUserName() + "," + command.getPassword();
            Cookie cookie = new Cookie(Constants.COOKIE_USER, CoreRc4Utils.encry_RC4_string(encryptStr, Constants.PASSWORD_ENCRYP_KEY));
            cookie.setMaxAge(604800);
            response.addCookie(cookie);
        } else {
            clearCookie(request, response);
        }
    }

    /**
     * 移除用户保存的cookie
     *
     * @param request
     * @param response
     */
    public static void clearCookie(HttpServletRequest request, HttpServletResponse response) {
        Cookie[] cookies = request.getCookies();
        if (null != cookies && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(Constants.COOKIE_USER)) {
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                    break;
                }
            }
        }
    }

    public static AccountRepresentation getSessionAccount(HttpSession session) {
        AccountRepresentation account = (AccountRepresentation) session.getAttribute(Constants.SESSION_USER);
        if (null == account) {
            throw new NoLoginException("没有登录");
        }
        return account;
    }

    public static String urlConnection(String url, String pa) {

        String response = null;

        try {
            HttpURLConnection conn = (HttpURLConnection) new URL(url).openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod("POST");


            // Send data
            PrintWriter pw = new PrintWriter(new OutputStreamWriter(conn.getOutputStream(), CharsetConstant.UTF8_STRING));
            // pa为请求的参数
            pw.print(pa);
            pw.flush();
            pw.close();

            // Get the response!
            int httpResponseCode = conn.getResponseCode();
            if (httpResponseCode != HttpURLConnection.HTTP_OK) {
                throw new Exception("HTTP response code: " + httpResponseCode +
                        "\nurl:" + url);
            }

            InputStream inputStream = conn.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream, CharsetConstant.UTF8_STRING));
            StringBuilder builder = new StringBuilder();
            String readLine;
            while (null != (readLine = br.readLine())) {
                builder.append(readLine);
            }
            inputStream.close();
            response = builder.toString();

            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return response;
    }

}
