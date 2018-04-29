package crm.utils;

import static crm.utils.StringUtils.hasText;
import static crm.utils.ValidationUtils.validateParam;
import static java.lang.String.format;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Utility methods related to Files.
 * 
 * @author Chris Mepham
 *
 */
public abstract class FileUtils {

	/**
	 * Deletes directory, including all files and all sub-directories
	 * 
	 * @param absolutePath The absolute path, including the directory, that should be deleted.
	 */
	public static final void deleteDirectory(String absolutePath) {
		
		validateParam(absolutePath, "absolutePath");
		
		File directory = new File(absolutePath);
		
		if (!directory.isDirectory()) {
			
			throw new IllegalStateException(format("Could not delete '%s' because it is not a directory.", absolutePath));
		}
		
		deleteRecursive(directory);
	}
	
	private static final void deleteRecursive(File path){
		
	    File[] c = path.listFiles();
	    
	    for (File file : c) {
	    	
	        if (file.isDirectory()) {
	        	
	            deleteRecursive(file);
	            
	            file.delete();
	            
	        } else {
	        	
	            file.delete();
	        }
	    }
	    
	    path.delete();
	}
	
	/**
	 * Deletes a specific file.
	 * 
	 * @param absolutePath The absolute path, including the file name that should be deleted.
	 */
	public static final void deleteFile(String absolutePath) {
		
		validateParam(absolutePath, "absolutePath");
		
		File file = new File(absolutePath);
		
		if (!file.isFile()) {
			
			throw new IllegalStateException(format("Could not delete '%s' because it is not a file.", absolutePath));
		}
		
		file.delete();
	}
	
	/**
	 * Returns all files in a specified directory.
	 * 
	 * @param path The absolute path to the directory containing the files to retrieve.
	 * @param recursive If true, all file in sub-directories will also be retrieved.
	 * 
	 * @return List of files from the specified directory and sub-directories if recursive.
	 */
	@SuppressWarnings("serial")
	public static final List<File> getFiles(String absolutePath, boolean recursive) {
		
		validateParam(absolutePath, "absolutePath");
		
		final File directory = new File(absolutePath);
		
		if (!directory.isDirectory()) {
			
			return new ArrayList<File>() {{ add(directory); }};
		}
		
		List<File> files = new ArrayList<File>();
		
		if (recursive) {
			
			files = getFilesRecursively(directory, files);
			
			return files;
		}
		
		File[] filez = directory.listFiles();
		
		
		for (File file : filez) {
			
			if (!file.isDirectory()) {
				
				files.add(file);
			}
		}
		
		return files;
		
	}
	
	private static final List<File> getFilesRecursively(File path, List<File> files) {

	    File[] filez = path.listFiles(); 
	    
	    for (File file : filez) {
	    	
	        if (file.isDirectory()) {
	        	
	        	getFilesRecursively(file, files);
	        	
	        } else {
	        	
	            files.add(file);
	        }
	    }
	    
	    return files;
	}
	
	/**
	 * Writes a byte array to a file.
	 * 
	 * @param absolutePath The absolute path, including the filename and extension.
	 * @param content The byte array contents of the file.
	 * @return The new or updated file.
	 * @throws IOException 
	 */
	public static final File writeByteArrayToFile(String absolutePath, byte[] content) throws IOException {
		
		validateParam(absolutePath, "absolutePath");
		
		File file = new File(absolutePath);
		file.createNewFile();
		
		if (content != null && content.length > 0) {
			
			FileOutputStream fos = new FileOutputStream(file);
			try {
				fos.write(content);
			} finally {
				fos.close();
			}
		}
		
		return file;
	}
	
	/**
	 * Reads the contents of a file as a String.
	 * 
	 * @param file The file that must be read.
	 * @param encoding The character encoding. Defaults to UTF-8.
	 * 
	 * @return The contents of the file as a String.
	 * 
	 * @throws IOException 
	 */
	public static final String readFileAsString(File file, String encoding) throws IOException {
		
		if (file == null) throw new IllegalArgumentException(format("Please specify value for parameter 'file'."));
		if (!hasText(encoding)) encoding = "UTF-8";
		
		if (hasContent(file)) {

			FileInputStream fis = new FileInputStream(file);

			BufferedReader br = new BufferedReader(new InputStreamReader(fis, encoding));

			StringBuilder sb = new StringBuilder();
			String line;

			while ((line = br.readLine()) != null) {

				sb.append(line);
				sb.append('\n');
			}

			br.close();
			
			return sb.toString();
		}
		
		return "";
	}
	
