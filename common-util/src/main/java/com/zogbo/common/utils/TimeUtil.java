package com.zogbo.common.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zongbo.zhang on 8/22/18.
 */
public class TimeUtil {

    public static String date2Str(Date date){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    public static String mill2Time(long mill){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss SSS");
        return sdf.format(mill);
    }

    public static void main(String[] args) {
        System.out.println(date2Str(new Date()));

        System.out.println(mill2Time(System.currentTimeMillis()));
    }

}
