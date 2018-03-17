package crm.utils;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)

@Suite.SuiteClasses({
	   MapUtilsTest.class,
	   StringUtilsTest.class,
	   DateUtilsTest.class,
	   CollectionUtilsTest.class,
	   HttpUtilsTest.class,
	   FileUtilsTest.class,
	   ValidationUtilsTest.class
	})

public class TestSuite {}
