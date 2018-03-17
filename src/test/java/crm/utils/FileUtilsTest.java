package crm.utils;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import junit.framework.TestCase;

public class FileUtilsTest extends TestCase {
	
	/* Be very careful when changing this */
	private static final String BASE_DIR = "C:\\Users\\crm\\workspaces\\crm-utils\\test\\crm\\utils\\testDirectory";
	
	private static final String SECOND_DIR = "C:\\Users\\crm\\workspaces\\crm-utils\\test\\crm\\utils\\secondDirectory";
	
	public void setUp() throws Exception {
		super.setUp();
		
		new File(BASE_DIR).mkdir();
	}
	
	public void tearDown() throws Exception {
		super.tearDown();
		
		FileUtils.deleteDirectory(BASE_DIR);
	}
	
	@Test
	public void testDeleteDirectory() throws IOException {
		new File(BASE_DIR + File.separator + "subdirectory").mkdir();
		FileUtils.deleteDirectory(BASE_DIR + File.separator + "subdirectory");
		assertFalse(new File(BASE_DIR + File.separator + "subdirectory").exists());
		
		new File(BASE_DIR + File.separator + "subdirectory").mkdir();
		new File(BASE_DIR + File.separator + "subdirectory/test1.txt").createNewFile();
		
		FileUtils.deleteDirectory(BASE_DIR + File.separator + "subdirectory");
		assertFalse(new File(BASE_DIR + File.separator + "subdirectory").exists());
	}
	
	@Test
	public void testDeleteFile() throws IOException {
		String path = BASE_DIR + File.separator + "test.txt";
		
		new File(path).createNewFile();
		assertTrue(new File(path).exists());
		
		FileUtils.deleteFile(path);
		assertFalse(new File(path).exists());
	}
	
	@Test
	public void testGetFiles() throws IOException {
		new File(BASE_DIR + File.separator + "test1.txt").createNewFile();
		new File(BASE_DIR + File.separator + "test2.txt").createNewFile();
		assertEquals(2, FileUtils.getFiles(BASE_DIR, false).size());
		
		String subPath = BASE_DIR + File.separator + "subdirectory";
		new File(subPath).mkdir();
		new File(subPath + File.separator + "test4.txt").createNewFile();
		new File(subPath + File.separator + "test5.txt").createNewFile();
		
		subPath = subPath + File.separator + "thirdSubDirectory";
		new File(subPath).mkdir();
		new File(subPath + File.separator + "test6.txt").createNewFile();
		
		assertEquals(5, FileUtils.getFiles(BASE_DIR, true).size());
	}
	
	@Test
	public void testWriteByteArrayToFile() throws IOException {
		File file = FileUtils.writeByteArrayToFile(BASE_DIR + File.separator + "test.txt", "test\nanother line".getBytes());
		assertNotNull(file);
		assertTrue(file.exists());
		
		file = FileUtils.writeByteArrayToFile(BASE_DIR + File.separator + "test.txt", "replace\nanother line\nthird line".getBytes());
		assertNotNull(file);
		assertTrue(file.exists());
		
		String contents = FileUtils.readFileAsString(file, "UTF-8");
		assertEquals("replace\nanother line\nthird line\n", contents);
	}
	
	@Test
	public void testHasContent() throws IOException {
		File file = FileUtils.writeByteArrayToFile(BASE_DIR + File.separator + "test.txt", "test".getBytes());
		assertTrue(FileUtils.hasContent(file));
		
		file.delete();
		
		file = FileUtils.writeByteArrayToFile(BASE_DIR + File.separator + "test.txt", "".getBytes());
		assertFalse(FileUtils.hasContent(file));
	}
	
	@Test
	public void readFileAsString() throws IOException {
		File file = FileUtils.writeByteArrayToFile(BASE_DIR + File.separator + "test.txt", "test".getBytes());
		assertEquals("test", FileUtils.readFileAsString(file, "UTF-8"));
	}
	
	@Test
	public void testCopyFile() throws IOException {
		FileUtils.writeByteArrayToFile(BASE_DIR + File.separator + "test.txt", "test".getBytes());
		
		new File(BASE_DIR + File.separator + "subdirectory").mkdir();
		
		FileUtils.copyFile(BASE_DIR + File.separator + "test.txt", BASE_DIR + File.separator + "subdirectory/test.txt", true);
		
		File read = new File(BASE_DIR + File.separator + "subdirectory/test.txt");
		String contents = FileUtils.readFileAsString(read);
		assertEquals("test\n", contents);
		
		FileUtils.deleteFile(BASE_DIR + File.separator + "subdirectory/test.txt");
		
		new File(BASE_DIR + File.separator + "subdirectory").mkdir();
		
		File file = new File(BASE_DIR + File.separator + "subdirectory/test.txt");
		file.createNewFile();
		
		try {
			FileUtils.copyFile(BASE_DIR + File.separator + "test.txt", BASE_DIR + File.separator + "subdirectory/test.txt", false);
		} catch (IllegalStateException e) {
			assertEquals("Cannot copy source file 'C:\\Users\\crm\\workspaces\\crm-utils\\test\\crm\\utils\\testDirectory/test.txt' because target file 'C:\\Users\\crm\\workspaces\\crm-utils\\test\\crm\\utils\\testDirectory/subdirectory/test.txt' exists.", e.getMessage());
		}
	}
	
	@Test
	public void testCopyFilesRecursively() throws IOException {
		new File(BASE_DIR + File.separator + "test1.txt").createNewFile();
		new File(BASE_DIR + File.separator + "test2.txt").createNewFile();
		
		String subPath = BASE_DIR + File.separator + "subdirectory";
		new File(subPath).mkdir();
		
		new File(subPath + File.separator + "test4.txt").createNewFile();
		new File(subPath + File.separator + "test5.txt").createNewFile();
		
		FileUtils.copyDirectory(BASE_DIR, SECOND_DIR, false, true);
		assertEquals(2, FileUtils.getFiles(SECOND_DIR, true).size());
		
		FileUtils.copyDirectory(BASE_DIR, SECOND_DIR, true, true);
		assertEquals(4, FileUtils.getFiles(SECOND_DIR, true).size());
		
		FileUtils.deleteDirectory(SECOND_DIR);
		
		new File(SECOND_DIR).mkdir();
		new File(SECOND_DIR + File.separator + "test1.txt").createNewFile();
		FileUtils.writeByteArrayToFile(SECOND_DIR + File.separator + "test1.txt", "second".getBytes());
		FileUtils.writeByteArrayToFile(BASE_DIR + File.separator + "test1.txt", "base".getBytes());
		
		FileUtils.copyDirectory(BASE_DIR, SECOND_DIR, true, true);
		assertEquals(4, FileUtils.getFiles(SECOND_DIR, true).size());
		
		FileUtils.deleteDirectory(SECOND_DIR);
	}
}








