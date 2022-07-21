package com.container.commons.util;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;

public class RawIteratorMapper implements Iterator<Object> {
	private final Iterator<Object> rawIterator;

	public RawIteratorMapper(Object[] raw) {
		rawIterator = Arrays.asList(MapperUtils.isEmpty(raw) ? new Object[0] : raw).iterator();
	}

	@Override
	public boolean hasNext() {
		return rawIterator.hasNext();
	}

	@Override
	public Object next() {
		return rawIterator.next();
	}

	public String nextString() {
		return MapperUtils.toString(rawIterator.next());
	}

	public Long nextLong() {
		return MapperUtils.toLong(rawIterator.next());
	}

	public Integer nextInteger() {
		return MapperUtils.toInteger(rawIterator.next());
	}

	public BigDecimal nextBigDecimal() {
		return MapperUtils.toBigDecimal(rawIterator.next());
	}

	public boolean nextBoolean() {
		return MapperUtils.toBoolean(rawIterator.next());
	}

	public Date nextDate() {
		return MapperUtils.toDate(rawIterator.next());
	}
}
