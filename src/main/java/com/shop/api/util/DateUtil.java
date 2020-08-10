package com.shop.api.util;



import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static final String  Y_M_D="yyyy-MM-dd";

    public static final String FULL_TIME="yyyy-MM-dd HH:mm:ss";

    public static String  date2str(Date date,String pattern){

        if(date ==null){
            return  "";
        }
        SimpleDateFormat  sim=new SimpleDateFormat(pattern);
        String result =sim.format(date);

        return result;

    }

}
