package org.llama.library.validation.validator;

import org.apache.commons.beanutils.Converter;
import org.llama.library.validation.validator.RangeBoundaryType;


public class RangeBoundaryTypeConverter implements Converter {

	public Object convert(Class type, Object value) {
		return RangeBoundaryType.parse(value.toString());
	}

}
