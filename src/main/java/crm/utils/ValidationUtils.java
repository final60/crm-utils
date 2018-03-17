package crm.utils;

import static crm.utils.StringUtils.hasText;
import static java.lang.String.format;

/**
 * Utility methods related to validation.
 * 
 * @author Chris Mepham
 *
 */
public abstract class ValidationUtils {

	public static final void validateParam(String input, String name) {
		
		if (!(hasText(input))) throw new IllegalArgumentException(format("Please specify value for parameter '%s'.", name));
	}
}















