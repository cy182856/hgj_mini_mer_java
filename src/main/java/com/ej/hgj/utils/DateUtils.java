package com.ej.hgj.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    Logger logger = LoggerFactory.getLogger(getClass());

    static SimpleDateFormat sdfYmd = new SimpleDateFormat("yyyyMMdd");
    static SimpleDateFormat sdf_Ymd = new SimpleDateFormat("yyyy-MM-dd");
    static SimpleDateFormat sdfYmdHms = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    // 获取系统时间 年月日时分秒
    public static String strYmdHms(){
        Date date = new Date();
        String ymdHms = DateUtils.sdfYmdHms.format(date);
        return ymdHms;
    }

    public static String strYmd(){
        Date date = new Date();
        String ymd = DateUtils.sdfYmd.format(date);
        return ymd;
    }

    // 校验通行码是否超过多少小时
    public static boolean isOutTime(Date data, Integer hour){
        //24小时
        long house=60*60*hour;
        //系统当前时间,
        long currentTime = System.currentTimeMillis()/1000;
        //业务时间
        long endTime=data.getTime()/1000;
        if((endTime + house) < currentTime ){
            //超过了24小时
            return true;
        }else{
            //没有超过
            return false;
        }
    }

    // 校验日期是不是当天
    public static boolean isToday(Date date){
        //格式化为相同格式
        String createTime = DateUtils.sdf_Ymd.format(date);
        String sysTime = DateUtils.sdf_Ymd.format(new Date());
        if(createTime.equals(sysTime)){
            return true;
        }else {
            return false;
        }
    }
}
