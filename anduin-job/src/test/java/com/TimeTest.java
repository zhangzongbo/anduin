package com;

import org.omg.CORBA.INTERNAL;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zongbo.zhang on 9/7/18.
 */
public class TimeTest {

    public static void main(String[] args) throws ParseException {
        //DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        //Date end = sdf.parse("2018-09-07 19:56:05");

        //Date begin = sdf.parse("2018-09-07 19:43:25");
        //Long sec = (end.getTime() - begin.getTime()) / 1000;
        //System.out.println(sec);
        //Integer h = Math.toIntExact(sec / 3600);
        //Integer m = Math.toIntExact(sec / 60);
        //Integer s = Math.toIntExact(sec % 60);
        //System.out.println(String.format("%s hour,%s min, %s sec",h,m,s ));

        int x = 1;
        Long y = 1L;
        System.out.println(String.valueOf(x)  == String.valueOf(y));
        System.out.println(String.valueOf(x));
        System.out.println(String.valueOf(y));

        System.out.println(x != y);
        System.out.println(x == y);
    }

}
