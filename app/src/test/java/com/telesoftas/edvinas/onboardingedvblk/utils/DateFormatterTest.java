package com.telesoftas.edvinas.onboardingedvblk.utils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.Locale;

import static com.telesoftas.edvinas.onboardingedvblk.utils.DateFormatter.DEFAULT_APP_DATE_FORMAT;
import static com.telesoftas.edvinas.onboardingedvblk.utils.DateFormatter.DATE_FORMAT_RECEIVED;

public class DateFormatterTest {
    private static final String EMPTY_STRING = "";
    private static final String FORMATTED_DATE = "2017-October-24 03:27";
    private static final String INCORRECT_DATE_MESSAGE = "date is not formatted correctly";
    private static final String INCORRECT_DATE = "12,7.0:0.1";
    private static final String CORRECT_DATE = "2017-10-24T03:27:37Z";
    private DateFormatter dateFormatter;

    @Before
    public void setUp() throws Exception {
        SimpleDateFormat formatReceived = new SimpleDateFormat(DATE_FORMAT_RECEIVED, Locale.US);
        SimpleDateFormat formatNeeded = new SimpleDateFormat(DEFAULT_APP_DATE_FORMAT, Locale.US);
        dateFormatter = new DateFormatter(formatReceived, formatNeeded);
    }

    @Test
    public void changeStringDateFormat_inputCorrectFormat_returnsFormattedDateString()
            throws Exception {
        String actual = dateFormatter.changeStringDateFormat(CORRECT_DATE);

        Assert.assertEquals(INCORRECT_DATE_MESSAGE, FORMATTED_DATE, actual);
    }

    @Test
    public void changeStringDateFormat_inputIncorrectFormat_returnsEmptyString()
            throws Exception {
        String actual = dateFormatter.changeStringDateFormat(INCORRECT_DATE);

        Assert.assertEquals(INCORRECT_DATE_MESSAGE, EMPTY_STRING, actual);
    }

    @Test
    public void changeStringDateFormat_inputIsNull_returnsEmptyString()
            throws Exception {
        String actual = dateFormatter.changeStringDateFormat(null);

        Assert.assertEquals(INCORRECT_DATE_MESSAGE, EMPTY_STRING, actual);
    }
}