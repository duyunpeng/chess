package chess.core.api;

import com.alibaba.fastjson.serializer.SerializerFeature;

/**
 * Created by YJH on 2016/4/15.
 */
public class ApiConstant {

    // JSON序列化特性配置
    public static final SerializerFeature[] JSON_FEATURES = {SerializerFeature.WriteClassName,
            SerializerFeature.NotWriteRootClassName};

    // 发送API时的参数名称
    public static final String API_REQUEST_PARAMETER_NAME = "json";

    public static final int API_REQUEST_TIME_OUT_DEFAULT = 10000;           // 默认的超时时间
}
