//Jayden Wyatt
//CS 2463
//Description: This is the OCCCDate wrapper class around the GregorianCalender class. 

import java.util.GregorianCalendar;
import java.util.Calendar;
import java.io.Serializable;

public class OCCCDate implements Comparable<OCCCDate>, Serializable {
	private static final long serialVersionUID = 1L;
    // Instance variables
    private int dayOfMonth;      // 1..31
    private int monthOfYear;     // 1..12
    private int year;            // e.g. 2020
    private GregorianCalendar gc;

    private boolean dateFormat;  // default FORMAT_US
    private boolean dateStyle;   // default STYLE_NUMBERS
    private boolean dateDayName; // default SHOW_DAY_NAME

    // Constants
    public static final boolean FORMAT_US = true;
    public static final boolean FORMAT_EURO = false;

    public static final boolean STYLE_NUMBERS = true;
    public static final boolean STYLE_NAMES = false;

    public static final boolean SHOW_DAY_NAME = true;
    public static final boolean HIDE_DAY_NAME = false;

    // Default constructor (current date)
    public OCCCDate() {
		gc = new GregorianCalendar();
        dayOfMonth = gc.get(Calendar.DAY_OF_MONTH);
        monthOfYear = gc.get(Calendar.MONTH) + 1;
        year = gc.get(Calendar.YEAR);

        dateFormat = FORMAT_US;
        dateStyle = STYLE_NUMBERS;
        dateDayName = SHOW_DAY_NAME;
    }

    // Constructor with day, month, year
    public OCCCDate(int day, int month, int year) {
		//New throw exception
		if (!isValidDate(day, month, year)){
			throw new InvalidOCCCDateException("Invalid date: " + month + "/" + day + "/" + year);
		}
		
		gc = new GregorianCalendar();
		gc.setLenient(false);

		gc.set(Calendar.YEAR, year);
		gc.set(Calendar.MONTH, month - 1);
		gc.set(Calendar.DAY_OF_MONTH, day);
		
		//force normalization of the invalid dates
		gc.getTime();
		
		// pull corrected values from GregorianCalendar
		this.dayOfMonth = gc.get(Calendar.DAY_OF_MONTH);
		this.monthOfYear = gc.get(Calendar.MONTH) + 1;
		this.year = gc.get(Calendar.YEAR);

		dateFormat = FORMAT_US;
		dateStyle = STYLE_NUMBERS;
		dateDayName = SHOW_DAY_NAME;
	}

    // Constructor with GregorianCalendar
    public OCCCDate(GregorianCalendar gc) {
        this.gc = gc;
        dayOfMonth = gc.get(Calendar.DAY_OF_MONTH);
        monthOfYear = gc.get(Calendar.MONTH) + 1;
        year = gc.get(Calendar.YEAR);

        dateFormat = FORMAT_US;
        dateStyle = STYLE_NUMBERS;
        dateDayName = SHOW_DAY_NAME;
    }

    // Copy constructor
    public OCCCDate(OCCCDate d) {
        this.dayOfMonth = d.dayOfMonth;
        this.monthOfYear = d.monthOfYear;
        this.year = d.year;
        this.gc = new GregorianCalendar(year, monthOfYear - 1, dayOfMonth);

        this.dateFormat = d.dateFormat;
        this.dateStyle = d.dateStyle;
        this.dateDayName = d.dateDayName;
    }

    public int getDayofMonth() {
        return dayOfMonth;
    }

    public String getDayName() {
        return gc.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, java.util.Locale.US);
    }

    public int getMonthNumber() {
        return monthOfYear;
    }

    public String getMonthName() {
        return gc.getDisplayName(Calendar.MONTH, Calendar.LONG, java.util.Locale.US);
    }

    public int getYear() {
        return year;
    }

    public void setDateFormat(boolean df) {
        dateFormat = df;
    }

    public void setStyleFormat(boolean sf) {
        dateStyle = sf;
    }

    public void setDayName(boolean nf) {
        dateDayName = nf;
    }

    public int getDifferenceInYears() {
        GregorianCalendar now = new GregorianCalendar();
        return now.get(Calendar.YEAR) - year;
    }

    public int getDifferenceInYears(OCCCDate d) {
        return Math.abs(this.year - d.year);
    }

    public boolean equals(OCCCDate dob) {
        return this.dayOfMonth == dob.dayOfMonth &&
               this.monthOfYear == dob.monthOfYear &&
               this.year == dob.year;
    }
	
	public boolean isValidDate(int day, int month, int year){
		GregorianCalendar temp = new GregorianCalendar();
		temp.setLenient(true);
		
		temp.set(Calendar.YEAR, year);
		temp.set(Calendar.MONTH, month - 1);
		temp.set(Calendar.DAY_OF_MONTH, day);

		temp.getTime(); // force normalization

		int newDay = temp.get(Calendar.DAY_OF_MONTH);
		int newMonth = temp.get(Calendar.MONTH) + 1;
		int newYear = temp.get(Calendar.YEAR);

		return (day == newDay && month == newMonth && year == newYear);
	}

    @Override
    public String toString() {
        String dateStr;

        if (dateStyle == STYLE_NUMBERS) {
            if (dateFormat == FORMAT_US)
                dateStr = monthOfYear + "/" + dayOfMonth + "/" + year;
            else
                dateStr = dayOfMonth + "/" + monthOfYear + "/" + year;
        } else {
            if (dateFormat == FORMAT_US)
                dateStr = getMonthName() + " " + dayOfMonth + ", " + year;
            else
                dateStr = dayOfMonth + " " + getMonthName() + " " + year;
        }

        if (dateDayName == SHOW_DAY_NAME) {
            dateStr = getDayName() + ", " + dateStr;
        }

        return dateStr;
    }
	
	@Override
	public int compareTo(OCCCDate other) {
		if (this.year != other.year)
			return this.year - other.year;

		if (this.monthOfYear != other.monthOfYear)
			return this.monthOfYear - other.monthOfYear;

		return this.dayOfMonth - other.dayOfMonth;
	}
}