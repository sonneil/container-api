package com.container.commons.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public final class DateUtil {
	public static final String HTTP_HEADER_DATE_FORMAT = "EEE, d MMM yyyy HH:mm:ss Z";
	public static final String GMTLOCAL = "GMT-3";

	public static int getElapsedDays(Date date) {
		return elapsed(date, 5);
	}

	public static int getElapsedDays(Date d1, Date d2) {
		return elapsed((Date) d1, (Date) d2, 5);
	}

	public static long getElapsedMinutes(Date d1, Date d2) {
		return (d2.getTime() - d1.getTime()) / 60000L;
	}

	public static int getElapsedMonths(Date date) {
		return elapsed(date, 2);
	}

	public static int getElapsedMonths(Date d1, Date d2) {
		return elapsed((Date) d1, (Date) d2, 2);
	}

	public static int getElapsedYears(Date date) {
		return elapsed(date, 1);
	}

	public static int getElapsedYears(Date d1, Date d2) {
		return elapsed((Date) d1, (Date) d2, 1);
	}

	private static int elapsed(GregorianCalendar g1, GregorianCalendar g2, int type) {
		int elapsed = 0;
		GregorianCalendar gc1;
		GregorianCalendar gc2;
		if (g2.after(g1)) {
			gc2 = (GregorianCalendar) g2.clone();
			gc1 = (GregorianCalendar) g1.clone();
		} else {
			gc2 = (GregorianCalendar) g1.clone();
			gc1 = (GregorianCalendar) g2.clone();
		}

		if (type == 2 || type == 1) {
			gc1.clear(5);
			gc2.clear(5);
		}

		if (type == 1) {
			gc1.clear(2);
			gc2.clear(2);
		}

		while (gc1.before(gc2)) {
			gc1.add(type, 1);
			++elapsed;
		}

		return elapsed;
	}

	private static int elapsed(Date date, int type) {
		return elapsed(date, new Date(), type);
	}

	private static int elapsed(Date d1, Date d2, int type) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(d1);
		GregorianCalendar g1 = new GregorianCalendar(cal.get(1), cal.get(2), cal.get(5));
		cal.setTime(d2);
		GregorianCalendar g2 = new GregorianCalendar(cal.get(1), cal.get(2), cal.get(5));
		return elapsed(g1, g2, type);
	}

	public static Date stringToDate(String strDate, String dateFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);

		try {
			return sdf.parse(strDate);
		} catch (ParseException var4) {
			return null;
		}
	}

	public static Date stringToDate(String strDate, String dateFormat, Locale locale) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, locale);

		try {
			return sdf.parse(strDate);
		} catch (ParseException var5) {
			return null;
		}
	}

	public static int elapsed(Calendar before, Calendar after, int field) {
		Calendar clone = (Calendar) before.clone();

		int elapsed;
		for (elapsed = -1; !clone.after(after); ++elapsed) {
			clone.add(field, 1);
		}

		return elapsed;
	}

	public static boolean dateBetweenMinMax(Date date, Date min, Date max) {
		return date.after(min) && date.before(max);
	}

	public static int daysBetween(Date startDate, Date endDate) {
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(startDate);
		c2.setTime(endDate);
		return daysBetween(c1, c2);
	}

	public static int daysBetween(Calendar early, Calendar late) {
		return (int) (toJulian(late) - toJulian(early));
	}

	public static int mounthsBetween(Date startDate, Date endDate) {
		Calendar startCalendar = Calendar.getInstance();
		Calendar endCalendar = Calendar.getInstance();
		startCalendar.setTime(startDate);
		endCalendar.setTime(endDate);
		int startMes = startCalendar.get(1) * 12 + startCalendar.get(2);
		int endMes = endCalendar.get(1) * 12 + endCalendar.get(2);
		return endMes - startMes;
	}

	public static final float toJulian(Calendar c) {
		int Y = c.get(1);
		int M = c.get(2);
		int D = c.get(5);
		int A = Y / 100;
		int B = A / 4;
		int C = 2 - A + B;
		float E = (float) ((int) (365.25F * (float) (Y + 4716)));
		float F = (float) ((int) (30.6001F * (float) (M + 1)));
		float JD = (float) (C + D) + E + F - 1524.5F;
		return JD;
	}

	public static Date getLastMonthDay() {
		Calendar calendar = Calendar.getInstance();
		int lastDate = calendar.getActualMaximum(5);
		calendar.set(5, lastDate);
		return calendar.getTime();
	}

	public static Date getFirstMonthDay() {
		Calendar calendar = Calendar.getInstance();
		int minDate = calendar.getActualMinimum(5);
		calendar.set(5, minDate);
		return calendar.getTime();
	}

	public static Date getLastMonthDay(Calendar cal) {
		int lastDate = cal.getActualMaximum(5);
		cal.set(5, lastDate);
		return cal.getTime();
	}

	public static Date getFirstMonthDay(Calendar cal) {
		int minDate = cal.getActualMinimum(5);
		cal.set(5, minDate);
		return cal.getTime();
	}

	public static Date decrementMonths(Date dateToDecrement, int numberOfMonths) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateToDecrement);
		calendar.add(2, numberOfMonths * -1);
		return calendar.getTime();
	}

	public static Date incrementMonths(Date dateToIncrement, int numberOfMonths) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateToIncrement);
		calendar.add(2, numberOfMonths);
		return calendar.getTime();
	}

	public static Date decrementDays(Date dateToDecrement, int numberOfDays) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateToDecrement);
		calendar.add(5, numberOfDays * -1);
		return calendar.getTime();
	}

	public static Date incrementDays(Date dateToIncrement, int numberOfDays) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateToIncrement);
		calendar.add(5, numberOfDays);
		return calendar.getTime();
	}

	public static Date incrementCalendarField(Date dateToIncrement, int number, int calendarField) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateToIncrement);
		calendar.add(calendarField, number);
		return calendar.getTime();
	}

	public static Date decrementYears(Date dateToDecrement, int numberOfYears) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateToDecrement);
		calendar.add(1, numberOfYears * -1);
		return calendar.getTime();
	}

	public static Date decrementYearsToday(int numberOfYears) {
		return decrementYears(new Date(), numberOfYears);
	}

	public static Date incrementDaysToday(int numberOfDays) {
		return incrementDays(new Date(), numberOfDays);
	}

	public static Date decrementDaysToday(int numberOfDays) {
		return decrementDays(new Date(), numberOfDays);
	}

	public static Date incrementMonthsToday(int numberOfMonths) {
		return incrementMonths(new Date(), numberOfMonths);
	}

	public static Date decrementMonthsToday(int numberOfMonths) {
		return decrementMonths(new Date(), numberOfMonths);
	}

	public static Date incrementHoursNow(int hours) {
		return incrementHours(new Date(), hours);
	}

	public static Date incrementMinutesNow(int minutes) {
		return incrementMinutes(new Date(), minutes);
	}

	public static Date incrementHours(Date date, int hours) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(10, hours);
		return calendar.getTime();
	}

	public static Date decrementHours(Date date, int hours) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(10, hours * -1);
		return calendar.getTime();
	}

	public static Date incrementMinutes(Date date, int minutes) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(12, minutes);
		return calendar.getTime();
	}

	public static Date yesterday() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(5, -1);
		return calendar.getTime();
	}

	public static Date tomorrow() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(5, 1);
		return calendar.getTime();
	}

	public static Date now() {
		return new Date();
	}

	public static String dateToString(Date date, String dateFormat) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
		return sdf.format(date);
	}

	public static String dateToString(Date date, String dateFormat, Locale locale) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, locale);
		return sdf.format(date);
	}

	public static Date dateFromFields(String year, String month, String day) {
		return dateFromFields(Integer.valueOf(year), Integer.valueOf(month), Integer.valueOf(day));
	}

	public static Date dateFromFields(int year, int month, int day) {
		return stringToDate(year + "-" + month + "-" + day, "yyyy-MM-dd");
	}

	public static Date getMidNight(Calendar date) {
		date.set(11, 0);
		date.set(12, 0);
		date.set(13, 0);
		date.set(14, 0);
		return date.getTime();
	}

	public static int getYear(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(1);
	}

	public static int getMonth(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(2) + 1;
	}

	public static int getDay(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(5);
	}

	public static int getLastDayOfMonths(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.getActualMaximum(5);
	}

	public static int getHour(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(11);
	}

	public static int getMinute(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(12);
	}

	public static int getSecond(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(13);
	}

	public static int getAge(Date birthday) {
		Calendar dob = Calendar.getInstance();
		dob.setTime(birthday);
		Calendar today = Calendar.getInstance();
		int age = today.get(1) - dob.get(1);
		if (today.get(2) < dob.get(2)) {
			--age;
		} else if (today.get(2) == dob.get(2) && today.get(5) < dob.get(5)) {
			--age;
		}

		return age;
	}

	public static boolean isLeapYear(int year) {
		return year % 400 == 0 || year % 4 == 0 && year % 100 != 0;
	}

	public static SimpleDateFormat getSimpleDateFormatLocal(String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		sdf.setTimeZone(TimeZone.getTimeZone("GMT-3"));
		return sdf;
	}

	public static String getMonthName(int month) {
		String name = "";
		switch (month) {
			case 1 :
				name = "Enero";
				break;
			case 2 :
				name = "Febrero";
				break;
			case 3 :
				name = "Marzo";
				break;
			case 4 :
				name = "Abril";
				break;
			case 5 :
				name = "Mayo";
				break;
			case 6 :
				name = "Junio";
				break;
			case 7 :
				name = "Julio";
				break;
			case 8 :
				name = "Agosto";
				break;
			case 9 :
				name = "Septiembre";
				break;
			case 10 :
				name = "Octubre";
				break;
			case 11 :
				name = "Noviembre";
				break;
			case 12 :
				name = "Diciembre";
		}

		return name;
	}

	public static Date removeTimeFrom(Date date) {
		try {
			Calendar c = Calendar.getInstance();
			c.setTime(date);
			c.set(11, 0);
			c.set(12, 0);
			c.set(13, 0);
			c.set(14, 0);
			return c.getTime();
		} catch (Exception var2) {
			return date;
		}
	}

	public static int calculateAge(Calendar bornDate, Calendar compare) {
		if (bornDate != null && compare != null) {
			int year = compare.get(1) - bornDate.get(1);
			int mes = compare.get(2) - bornDate.get(2);
			int dia = compare.get(5) - bornDate.get(5);
			if (year <= 0) {
				return 0;
			} else {
				if (mes < 0 || mes == 0 && dia < 0) {
					--year;
				}

				return year;
			}
		} else {
			return 0;
		}
	}

	public static Boolean equalsMonthAndYear(Date date1, Date date2) {
		Calendar cal1 = Calendar.getInstance();
		Calendar cal2 = Calendar.getInstance();
		cal1.setTime(date1);
		cal2.setTime(date2);
		return cal1.get(1) == cal2.get(1) && cal1.get(2) == cal2.get(2);
	}

	public static Date addMonthsMapfre(Date date, int monthAmount) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(2, monthAmount);
		if (isLastDayOfMonth(date)) {
			calendar.set(5, calendar.getActualMaximum(5));
		}

		return calendar.getTime();
	}

	public static Boolean isLastDayOfMonth(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.getActualMaximum(5) == calendar.get(5);
	}

	public static Date retrieveFirstDayOfMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(5, c.getActualMinimum(5));
		return c.getTime();
	}

	public static Date retrieveLastDayOfMonth(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.set(5, c.getActualMaximum(5));
		return c.getTime();
	}

	public static Date retrieveLastDayOfMonthPrevious(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int previousDay = 1;
		int previousMonth = calendar.get(2) - 1;
		int previousYear = calendar.get(1);
		if (calendar.get(2) == 0) {
			previousMonth = 11;
			previousYear = calendar.get(1) - 1;
		}

		calendar.setTime(new Date());
		calendar.set(5, previousDay);
		calendar.set(2, previousMonth);
		calendar.set(1, previousYear);
		return retrieveLastDayOfMonth(calendar.getTime());
	}

	public static Boolean isValid(String dateToValidate, String dateFormat) {
		if (dateToValidate == null) {
			return false;
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			sdf.setLenient(false);

			try {
				Date date = sdf.parse(dateToValidate);
				System.out.println(date);
			} catch (ParseException var4) {
				return false;
			}

			return true;
		}
	}

	public static int getDaysOfYearByYear(int year) {
		int days = 365;
		if (isLeapYear(year)) {
			days = 366;
		}

		return days;
	}

	public static int getDaysOfYearByDate(Date date) {
		int year = getYear(date);
		return getDaysOfYearByYear(year);
	}

	public static Date dateFrom(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(11, 0);
		calendar.set(12, 0);
		calendar.set(13, 0);
		return calendar.getTime();
	}

	public static Date dateTo(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(11, 23);
		calendar.set(12, 59);
		calendar.set(13, 59);
		calendar.set(14, 999);
		return calendar.getTime();
	}
}