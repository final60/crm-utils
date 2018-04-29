package com.crm.exception;

/**
 * Thrown when an entity is not in the correct state for the operation
 * about to be performed on it.
 * 
 * @author Chris Mepham
 */
public class InvalidStateException extends RuntimeException {
	
	public InvalidStateException(String message) {
		super(message);
	}

}
