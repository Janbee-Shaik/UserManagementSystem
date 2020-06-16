package com.demo.user.exception;

/**
 * 
 * @author janbee
 *
 */
public class UserNotFoundException extends Exception{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UserNotFoundException() {

	}
	
	public UserNotFoundException(String msg) {
		super(msg);
	}
}
