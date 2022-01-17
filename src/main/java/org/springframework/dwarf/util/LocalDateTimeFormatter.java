package org.springframework.dwarf.util;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Locale;

import org.springframework.format.Formatter;
import org.springframework.stereotype.Component;

@Component
public class LocalDateTimeFormatter implements Formatter<LocalDateTime>{

	@Override
	public String print(LocalDateTime object, Locale locale) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public LocalDateTime parse(String text, Locale locale) throws ParseException {
		// TODO Auto-generated method stub
		return null;
	}

}
