package com.baizhi.springbootselenium.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author Zz_Wang
 * @time ---
 * @function
 */
public class DateUtil {

    private static SimpleDateFormat sdft = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * @Title: getNextDay
     * @Description: 获取前n天的日期 yyyyMMdd（-1昨天，-2前天，以此类推）
     * @return String
     */
    public static String getNextDay(int n) {

        if(n == 0) {
            return sdft.format(new Date());
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.DATE, n);
            return sdft.format(calendar.getTime());
        }
    }

    public static String getToday() {
        return getNextDay(0);
    }
}
