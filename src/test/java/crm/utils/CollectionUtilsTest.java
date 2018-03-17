package crm.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;

import crm.utils.CollectionUtils;
import junit.framework.TestCase;

public class CollectionUtilsTest extends TestCase {

	private Collection<String> collection;
	
	public void setUp() throws Exception {
		super.setUp();
		
		collection = new ArrayList<String>();
	}
	
	@Test
	public void testIsEmpty() {
		assertTrue(CollectionUtils.isEmpty(collection));
		assertTrue(CollectionUtils.isEmpty(Arrays.asList(new String[] {})));
		
		collection.add("one");
		
		assertFalse(CollectionUtils.isEmpty(collection));
		assertFalse(CollectionUtils.isEmpty(Arrays.asList(new String[] {"one","two"})));
		
	}
	
	@Test
	public void testParseStringList() {
		assertTrue(CollectionUtils.parseStringList(",,", ",").isEmpty());
		assertTrue(CollectionUtils.parseStringList(" , ", ",").isEmpty());
		assertTrue(CollectionUtils.parseStringList(" ,", ",").isEmpty());
		assertTrue(CollectionUtils.parseStringList("one", ",").size() == 1);
		assertTrue(CollectionUtils.parseStringList("one,two", ",").size() == 2);
		assertTrue(CollectionUtils.parseStringList("one,two,,", ",").size() == 2);
		assertTrue(CollectionUtils.parseStringList("one,two, ,,", ",").size() == 2);
		assertTrue(CollectionUtils.parseStringList(",one,two ,", ",").size() == 2);
	}
}
