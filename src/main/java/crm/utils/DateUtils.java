package crm.utils;

import static crm.utils.ValidationUtils.validateParam;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Utils methods related to Dates.
 * 
 * @author Chris Mepham
 */
public abstract class DateUtils {

	/**
	 * Converts a String date value from one date format to another. Useful for
	 * easily converting unix dates to human-readable equivalent.<br>
	 * For example:<br><br>
	 * <em>formatDateString("yyyy-MM-dd hh:mm:ss", "d MMM, yyyy", date.toString());</em>
	 * 
	 * @param from The format of the input value.
	 * @param to The format that you want the value to be changed to.
	 * @param date the String date value that will be converted to the new format
	 * 
	 * @return The input value after it has been converted to the new date format.
	 * 
	 * @throws ParseException
	 */
	public static final String formatDateString(String from, String to, String date) throws ParseException {
		
		validateParam(from, "from");
		validateParam(to, "to");
		validateParam(date, "date");
		
        SimpleDateFormat fromFormat = new SimpleDateFormat(from, Locale.ENGLISH);
        
        SimpleDateFormat toFormat = new SimpleDateFormat(to, Locale.ENGLISH);
        
        Date newDate = fromFormat.parse(date);
        
        return toFormat.format(newDate);
    }
	
	/**
	 * Returns a short format, human-readable String output of the supplied date. For example: "<em>19 Aug, 2017</em>".
	 * 
	 * @param date java.util.Date
	 * 
	 * @return Short String formatted version of the supplied date.
	 * 
	 * @throws ParseException
	 */
	public static final String shortDate(Date date) throws ParseException {
		
		return new SimpleDateFormat("dd MMM, yyyy", Locale.ENGLISH).format(date);
	}
	
	/**
	 * Returns a long format, human-readable String output of the supplied date. For example: "<em>Saturday 19 August, 2017</em>".
	 * 
	 * @param date java.util.Date
	 * 
	 * @return Long String formatted version of the supplied date.
	 * 
	 * @throws ParseException
	 */
	public static final String longDate(Date date) throws ParseException {
		
		return new SimpleDateFormat("EEEE d MMMM, yyyy", Locale.ENGLISH).format(date);
	}
}
