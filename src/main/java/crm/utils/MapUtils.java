package crm.utils;

import static crm.utils.ValidationUtils.validateParam;

import java.util.HashMap;
import java.util.Map;

/**
 * Utils class providing static methods for creating Maps
 * from a variety of inputs.
 * 
 * @author Chris Mepham
 */
public abstract class MapUtils {

	/**
	 * Parse a String into a Map consisting of a String key and a specified
	 * value type.<br> 
	 * Unsupported types are defaulted to String.<br>
	 * Example: <em>parseTypeValueMap("key1=1,key2=1", Integer.class, ",")</em>
	 * 
	 * @param input The String containing key/value pairs separated by an equals (=) delimiter.
	 * @param clazz The object type of the value.
	 * @param delimiter The delimiter that separates the key/value pairs.
	 * 
	 * @return A Map of String keys and derived object type values.
	 */
	public static final <T extends Object> Map<String, Object> parseTypeValueMap(String input, Class<T> clazz, String delimiter) {
		
		validateParam(input, "input");
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		String[] entries = input.split(delimiter);
		
		for (String entry : entries) {
			
			String key = entry.split("=")[0];
			Object value = null;
			
			if (clazz == Integer.class) {
				value = Integer.valueOf(entry.split("=")[1]);				
			}
			
			if (clazz == String.class || value == null) {
				value = String.valueOf(entry.split("=")[1]);
			}
			
			map.put(key, value);
		}
		
		return map;
	}
	
	/**
	 * Parse a String into a Map consisting of a String key and value
	 * Expects the input in format <em>'key1=value,key2=value,key3=value'</em>.<br>
	 * Optionally supply the delimiter, otherwise comma ( , ) is used.
	 * 
	 * @param input The String containing key/value pairs separated by an equals (=) delimiter.
	 * @param delimiter The delimiter that separates the key/value pairs.
	 * 
	 * @return A Map<String, String> parsed from the String input.
	 */
	public static final Map<String, String> parseStringMap(String input, String delimiter) {
		
		validateParam(input, "input");
		validateParam(delimiter, "delimiter");
		
		Map<String, String> map = new HashMap<String, String>();
		
		String[] entries = input.split(delimiter);
		
		for (String entry : entries) {
			
			String key = entry.split("=")[0];
			String value = entry.split("=")[1];
			
			map.put(key, value);
		}
		 
		return map;
	}
	
	/**
	 * Parse a String into a Map consisting of a String key and value
	 * Expects the input in format <em>'key1=value,key2=value,key3=value'</em>.<br>
	 * Comma ( , ) is expected to be the delimiter.
	 * 
	 * @param input The String containing key/value pairs separated by an equals (=) delimiter.
	 * 
	 * @return A Map<String, String> parsed from the String input.
	 */
	public static final Map<String, String> parseStringMap(String input) {
		return parseStringMap(input, ",");
	}
}
