package pay;

import chess.core.util.CoreHttpUtils;
import chess.core.util.Md5Tools;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by yjh
 * Date : 2016/8/12.
 */
public class PayTest {

    public static void main(String[] area) throws UnsupportedEncodingException {
        String beforeSign = "version=1" +
                "&agent_id=1664502" +
                "&agent_bill_id=0000000056dec3140156df66b5d52585" +
                "&agent_bill_time=20160831150107" +
                "&pay_type=22" +
                "&pay_amt=10.00" +
                "&notify_url=http://w.173600.com/recharge/notify" +
                "&user_ip=27.10.150.219" +
                "&key=1390B1B43F154864A727A587";
        System.out.println("md5签名字符串：" + beforeSign);
        String sign = Md5Tools.MD5(beforeSign);
        System.out.println("md5签名后字符串：" + sign);
        String parm = "version=1" +
                "&agent_id=1664502" +
                "&agent_bill_id=abc123" +
                "&agent_bill_time=20160812113602" +
                "&pay_type=30" +
                "&pay_amt=1.12" +
                "&notify_url=http://w.173600.com/recharge/notify" +
                "&user_ip=58.17.133.6" +
                "&goods_name=" + URLEncoder.encode("虚拟测试产品", "UTF-8") +
                "&remark=iPhone6s" +
                "&return_url=w.173600.com" +
                "goods_note=" + URLEncoder.encode("虚拟测试产品0.01元", "UTF-8") +
                "remark" + URLEncoder.encode("无", "UTF-8") +
                "&sign=" + sign;
        System.out.println("发送参数：" + parm);
        String result = CoreHttpUtils.urlConnection("http://pay.heepay.com/Phone/SDK/PayInit.aspx", parm);
        System.out.println(result);
    }

}
