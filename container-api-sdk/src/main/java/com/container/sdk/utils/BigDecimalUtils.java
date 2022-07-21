package com.container.sdk.utils;

import java.math.BigDecimal;

public class BigDecimalUtils {
	public static Boolean equals(BigDecimal a, BigDecimal b) {
		if (a == null && b == null)
			return Boolean.TRUE;
		if (a == null || b == null)
			return Boolean.FALSE;

		return a.compareTo(b) == 0;
	}
	
	public static Boolean emptyValue(BigDecimal a) {
		return a == null || BigDecimalUtils.equals(BigDecimal.ZERO, a);
	}
}

