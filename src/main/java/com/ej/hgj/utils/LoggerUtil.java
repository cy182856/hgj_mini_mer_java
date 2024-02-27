package com.ej.hgj.utils;

/**
 * 
 * @author  xia
 * @version $Id: LoggerUtil.java, v 0.1 2020年3月31日 下午5:39:52 xia Exp $
 */
public class LoggerUtil {
    
    /**
     * 获取随机串
     * 
     * @return
     */
    public static String getRandomCode(){
        return "【UniqCode=" + new Codec().getUniqIdHash() + "】";
    }
}
