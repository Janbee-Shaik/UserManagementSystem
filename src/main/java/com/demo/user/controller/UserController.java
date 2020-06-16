package com.demo.user.controller;

import java.util.List;
import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.demo.user.dto.UserReqDto;
import com.demo.user.entity.Audit;
import com.demo.user.entity.User;
import com.demo.user.exception.UserExistsException;
import com.demo.user.exception.UserNotFoundException;
import com.demo.user.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * Controller for handling User operations
 * @author janbee
 *
 */
@Api(description = "Operations pertaining to user management system")
@RestController
@RequestMapping("/users")
public class UserController {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

	@Autowired
	UserService userService;

	/** 
	 * 
	 * @param userReqDto
	 * @param startDate
	 * @param endDate
	 * @return User object
	 * @throws UserExistsException
	 */
	@ApiOperation("Create a user")
	@PostMapping("/")
	public ResponseEntity<User> create(@RequestBody UserReqDto userReqDto,@RequestParam String
			startDate, @RequestParam String endDate) throws UserExistsException {
		LOGGER.debug("UserController :: create() :: start "+userReqDto);
		User user;
		try {
			user = userService.create(userReqDto,startDate,endDate);
		}catch(UserExistsException e) {
			LOGGER.debug("UserController :: create() :: UserExistsException ");
			throw new UserExistsException();
		}
		LOGGER.debug("UserController :: create() :: end ");
		return new ResponseEntity<>(user,HttpStatus.CREATED);
	}

	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @return list of users
	 */
	@ApiOperation("Get all users")
	@GetMapping("/")
	public ResponseEntity<List<User>> getAll(@RequestParam String
			startDate, @RequestParam String endDate) {
		LOGGER.debug("UserController :: getAll() :: start ");
		List<User> userList = userService.getAll(startDate,endDate);
		LOGGER.debug("UserController :: getAll() :: end ");
		return new ResponseEntity<>(userList,HttpStatus.OK);
	}

	/**
	 * 
	 * @param userId
	 * @param startDate
	 * @param endDate
	 * @return User object
	 * @throws UserNotFoundException
	 */
	@ApiOperation("Get a user")
	@GetMapping("/{userId}")
	public ResponseEntity<User> getById(@PathVariable Long userId,@RequestParam String
			startDate, @RequestParam String endDate) throws UserNotFoundException {
		LOGGER.debug("UserController :: getById() :: start "+userId);
		User user = userService.getById(userId,startDate,endDate);
		if(Objects.isNull(user)) {
			throw new UserNotFoundException();
		}
		LOGGER.debug("UserController :: getById() :: end ");
		return new ResponseEntity<>(user,HttpStatus.OK);
	}

	/**
	 * 
	 * @param userId
	 * @param user
	 * @param startDate
	 * @param endDate
	 * @return String
	 * @throws UserNotFoundException
	 */
	@ApiOperation("Update a user")
	@PutMapping("/userId")
	public ResponseEntity<String> update(@PathVariable Long userId,@RequestBody User user,@RequestParam String
			startDate, @RequestParam String endDate) throws UserNotFoundException {
		LOGGER.debug("UserController :: update() :: start ");
		String msg;
		try {
			msg = userService.update(userId, user,startDate,endDate);
		} catch(UserNotFoundException e) {
			LOGGER.error("UserController :: update() :: UserNotFoundException ");
			throw new UserNotFoundException();
		}
		LOGGER.debug("UserController :: update() :: end ");
		return new ResponseEntity<>(msg,HttpStatus.OK);
	}

	/**
	 * 
	 * @param userId
	 * @param startDate
	 * @param endDate
	 * @return String
	 * @throws UserNotFoundException
	 */
	@ApiOperation("Delete a user")
	@DeleteMapping("/userId")
	public ResponseEntity<String> delete(@PathVariable Long userId,@RequestParam String
			startDate, @RequestParam String endDate) throws UserNotFoundException {
		LOGGER.debug("UserController :: delete() :: start ");
		String msg = userService.delete(userId,startDate,endDate);
		LOGGER.debug("UserController :: delete() :: end ");
		return new ResponseEntity<>(msg,HttpStatus.OK);
	}

	/**
	 * 
	 * @param actionPerformed
	 * @return Audit Object
	 */
	@ApiOperation("searching based on admin action performed")
	@GetMapping("/search")
	public ResponseEntity<List<Audit>> search(@RequestParam String actionPerformed) {
		LOGGER.debug("UserController :: search() :: start ");
		List<Audit> auditList = userService.search(actionPerformed);
		LOGGER.debug("UserController :: search() :: end ");
		return new ResponseEntity<>(auditList,HttpStatus.OK);
	}
}
