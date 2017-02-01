package ru.javawebinar.topjava.util;

import org.springframework.format.Formatter;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class LocalDateTimeFormatter implements Formatter<LocalDateTime> {
    @Override
    public LocalDateTime parse(String text, Locale locale) throws ParseException {
        if (text == null) {
            return null;
        }
        return DateTimeUtil.parseLocalDateTime(text, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    @Override
    public String print(LocalDateTime dateTime, Locale locale) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.toString();
    }
}
