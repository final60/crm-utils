package crm.utils;

import org.junit.Test;

import junit.framework.TestCase;

public class StringUtilsTest extends TestCase {

	public void setUp() throws Exception {
		super.setUp();
	}
	
	@Test
	public void testHasText() {
		assertFalse(StringUtils.hasText(null));
		assertFalse(StringUtils.hasText(""));
		assertFalse(StringUtils.hasText(" "));
		assertTrue(StringUtils.hasText("a"));
	}
	
	@Test
	public void testPrettyPrintXml() {
		
		String result = ""; 
		
		try {
		
			StringUtils.prettyPrintXml("<something><inner></inner></something>", 2);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		System.out.println(result);
	}
	
	@Test
	public void testPrettPrintJson() throws Exception {
		String result = ""; 
		try {
			StringUtils.prettyPrintJson("{\"id\": 1,\"name\": \"A green door\",\"price\": 12.50,\"tags\": [\"home\", \"green\"],\"tagees\": [\"home\", \"green\"]}", 2);
		} catch (Exception e) {
			e.printStackTrace();
			fail();
		}
		System.out.println(result);
	}
}
