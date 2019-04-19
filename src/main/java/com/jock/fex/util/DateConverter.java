package com.jock.fex.util;

import org.springframework.core.convert.converter.Converter;
import org.springframework.util.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConverter implements Converter<String, Date> {

	private static final DateFormat DATEFORMAT = new SimpleDateFormat("yyyy-MM-dd");
	private static final DateFormat TIMEFORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public Date convert(String source) {
		if (!StringUtils.hasText(source)) {
			return null;
		} else {
			try {
				if (source.contains(":")) {
					DATEFORMAT.setLenient(false);
					return TIMEFORMAT.parse(source);
				} else {
					TIMEFORMAT.setLenient(false);
					return DATEFORMAT.parse(source);
				}
			} catch (ParseException ex) {
				throw new IllegalArgumentException("Could not parse date: " + ex.getMessage(), ex);
			}
		}
	}
}