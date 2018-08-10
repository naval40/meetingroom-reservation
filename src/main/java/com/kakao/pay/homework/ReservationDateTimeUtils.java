package com.kakao.pay.homework;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import org.apache.commons.lang3.StringUtils;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ReservationDateTimeUtils {
    private static final String DATE_PARAM_FORMAT = "yyyy-MM-dd";

	public static LocalDate getLocalDateFromStr(String dateStr) {
		LocalDate date  = LocalDate.now();
		try {
			if (StringUtils.isNotEmpty(dateStr)) {
	    		date = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(DATE_PARAM_FORMAT));
			}
		} catch(DateTimeParseException e) {
			log.warn("data parse error for : " + dateStr);
		}
		return date;
	}
}