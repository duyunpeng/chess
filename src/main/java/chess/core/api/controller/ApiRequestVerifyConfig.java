package chess.core.api.controller;

/**
 * Created by YJH on 2016/4/15.
 */
public class ApiRequestVerifyConfig {

    private String key;

    private String secret;

    public ApiRequestVerifyConfig() {
    }

    public ApiRequestVerifyConfig(String key, String secret) {
        this.key = key;
        this.secret = secret;
    }

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

}
