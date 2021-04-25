package com.moon.moon_commons.util;

import java.text.FieldPosition;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

public class JacksonDateFormatter extends SimpleDateFormat {

	
	
	/**
	 * 
	 */ 
	private static final long serialVersionUID = 1L;

	@Override
	public Date parse(String source) {
		return DateUtil.parse(source);
	}
	
	@Override
	public Date parse(String dateStr, ParsePosition pos) {
		return DateUtil.parse(dateStr);
	}
	
	@Override
	public StringBuffer format(Date date, StringBuffer toAppendTo,
            FieldPosition pos) {
		return new StringBuffer(date.getTime() + "");
		
	}
}
