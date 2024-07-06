package com.github.kotooriiii.myworld.util;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Arrays;
import java.util.List;

public class DateUtils
{
    private static final List<DateTimeFormatter> formatters = Arrays.asList(
            DateTimeFormatter.ISO_LOCAL_DATE_TIME,
            DateTimeFormatter.ISO_LOCAL_DATE,

            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"),
            DateTimeFormatter.ofPattern("yyyy-MM-dd"),
            DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss"),
            DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm"),
            DateTimeFormatter.ofPattern("yyyy/MM/dd"),

            DateTimeFormatter.ofPattern("yyyy-M-dd HH:mm:ss"),
            DateTimeFormatter.ofPattern("yyyy-M-dd HH:mm"),
            DateTimeFormatter.ofPattern("yyyy-M-dd"),
            DateTimeFormatter.ofPattern("yyyy/M/dd HH:mm:ss"),
            DateTimeFormatter.ofPattern("yyyy/M/dd HH:mm"),
            DateTimeFormatter.ofPattern("yyyy/M/dd")
    );


    public static LocalDateTime parseToLocalDateTime(String dateString) {
        for (DateTimeFormatter formatter : formatters) {
            try {
                return LocalDateTime.parse(dateString, formatter);
            } catch (Exception e) {
                //It's possible there's no time attribute... let's try again with time attribute reset at start of day.

                try
                {
                    return LocalDate.parse(dateString, formatter).atStartOfDay();
                } catch (Exception e2)
                {
                    // Date string does not match this format, try the next one
                }

            }
        }
        // If no format matched, return null
        return null;
    }

    public static LocalDate parseToLocalDate(String dateString) {
        LocalDateTime dateTime = parseToLocalDateTime(dateString);
        if (dateTime != null) {
            return dateTime.toLocalDate();
        } else {
            return null;
        }
    }
}
