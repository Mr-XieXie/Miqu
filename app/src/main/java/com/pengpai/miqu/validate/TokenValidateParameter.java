package com.pengpai.miqu.validate;


import android.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by haoxin on 2017/4/24.
 */

public class TokenValidateParameter {


    /**
     * header : {"strictcheck":"0","version":"1.0","msgid":"40a940a940a940a93b8d3b8d3b8d3b8d","systemtime":"20170515090923489","appid":"10000001","apptype":"5","sourceid":"sourceid","ssotosourceid":"ssotosourceid","expandparams":"expandparams"}
     * body : {"token":"8484010001320200344E6A5A4551554D784F444E474E446C434E446779517A673340687474703A2F2F3139322E3136382E31322E3233363A393039302F0300040353EA68040006313030303030FF00203A020A143C6703D7D0530953C760744C7D61F5F7B546F12BC17D65254878748C"}
     */

    private HeaderBean header;
    private BodyBean body;
    private String appid;
    private String timestamp;

    public static String hamcSha1(String data, String key) {
        try {
            SecretKeySpec signingKey = null;
            signingKey = new SecretKeySpec(key.getBytes("UTF-8"), "HmacSHA1");
            Mac mac = Mac.getInstance(signingKey.getAlgorithm());
            mac.init(signingKey);
            byte[] digest = new byte[0];
            digest = mac.doFinal(data.getBytes("UTF-8"));
            return Base64.encodeToString(digest, Base64.NO_WRAP);
//            return Base64.encodeBase64String(digest);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }

    public HeaderBean getHeader() {
        return header;
    }

    public void setHeader(HeaderBean header) {
        this.header = header;
    }

    public BodyBean getBody() {
        return body;
    }

    public void setBody(BodyBean body) {
        this.body = body;
    }


    public static class HeaderBean {
        /**
         * strictcheck : 0
         * version : 1.0
         * msgid : 40a940a940a940a93b8d3b8d3b8d3b8d
         * systemtime : 20170515090923489
         * appid : 10000001
         * apptype : 5
         * sourceid : sourceid
         * ssotosourceid : ssotosourceid
         * expandparams : expandparams
         */

        private String strictcheck;//strictcheck	必选	2	string	验证源ip合法性，填写”1”，统一认证会校验sourceid与出口ip对应关系（申请sourceid时需提供业务出口ip，可以多个IP）
        private String version; //version	必选	2	string	填1.0
        private String msgid; //msgid	必选	2	string	标识请求的随机数即可(1-36位)
        private String systemtime;//systemtime	必选	2	string	请求消息发送的系统时间，精确到毫秒，共17位，格式：20121227180001165
        private String appid;//appid	必选	2	string	业务在统一认证申请的应用id
        private String apptype;//apptype	必选	2	string	参见附录“渠道编码定义”1|BOSS 2|web 3|wap 4|pc客户端 5|手机客户端
        private String sourceid;//sourceid	可选	2	string	业务集成统一认证的标识，需提前申请，申请指南见附录一
        private String ssotosourceid;//ssotosourceid	可选	2	string	单点登录时使用，填写被登录业务的sourceid
        private String expandparams;//expandparams	扩展参数	2	Map	map(key,value)
//        private String expandparams= "13802885114";

        private String sign;//token	必选	2	string	需要解析的凭证值。

        public String getSign() {
            return sign;
        }

        public void setSign(String sign) {
            this.sign = sign;
        }
        public String getStrictcheck() {
            return strictcheck;
        }

        public void setStrictcheck(String strictcheck) {
            this.strictcheck = strictcheck;
        }

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public String getMsgid() {
            return msgid;
        }

        public void setMsgid(String msgid) {
            this.msgid = msgid;
        }

        public String getSystemtime() {
            return systemtime;
        }

        public void setSystemtime(String systemtime) {
            this.systemtime = systemtime;
        }

        public String getAppid() {
            return appid;
        }

        public void setAppid(String appid) {
            this.appid = appid;
        }

        public String getApptype() {
            return apptype;
        }

        public void setApptype(String apptype) {
            this.apptype = apptype;
        }

        public String getSourceid() {
            return sourceid;
        }

        public void setSourceid(String sourceid) {
            this.sourceid = sourceid;
        }

        public String getSsotosourceid() {
            return ssotosourceid;
        }

        public void setSsotosourceid(String ssotosourceid) {
            this.ssotosourceid = ssotosourceid;
        }

        public String getExpandparams() {
            return expandparams;
        }

        public void setExpandparams(String expandparams) {
            this.expandparams = expandparams;
        }
    }

    public static class BodyBean {
        /**
         * token : 8484010001320200344E6A5A4551554D784F444E474E446C434E446779517A673340687474703A2F2F3139322E3136382E31322E3233363A393039302F0300040353EA68040006313030303030FF00203A020A143C6703D7D0530953C760744C7D61F5F7B546F12BC17D65254878748C
         */

        private String token;//token	必选	2	string	需要解析的凭证值。


        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }

    public String getAppid() {
        return appid;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public JSONObject toJson() {
        JSONObject mainJson = new JSONObject();
        JSONObject headerJson = new JSONObject();
        JSONObject bodyJson = new JSONObject();
        try {
            headerJson.put("strictcheck",header.getStrictcheck());
            headerJson.put("version",header.getVersion());
            headerJson.put("msgid",header.getMsgid());
            headerJson.put("systemtime",header.getSystemtime());
            headerJson.put("appid",header.getAppid());
            headerJson.put("apptype",header.getApptype());
            headerJson.put("sourceid","800120170818101447");
            headerJson.put("ssotosourceid",header.getSsotosourceid());
            headerJson.put("expandparams",header.getExpandparams());
            mainJson.put("header",headerJson);
            bodyJson.put("token",body.getToken());
            headerJson.put("sign",header.getSign());

            mainJson.put("body",bodyJson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return mainJson;
    }
}
