package chess.core.api.command;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import chess.core.common.CharsetConstant;
import chess.core.util.CoreStringUtils;

import java.io.Serializable;

/**
 * Created by YJH on 2016/4/15.
 */
public class BasicApiCommand implements Serializable {

    protected static Logger log = LoggerFactory.getLogger(BasicApiCommand.class.getName());

    private String json;

    public <T> T convertJsonTo(Class<T> clazz) {
        return internalConvertJsonTo(json, clazz);
    }

    public void setJson(String json) {
        this.json = json;
    }

    private static <T> T internalConvertJsonTo(String json, Class<T> clazz) {
        T t = null;
        try {
            t = JSON.parseObject(CoreStringUtils.urlDecode(json, CharsetConstant.DEFAULT_STRING), clazz);
        } catch (JSONException e) {
            log.error("转换json异常.", e);
        }
        return t;
    }

}
