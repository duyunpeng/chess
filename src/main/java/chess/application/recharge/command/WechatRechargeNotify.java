package chess.application.recharge.command;

import java.math.BigDecimal;

/**
 * Author pengyi
 * Date 16-8-9.
 */
public class WechatRechargeNotify {

    private int result;                         //支付结果：0=正在处理，1=成功，-1=失败
    private String pay_message;                 //支付结果信息，支付成功时为空
    private String agent_id;                    //商户编号 如1234567
    private String jnet_bill_no;                //汇付宝交易号(订单号)
    private String agent_bill_id;               //商户系统内部的定单号
    private String pay_type;                    //支付类型
    private BigDecimal pay_amt;                 //订单实际支付金额(注意：此金额是用户的实付金额)
    private String remark;                      //说明
    private String sign;                        //MD5签名结果
    //sign=md5(result=1&agent_id=1001&jnet_bill_no=B20100225132210&agent_bill_id=20100225132210&pay_type=10&pay_amt=15.33&remark=test_remark&key=CC08C5E3E69F4E6B85F1DC0B)

    public String signStr() {
        return "result=" + result +
                "&agent_id=" + agent_id +
                "&jnet_bill_no=" + jnet_bill_no +
                "&agent_bill_id=" + agent_bill_id +
                "&pay_type=" + pay_type +
                "&pay_amt=" + pay_amt +
                "&remark=" + remark +
                "&key=DA6EABB2DA054B399C3B8AD0";
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getPay_message() {
        return pay_message;
    }

    public void setPay_message(String pay_message) {
        this.pay_message = pay_message;
    }

    public String getAgent_id() {
        return agent_id;
    }

    public void setAgent_id(String agent_id) {
        this.agent_id = agent_id;
    }

    public String getJnet_bill_no() {
        return jnet_bill_no;
    }

    public void setJnet_bill_no(String jnet_bill_no) {
        this.jnet_bill_no = jnet_bill_no;
    }

    public String getAgent_bill_id() {
        return agent_bill_id;
    }

    public void setAgent_bill_id(String agent_bill_id) {
        this.agent_bill_id = agent_bill_id;
    }

    public String getPay_type() {
        return pay_type;
    }

    public void setPay_type(String pay_type) {
        this.pay_type = pay_type;
    }

    public BigDecimal getPay_amt() {
        return pay_amt;
    }

    public void setPay_amt(BigDecimal pay_amt) {
        this.pay_amt = pay_amt;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
