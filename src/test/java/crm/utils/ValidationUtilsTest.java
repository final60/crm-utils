package crm.utils;

import org.junit.Test;

import junit.framework.TestCase;

public class ValidationUtilsTest extends TestCase {

	public void setUp() throws Exception {
		super.setUp();
	}
	
	@Test
	public void testHasText() {
		assertFailure("");
		assertFailure(" ");
		ValidationUtils.validateParam("valid", "param");
	}
	
	private void assertFailure(String input) {
		try {
			ValidationUtils.validateParam(input, "param");
			fail();
		} catch (IllegalArgumentException e) {
			assertEquals("Please specify value for parameter 'param'.", e.getMessage());
		}
	}
}
