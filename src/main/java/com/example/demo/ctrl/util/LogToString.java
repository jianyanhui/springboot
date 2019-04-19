package com.example.demo.ctrl.util;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

//打日志工具类，转String
public class LogToString {
    public static String objectToString(Object o){
        String s= JSONObject.toJSONString(o, SerializerFeature.WriteMapNullValue);
        return s;
    }
}
