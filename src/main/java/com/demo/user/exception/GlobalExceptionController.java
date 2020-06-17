package com.demo.user.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.demo.user.util.Constants;

/**
 * Global exception handler controller
 * 
 * @author janbee
 *
 */
@ControllerAdvice
public class GlobalExceptionController {

	/**
	 * Handles UserExistsException
	 * @return String
	 */
	@ExceptionHandler(value = UserExistsException.class)
	public ResponseEntity<String> handleUserExistsException() {
		return new ResponseEntity<>(Constants.USER_EXISTS,HttpStatus.OK);
	}

	/**
	 * Handles UserNotFoundException
	 * @return String
	 */
	@ExceptionHandler(value = UserNotFoundException.class)
	public ResponseEntity<String> handleUserNotFoundException() {
		return new ResponseEntity<>(Constants.USER_NOT_FOUND,HttpStatus.OK);
	}
	
	/**
	 * Handles UserIsNotAllowedException
	 * @return String
	 */
	@ExceptionHandler(value = UserIsNotAllowedException.class)
	public ResponseEntity<String> handleUserIsNotAllowedException() {
		return new ResponseEntity<>(Constants.USER_NOT_ALLOWED,HttpStatus.OK);
	}
}
