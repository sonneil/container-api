package com.container.sdk.utils;

import java.time.LocalDate;
import java.time.Period;
import java.time.YearMonth;
import java.time.ZoneId;
import java.util.Date;

public class DateUtils {

	public static LocalDate toLocalDate(Date birthDate) {
		return birthDate.toInstant()
				.atZone(ZoneId.systemDefault())
				.toLocalDate();
	}

	public static Integer calculateAge(LocalDate birthDate, LocalDate processingDate) {
		return Period.between(birthDate, processingDate).getYears();
	}
	
	public static Integer calculateAge(Date birthDate, LocalDate processingDate) {
		LocalDate birthLocalDate = new java.sql.Date(birthDate.getTime()).toLocalDate();
		return calculateAge(birthLocalDate, processingDate);
	}

	public static String convertToMonthYearFormat(Date date) {
		return convertToMonthYearFormat(toLocalDate(date));
	}

	public static String convertToMonthYearFormat(LocalDate date) {
		int monthNumber = date.getMonth().getValue();
		String monthString = Integer.toString(monthNumber);
		if (monthNumber < 10) monthString = "0" + monthString;
		String yearString = Integer.toString(date.getYear());
		return monthString.concat("/").concat(yearString.substring(yearString.length() - 2));
	}

	public static Date convertMonthYearToDate(String monthYearDate) {
		int monthNumber = Integer.valueOf(monthYearDate.substring(0, 2));
		int yearNumber = Integer.valueOf(20 + monthYearDate.substring(monthYearDate.length() -2));
		YearMonth ymAux = YearMonth.of(yearNumber,monthNumber);
		LocalDate date = ymAux.atEndOfMonth();
		return java.sql.Date.valueOf(date);
	}

	public static Date convertIntegersToDate(Integer year,Integer month,Integer day) {
		int y = year;
		int m = month;
		int d = day;
		LocalDate date = LocalDate.of(y, m, d);
		return java.sql.Date.valueOf(date);
	}

}
