package crm.utils;

import java.io.IOException;
import java.util.Map.Entry;

import org.junit.Test;

import junit.framework.TestCase;

public class HttpUtilsTest extends TestCase {
	
	public void setUp() throws Exception {
		super.setUp();
	}
	
	@Test
	public void testPOST() {
		try {
			
			Entry<Integer, String> post = HttpUtils.post("https://stackoverflow.com/", "", null, HttpUtils.METHOD_POST);
			assertEquals(200, post.getKey().intValue());
			System.out.println(post.getValue());
			
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}
	}
	
	@Test
	public void testGET() {
		try {
			
			Entry<Integer, String> post = HttpUtils.post("https://stackoverflow.com/", "", null, HttpUtils.METHOD_GET);
			assertEquals(200, post.getKey().intValue());
			System.out.println(post.getValue());
			
		} catch (IOException e) {
			e.printStackTrace();
			fail();
		}
	}
}
