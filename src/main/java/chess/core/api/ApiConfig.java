package chess.core.api;

import java.util.Map;

/**
 * Created by YJH on 2016/4/22.
 */
public class ApiConfig {

    private String basicUrl;                             // api调用地址

    private Map<String, String> parameterMap;            // 默认参数 比如秘钥

    public String getBasicUrl() {
        return basicUrl;
    }

    public void setBasicUrl(String basicUrl) {
        this.basicUrl = basicUrl;
    }

    public Map<String, String> getParameterMap() {
        return parameterMap;
    }

    public void setParameterMap(Map<String, String> parameterMap) {
        this.parameterMap = parameterMap;
    }

}
