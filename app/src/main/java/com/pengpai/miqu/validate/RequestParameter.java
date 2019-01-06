package com.pengpai.miqu.validate;


import org.json.JSONObject;

/**
 * <pre>
 * @desc       : 请求参数基类
 * </pre>
 */

public abstract class RequestParameter {


    public abstract JSONObject toJson();

    //protected abstract String generateSign(String str);


}
