package crm.utils;

import static crm.utils.StringUtils.hasText;
import static crm.utils.ValidationUtils.validateParam;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public abstract class CollectionUtils {

	/**
	 * Parse supplied String as a collection of Strings. Disregards empty values. 
	 * 
	 * @param input The String input consisting of a comma-separated list of values.
	 * 
	 * @return A Collection of String. If <em>empty</em> or <em>null</em> it returns an empty list.
	 */
	public static final Collection<String> parseStringList(String input, String delimiter) {
		
		validateParam(input, "input");
		validateParam(delimiter, "delimiter");
		
		String[] values = input.split(delimiter);
		
		if (isEmpty(Arrays.asList(values))) return Collections.emptyList();
		
		Collection<String> collection = new ArrayList<String>();
		
		for (String value : values) {
			
			if (hasText(value)) {
				
				collection.add(value.trim());
			}
		}
		
		return collection;	
	}
	
	/**
	 * Returns true if the collection is <em>null</em> or <em>empty</em>.
	 * 
	 * @param collection
	 * 
	 * @return True if the collection is <em>null</em> or <em>empty</em>.
	 */
	@SuppressWarnings("rawtypes")
	public static final boolean isEmpty(Iterable collection) {
		
		return (collection == null || !(collection.iterator().hasNext()));
	}
}
