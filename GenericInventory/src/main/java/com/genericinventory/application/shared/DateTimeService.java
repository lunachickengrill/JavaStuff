package com.genericinventory.application.shared;

import java.time.ZonedDateTime;

public interface DateTimeService {

	/**
	 * Returns the current date and time.
	 * 
	 * @return
	 */
	ZonedDateTime getCurrentDateAndTime();

}
