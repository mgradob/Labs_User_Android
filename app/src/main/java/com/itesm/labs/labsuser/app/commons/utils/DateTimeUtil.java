package com.itesm.labs.labsuser.app.commons.utils;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

/**
 * Created by mgradob on 3/6/16.
 */
public class DateTimeUtil {

    public static String getCurrentDateTimeUtc() {
        return new DateTime(DateTimeZone.UTC).toString();
    }
}
