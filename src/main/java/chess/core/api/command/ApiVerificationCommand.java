package chess.core.api.command;

import chess.core.util.CoreStringUtils;

/**
 * Created by YJH on 2016/4/15.
 */
public class ApiVerificationCommand extends BasicApiCommand {
    private String key;                 // key

    private String secret;              // 秘钥

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public boolean verify(String key, String secret) {
        String origin = CoreStringUtils.hashCRC32(key + secret);
        String request = CoreStringUtils.hashCRC32(this.key + this.secret);
        return origin.equals(request);
    }
}
