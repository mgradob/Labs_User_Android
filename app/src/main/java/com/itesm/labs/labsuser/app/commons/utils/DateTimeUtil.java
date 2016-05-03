package com.itesm.labs.labsuser.app.commons.utils;

import org.joda.time.DateTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by mgradob on 3/6/16.
 */
public class DateTimeUtil {

    public static final String isoFormat = "yyyy-MM-dd'T'HH:mm:ssZ";
    public static final String labsFormat = "dd/MM/yyyy @ KK:mm a";

    public static String getCurrentDateTimeUtc() {
        return new DateTime().toDateTimeISO().toString();
    }

    public static String formatDateToLocal(String dateTime) {
        if (dateTime == null) return "";

        Date date = new Date();

        try {
            date = new SimpleDateFormat(isoFormat).parse(dateTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return new SimpleDateFormat(labsFormat).format(date);
    }
}
