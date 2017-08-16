package com.sh.ilovepooq.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class TextUtils {

    public static String parseDateFromString(String dateTime) {
        if (dateTime == null || dateTime.isEmpty()) {
            return null;
        }

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.sssZZZZZ",
                    Locale.KOREA);
            Date date = dateFormat.parse(dateTime);

            dateFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.KOREA);
            return dateFormat.format(date);
        } catch (ParseException e) {
            return null;
        }
    }

}
