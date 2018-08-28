package com.telesoftas.edvinas.onboardingedvblk.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import timber.log.Timber;

public class DateFormatter {
    private static final String DEFAULT_API_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    private static final String DEFAULT_APP_DATE_FORMAT = "yyyy-MMMM-dd HH:mm";
    private final SimpleDateFormat defaultApiDateFormat =
            new SimpleDateFormat(DEFAULT_API_DATE_FORMAT, Locale.US);
    private final SimpleDateFormat defaultAppDateFormat =
            new SimpleDateFormat(DEFAULT_APP_DATE_FORMAT, Locale.US);

    public String changeStringDateFormat(String stringDate) {
        if (stringDate != null) {
            try {
                Date date = defaultApiDateFormat.parse(stringDate);
                return defaultAppDateFormat.format(date);
            } catch (ParseException exception) {
                Timber.e(exception);
            }
        }
        return "";
    }
}
