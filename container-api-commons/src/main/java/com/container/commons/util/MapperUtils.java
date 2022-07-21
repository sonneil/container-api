package com.container.commons.util;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class MapperUtils {
	private static final String SIMPLE_DATE_FORMAT = "yyyy-MM-dd";

	public static Boolean isEmpty(Object[] raw) {
		return raw == null || raw.length <= 0;
	}

	public static String toString(Object rawValue) {
		return Objects.nonNull(rawValue) ? rawValue.toString() : null;
	}

	public static Long toLong(Object rawValue) {
		return Objects.nonNull(rawValue) ? Long.valueOf(rawValue.toString()) : null;
	}

	public static Integer toInteger(Object rawValue) {
		return Objects.nonNull(rawValue) ? Integer.valueOf(rawValue.toString()) : null;
	}

	public static BigDecimal toBigDecimal(Object rawValue) {
		return Objects.nonNull(rawValue) ? new BigDecimal(rawValue.toString()) : null;
	}

	public static Boolean toBoolean(Object rawValue) {
		if (rawValue instanceof Number){
			return Objects.equals(toLong(rawValue), 1L);
		} else if (rawValue instanceof  Boolean) {
			return (Boolean)rawValue;
		}
		if (rawValue == null) {
			return  Boolean.FALSE;
		}
		throw new IllegalArgumentException("Cannot map to Boolean, invalid rawValue");
	}

	public static Date toDate(Object rawValue) {
		return Objects.nonNull(rawValue) ? DateUtil.stringToDate(rawValue.toString(), SIMPLE_DATE_FORMAT) : null;
	}

	public static List<Long> toLongList(List<Number> numbers) {
		return numbers.stream().map(e -> e.longValue()).collect(Collectors.toList());
	}
}
