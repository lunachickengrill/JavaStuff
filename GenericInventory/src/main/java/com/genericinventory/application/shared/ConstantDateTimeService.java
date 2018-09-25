package com.genericinventory.application.shared;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class ConstantDateTimeService implements DateTimeService {

	public static final String CURRENT_DATE_AND_TIME = getConstantDateAndTime();

	private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ISO_ZONED_DATE_TIME;

	private static final Logger LOGGER = LoggerFactory.getLogger(ConstantDateTimeService.class);

	private static String getConstantDateAndTime() {
		return "2015-12-13T12:00:00" + getSystemZoneOffset() + getSystemZoneId();
	}

	private static String getSystemZoneOffset() {
		return ZonedDateTime.now().getOffset().toString();
	}

	private static String getSystemZoneId() {
		return "[" + ZoneId.systemDefault().toString() + "]";
	}

	@Override
	public ZonedDateTime getCurrentDateAndTime() {
		ZonedDateTime constantDateAndTime = ZonedDateTime.from(FORMATTER.parse(CURRENT_DATE_AND_TIME));

		LOGGER.info("Returning constant date and time: {}", constantDateAndTime);

		return constantDateAndTime;
	}

}
