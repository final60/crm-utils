package crm.utils;

import java.util.Map;

import junit.framework.TestCase;

import org.junit.Test;

public class MapUtilsTest extends TestCase {

	public void setUp() throws Exception {
		super.setUp();
	}
	
	@Test
	public void testParseStringMap() {
		
		assertNotNull(MapUtils.parseStringMap("key=value"));
		assertEquals(1, MapUtils.parseStringMap("key=value").size());
		
		assertNotNull(MapUtils.parseStringMap("key=value", ","));
		assertEquals(1, MapUtils.parseStringMap("key=value", ",").size());
		
		assertEquals(1, MapUtils.parseStringMap("key=value,key=value", ",").size());
		assertEquals(1, MapUtils.parseStringMap("key=value1,key=value2", ",").size());
		assertEquals(2, MapUtils.parseStringMap("key1=value,key2=value", ",").size());
	}
	
	@Test
	public void testParseTypeValueMap() {
		assertEquals(1, MapUtils.parseTypeValueMap("key=1", Integer.class, ",").size());
		Map<String, Object> parseTypeValueMap = MapUtils.parseTypeValueMap("key=value", String.class, ",");
		assertEquals(1, parseTypeValueMap.size());
		Object value = parseTypeValueMap.get("key");
		assertEquals("value", value);
		assertTrue(value instanceof String);
		
		parseTypeValueMap = MapUtils.parseTypeValueMap("key=1", Integer.class, ",");
		value = parseTypeValueMap.get("key");
		assertEquals(1, value);
		assertTrue(value instanceof Integer);
		assertFalse(value instanceof String);
		
		parseTypeValueMap = MapUtils.parseTypeValueMap("key=1.1", Object.class, ",");
		value = parseTypeValueMap.get("key");
		assertFalse(value instanceof Integer);
		assertTrue(value instanceof String);
		assertEquals("1.1", value);
	}
}