	/**
	 * Reads the contents of a file as a UTF-8 encoded String.
	 * 
	 * @param file The file that must be read.
	 * 
	 * @return The contents of the file as a String.
	 * 
	 * @throws IOException 
	 */
	public static final String readFileAsString(File file) throws IOException {
		return readFileAsString(file, "UTF-8");
	}
	
	/**
	 * Returns true if the file is not null and has content.
	 * 
	 * @param file The file to read.
	 * 
	 * @return True if the file is not null and has content.
	 * 
	 * @throws IOException
	 */
	public static final boolean hasContent(File file) throws IOException {
		
		if (file == null) return false;
		
		FileInputStream fis = new FileInputStream(file);

		boolean hasContent = (fis.available() > 0);

		fis.close();

		return hasContent;
	}
	
	/**
	 * Copies a single file.
	 * 
	 * @param sourcePath The absolute path to the file that will be copied.
	 * @param targetPath The absolute path to the new file.
	 * @param override If true, if the target file already exists it will be overridden with the contents of the source file. 
	 * 
	 * @throws IOException 
	 * @throws IllegalStateException If the targetPath 
	 */
	public static final void copyFile(String sourcePath, String targetPath, boolean override) throws IOException {
		
		validateParam(sourcePath, "sourcePath");
		validateParam(targetPath, "targetPath");
		
		if (sourcePath.equals(targetPath)) throw new IllegalStateException(format("Cannot copy source file because sourcePath '%s' matches targetPath '%s'.", sourcePath, targetPath));
		
		File fromFile = new File(sourcePath);
		
		if (!fromFile.exists()) throw new IllegalStateException(format("Cannot copy source file '%s' because it does not exist.", sourcePath));
		
		File toFile = new File(targetPath);
		
		if (toFile.exists() && !override) throw new IllegalStateException(format("Cannot copy source file '%s' because target file '%s' exists.", sourcePath, targetPath));
		
		toFile.createNewFile();
		
		writeByteArrayToFile(targetPath, readFileAsString(fromFile).getBytes());
	}
	
	/**
	 * Copies a source directory and its contents to the target directory. If the target directory contains a file
	 * by the same name, that file will be replaced with the contents of the file being copied.
	 * 
	 * @param sourcePath The absolute path to the directory that will be copied.
	 * @param targetPath The absolute path to the new directory.
	 * @param recursive If true, will also copy sub-directories and files in sub-directories.
	 * @param override If true, if the target file already exists it will be overridden with the contents of the source file.
	 * 
	 * @throws IOException 
	 */
	public static final void copyDirectory(String sourcePath, String targetPath, boolean recursive, boolean override) throws IOException {
		
		File sourceDirectory = validateCopy(sourcePath, targetPath);
		
		if (sourceDirectory != null) {
		
			File[] files = sourceDirectory.listFiles();
			
			if (!recursive) {	
				
				for (File file : files) {
					
					if (file.isFile()) {
						
						String filename = file.getName();
						
						copyFile(sourcePath + File.separator + filename, targetPath + File.separator + filename, override);
					}
				}
				
			} else {
				
				copyFilesRecursively(sourcePath, targetPath, files, override);
			}
		}
	}
	
	private static final void copyFilesRecursively(String fromPath, String toPath, File[] files, boolean override) throws IOException {
		
		for (File file : files) {
			
			String filename = file.getName();
			
			File toFile = new File(toPath);
			
			toFile.mkdirs();
			
			if (file.isDirectory()) {
				
				String from = fromPath + File.separator + file.getName();
				
				String to = toPath + File.separator + file.getName();
				
				File[] filez = file.listFiles();
				
				copyFilesRecursively(from, to, filez, override);
				
			} else {
				
				copyFile(fromPath + File.separator + filename, toPath + File.separator + filename, override);
			}
		}
	}
	
	private static final File validateCopy(String sourcePath, String targetPath) throws IOException {
		validateParam(sourcePath, "sourcePath");
		validateParam(targetPath, "targetPath");
		
		File sourceDirectory = new File(sourcePath);
		if (!sourceDirectory.exists() || !sourceDirectory.isDirectory()) throw new IllegalStateException(format("File '%s' is not a directory or does not exist.", sourcePath));
		
		File targetDirectory = new File(targetPath);
		targetDirectory.mkdirs();
		
		if (targetDirectory.exists() && !targetDirectory.isDirectory()) throw new IllegalStateException(format("File '%s' is not a directory.", targetPath)); 
		
		if (!targetDirectory.exists()) targetDirectory.createNewFile();
		
		if (sourceDirectory == targetDirectory) return null;
		
		return sourceDirectory;
	}
}















