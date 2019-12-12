package com.example.demo.ctrl.util;

import org.slf4j.MDC;
import org.springframework.util.StringUtils;

/**
 * MDC日志工具类，用于打日志
 */
public class MDCUtil {
    public static final String MDC_F="F";
    public static final String MDC_U="U";
    public static final String MDC_T="T";
    //设置flowId
    public static void setFlowId(String value){
        if(StringUtils.isEmpty(value)){
            return;
        }
        if(!value.contains("F=")){
            value="F="+value;
        }
        setMDC("F",value);
    }

    public static void removeFlowId(){
        setMDC("F",null);
    }
    //设置事务Id
    public static void setActionId(String value){
        if(StringUtils.isEmpty(value)){
            return;
        }
        value="T="+value;
        setMDC("T",value);
    }

    public static void removeActionId(){
        setMDC("T",null);
    }
    //设置日志的一些信息
    private static void setMDC(String key,String id){
        MDC.put(key,id);
    }
    //清空MDC
    public static void clear(){
        MDC.remove("F");
        MDC.remove("T");
    }

}
