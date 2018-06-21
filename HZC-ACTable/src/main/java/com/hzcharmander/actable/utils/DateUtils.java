package com.hzcharmander.actable.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    //返回
    public String toYMDHMS(){
       SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
       return dateFormat.format(new Date());
    }


    public Long ymdhmsToLong(String ymdhms){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            return dateFormat.parse(ymdhms).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}
