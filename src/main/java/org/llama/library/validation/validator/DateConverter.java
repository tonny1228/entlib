package org.llama.library.validation.validator;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.beanutils.Converter;

public class DateConverter implements Converter {
	private SimpleDateFormat format = null;;

	public Object convert(Class type, Object value) {
		if (value == null) {
			return value;
		}
		if (value instanceof Date) {
			return value;
		}
		if (value instanceof String) {
			int size = value.toString().length();
			if (size == 10)
				format = new SimpleDateFormat("yyyy-MM-dd");
			else
				format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			try {
				return format.parse((String) value);
			} catch (ParseException ignore) {

			}
		}
		return null;
	}

}
