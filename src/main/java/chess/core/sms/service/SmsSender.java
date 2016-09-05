package chess.core.sms.service;

import chess.core.sms.obj.SmsTemplate;
import com.taobao.api.ApiException;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Service;

/**
 * 短信发送
 * Created by pengyi on 2016/3/7.
 */
@Service("smsSender")
public class SmsSender {

    @Autowired
    private TaskExecutor taskExecutor;
    @Autowired
    private TaobaoClient taobaoClient;

    /**
     * 构建邮件内容，发送短信。
     *
     * @param phone    手机号
     * @param code     验证码
     * @param template 模板
     */
    public void send(final String phone, final String code, final SmsTemplate template) {

        this.taskExecutor.execute(() -> {
            AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
            req.setSmsType("normal");
            req.setSmsFreeSignName("信用榜");
//            req.setSmsParamString("{\"code\":\"" + code + "\",\"product\":\"信用榜\"}");
            req.setRecNum(phone);
            req.setSmsTemplateCode(template.getKey());
            AlibabaAliqinFcSmsNumSendResponse rsp = null;
            try {
                rsp = taobaoClient.execute(req);
            } catch (ApiException e) {
                e.printStackTrace();
            }
        });

    }
}
