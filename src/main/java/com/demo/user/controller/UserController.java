package com.demo.user.controller;

import java.sql.Timestamp;
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
import com.demo.user.exception.UserIsNotAllowedException;
import com.demo.user.exception.UserNotFoundException;
import com.demo.user.service.UserService;
import com.demo.user.util.Constants;

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
	 * @throws UserIsNotAllowedException 
	 */
	@ApiOperation("Create a user")
	@PostMapping("/")
	public ResponseEntity<User> create(@RequestBody UserReqDto userReqDto, @RequestParam Long adminUserId) throws UserExistsException, UserIsNotAllowedException {
		Timestamp startDate = new Timestamp(System.currentTimeMillis());
		User user = null;
		LOGGER.debug("UserController :: create() :: start "+userReqDto);
		String role = userService.getUserRole(adminUserId);
		if(role == null) {
			user = userService.create(userReqDto,startDate,adminUserId,role);
		} else if(role.equals(Constants.ADMIN)) {
			try {
				user = userService.create(userReqDto,startDate,adminUserId,role);
			}catch(UserExistsException e) {
				LOGGER.debug("UserController :: create() :: UserExistsException ");
				throw new UserExistsException();
			}
		} else {
			throw new UserIsNotAllowedException();
		}
		LOGGER.debug("UserController :: create() :: end ");
		return new ResponseEntity<>(user,HttpStatus.CREATED);
	}

	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @return list of users
	 * @throws UserIsNotAllowedException 
	 */
	@ApiOperation("Get all users")
	@GetMapping("/")
	public ResponseEntity<List<User>> getAll(@RequestParam Long adminUserId) throws UserIsNotAllowedException {
		Timestamp startDate = new Timestamp(System.currentTimeMillis());
		LOGGER.debug("UserController :: getAll() :: start ");
		List<User> userList = null;
		String role = userService.getUserRole(adminUserId);
		if(role.equals(Constants.ADMIN)) {
			userList = userService.getAll(startDate,adminUserId);
		} else {
			throw new UserIsNotAllowedException();
		}
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
	 * @throws UserIsNotAllowedException 
	 */
	@ApiOperation("Get a user")
	@GetMapping("/{userId}")
	public ResponseEntity<User> getById(@PathVariable Long userId, @RequestParam Long adminUserId) throws UserNotFoundException, UserIsNotAllowedException {
		Timestamp startDate = new Timestamp(System.currentTimeMillis());
		LOGGER.debug("UserController :: getById() :: start "+userId);
		User user;
		String role = userService.getUserRole(adminUserId);
		if(role.equals(Constants.ADMIN)) {
			user = userService.getById(userId,startDate,adminUserId);
		} else {
			throw new UserIsNotAllowedException();
		}
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
	 * @throws UserIsNotAllowedException 
	 */
	@ApiOperation("Update a user")
	@PutMapping("/{userId}")
	public ResponseEntity<String> update(@PathVariable Long userId,@RequestBody User user, @RequestParam Long adminUserId) throws UserNotFoundException, UserIsNotAllowedException {
		Timestamp startDate = new Timestamp(System.currentTimeMillis());
		LOGGER.debug("UserController :: update() :: start ");
		String msg;
		String role = userService.getUserRole(adminUserId);
		if(role.equals(Constants.ADMIN)) {
			try {
				msg = userService.update(userId, user,startDate,adminUserId);
			} catch(UserNotFoundException e) {
				LOGGER.error("UserController :: update() :: UserNotFoundException ");
				throw new UserNotFoundException();
			}
		} else {
			throw new UserIsNotAllowedException();
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
	 * @throws UserIsNotAllowedException 
	 */
	@ApiOperation("Delete a user")
	@DeleteMapping("/{userId}")
	public ResponseEntity<String> delete(@PathVariable Long userId,@RequestParam Long adminUserId) throws UserNotFoundException, UserIsNotAllowedException {
		Timestamp startDate = new Timestamp(System.currentTimeMillis());
		LOGGER.debug("UserController :: delete() :: start ");
		String msg =null;
		String role = userService.getUserRole(adminUserId);
		if(role.equals(Constants.ADMIN)) {
			msg = userService.delete(userId,startDate,adminUserId);
		} else {
			throw new UserIsNotAllowedException();
		}
		LOGGER.debug("UserController :: delete() :: end ");
		return new ResponseEntity<>(msg,HttpStatus.OK);
	}

	/**
	 * 
	 * @param actionPerformed
	 * @return Audit Object
	 * @throws UserIsNotAllowedException 
	 */
	@ApiOperation("Search based on admin action performed")
	@GetMapping("/search")
	public ResponseEntity<List<Audit>> search(@RequestParam String actionPerformed,@RequestParam Long adminUserId) throws UserIsNotAllowedException {
		LOGGER.debug("UserController :: search() :: start ");
		List<Audit> auditList = null;
		String role = userService.getUserRole(adminUserId);
		if(role.equals(Constants.ADMIN)) {
			auditList = userService.search(actionPerformed);
		} else {
			throw new UserIsNotAllowedException();
		}
		LOGGER.debug("UserController :: search() :: end ");
		return new ResponseEntity<>(auditList,HttpStatus.OK);
	}
}
