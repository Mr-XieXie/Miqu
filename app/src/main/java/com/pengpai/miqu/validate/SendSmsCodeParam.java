package com.pengpai.miqu.validate;

import com.pengpai.miqu.base.utils.SignStrToMd5;

import org.json.JSONObject;

/**

 */

public class SendSmsCodeParam extends RequestParameter{

    private static final String TAG = "SendSmsCodeParam";

    public String msgId= Request.generateNonce32(); //	使用UUID标识请求的唯一性	是	String
    public String systemTime= Request.getCurrentTime(); //请求消息发送的系统时间，北京时间，东八区时间。精确到毫秒，共17位，格式：20121227180001165  String
    public String version="1.0"; //	版本号,初始版本号1.0,有升级后续调整	是	String
    public String requesterType="0"; //	合作伙伴集成类型0：APP；1：WAP	是	String
    public String appId; //	应用id	是	String
    public String mobileNumber; //	加密手机号码，AES加密，秘钥为appkey	是	String
    public String userIp; //	客户端IP 	是	String
    public String message; //	接入方预留参数，该参数会透传给通知接口，此参数需urlencode编码	否	String
    public String accessToken; //	临时凭证 要求：失效时间默认为5分钟，可配置  否	 String
    public String expandParams; //	扩展参数  否	 String
    public String sign; //	签名，MD5（msgId + systemTime + version + requestertype+ appId + mobilenumber+ userIp +appkey），输出32位小写字母	是	String

    public String generateSign(String appkey) {
        StringBuilder str = new StringBuilder();
        str.append(appId).append(appkey).append(mobileNumber).append(msgId)
                .append(requesterType).append(systemTime) .append(userIp)
                .append(version);

        String signStr=SignStrToMd5.getMD5Str32(str.toString());
        //LogUtils.e(TAG,"generateSign="+signStr);
        return signStr;
    }

    @Override
    public JSONObject toJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("msgId", msgId);
            jsonObject.put("systemTime", systemTime);
            jsonObject.put("version", version);
            jsonObject.put("requesterType", requesterType);
            jsonObject.put("appId", appId);
            jsonObject.put("mobileNumber", mobileNumber);
            jsonObject.put("userIp", userIp);
            jsonObject.put("message", message);
            jsonObject.put("accessToken", accessToken);
            jsonObject.put("expandParams", expandParams);
            jsonObject.put("sign", sign);

        } catch (Throwable e) {
                //LogUtils.d(TAG, e.getMessage());
        }
        return jsonObject;
    }




}
