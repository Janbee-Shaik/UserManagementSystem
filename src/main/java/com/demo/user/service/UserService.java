package com.demo.user.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.demo.user.dto.UserReqDto;
import com.demo.user.entity.Audit;
import com.demo.user.entity.User;
import com.demo.user.exception.UserExistsException;
import com.demo.user.exception.UserNotFoundException;
import com.demo.user.repository.AuditRepository;
import com.demo.user.repository.UserRepository;
import com.demo.user.util.Constants;

/**
 * 
 * @author janbee
 *
 */
@Service
public class UserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

	@Autowired
	UserRepository userRepository;

	@Autowired
	AuditRepository auditRepository;

	@Autowired
	AuditService auditService;

	/**
	 * 
	 * @param userReqDto
	 * @param startDate
	 * @param endDate
	 * @return User object
	 * @throws UserExistsException
	 */
	public User create(UserReqDto userReqDto,String startDate, String endDate) throws UserExistsException {
		LOGGER.info("UserService :: create() :: start");
		User user = userRepository.findByMailId(userReqDto.getMailId());
		if(!Objects.isNull(user)) {
			throw new UserExistsException();
		}
		User userObj = new User();
		userObj.setMailId(userReqDto.getMailId());
		userObj.setMobNum(userReqDto.getMobNum());
		userObj.setName(userReqDto.getName());
		userObj.setRole(userReqDto.getRole());
		User newUser = userRepository.save(userObj);
		requestToAuditService("create", startDate,endDate);
		LOGGER.info("UserService :: create() :: end");
		return newUser;
	}

	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @return list of users
	 */
	public List<User> getAll(String startDate, String endDate) {
		List<User> userList =  userRepository.findAll();
		requestToAuditService("getAll", startDate, endDate);
		return userList;
	}

	/**
	 * 
	 * @param userId
	 * @param startDate
	 * @param endDate
	 * @return User object
	 */
	public User getById(Long userId,String startDate, String endDate) {
		LOGGER.info("UserService :: getById() :: start");
		Optional<User> user = userRepository.findById(userId);
		requestToAuditService("getById", startDate, endDate);
		if(user.isPresent()) {
			return user.get();
		}
		LOGGER.info("UserService :: getById() :: end");
		return null;
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
	public String update(Long userId,User user,String startDate, String endDate) throws UserNotFoundException {
		LOGGER.info("UserService :: update() :: start");
		Optional<User> user1 = userRepository.findById(userId);
		if(user1.isPresent() && Objects.isNull(user1.get())) {
			throw new UserNotFoundException();
		}
		userRepository.save(user);
		requestToAuditService("update", startDate, endDate);
		LOGGER.info("UserService :: update() :: end");
		return Constants.USER_UPDATED;
	}

	/**
	 * 
	 * @param userId
	 * @param startDate
	 * @param endDate
	 * @return String
	 * @throws UserNotFoundException
	 */
	public String delete(Long userId,String startDate, String endDate) throws UserNotFoundException {
		LOGGER.info("UserService :: delete() :: start");
		Optional<User> user = userRepository.findById(userId);
		if(user.isPresent() && Objects.isNull(user.get())) {
			throw new UserNotFoundException();
		}
		userRepository.deleteById(userId);
		requestToAuditService("delete", startDate, endDate);
		LOGGER.info("UserService :: delete() :: end");
		return Constants.USER_DELETED;
	}

	/**
	 * 
	 * @param actionPerformed
	 * @return Audit object
	 */
	public List<Audit> search(String actionPerformed) {
		LOGGER.info("UserService :: search() :: start");
		return auditRepository.findByActionPerformed(actionPerformed);
	}

	
	private void requestToAuditService(String actionPerformed, String startDate, String endDate) {
		auditService.create(actionPerformed, startDate, endDate);
	}
}
