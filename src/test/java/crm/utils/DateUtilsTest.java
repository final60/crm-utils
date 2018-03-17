package crm.utils;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import junit.framework.TestCase;

import org.junit.Test;

public class DateUtilsTest extends TestCase {

	private Date utilDate;
	private java.sql.Date sqlDate;
	private Timestamp sqlDateTime;
	
	public void setUp() throws Exception {
		super.setUp();
		
		utilDate = newDate("Sat Aug 19 17:51:38 BST 2017");
		sqlDate = new java.sql.Date(utilDate.getTime());
		sqlDateTime = new Timestamp(utilDate.getTime());
	}
	
	@Test
	public void testFormatDateString() throws ParseException {
		
		assertNotNull(DateUtils.formatDateString("EEE MMM dd HH:mm:ss z yyyy", "EEE MMM dd HH:mm:ss z yyyy", utilDate.toString()));
		assertEquals("19 Aug, 2017", DateUtils.formatDateString("EEE MMM dd HH:mm:ss z yyyy", "dd MMM, yyyy", utilDate.toString()));
		
		assertNotNull(DateUtils.formatDateString("yyyy-MM-dd", "d MMM, yyyy", sqlDate.toString()));
		assertEquals("19 Aug, 2017", DateUtils.formatDateString("yyyy-MM-dd", "d MMM, yyyy", sqlDate.toString()));
		
		assertNotNull(DateUtils.formatDateString("yyyy-MM-dd HH:mm:ss", "d MMM, yyyy", sqlDateTime.toString()));
		assertEquals("19 Aug, 2017", DateUtils.formatDateString("yyyy-MM-dd HH:mm:ss", "d MMM, yyyy", sqlDateTime.toString()));
	}
	
	@Test
	public void testShortDate() throws ParseException {
		
		 assertEquals("19 Aug, 2017", DateUtils.shortDate(utilDate));
	}
	
	@Test
	public void testLongDate() throws ParseException {
		
		 assertEquals("Saturday 19 August, 2017", DateUtils.longDate(utilDate));
	}
	
	private Date newDate(String date) throws ParseException {
		return new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy").parse(date);
	}
}
