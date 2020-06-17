package com.demo.user.service;

import java.sql.Timestamp;
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
	public User create(UserReqDto userReqDto,Timestamp startDate,Long adminUserId,String role) throws UserExistsException {
		LOGGER.info("UserService :: create() :: start");
		User user = userRepository.findByMailId(userReqDto.getMailId());
		if(!Objects.isNull(user)) {
			throw new UserExistsException();
		}
		User userObj = new User();
		userObj.setMailId(userReqDto.getMailId());
		userObj.setName(userReqDto.getName());
		userObj.setRole(userReqDto.getRole());
		User newUser = userRepository.save(userObj);
		Timestamp endDate = new Timestamp(System.currentTimeMillis());
		if(role != null) {
			requestToAuditService("POST", startDate,endDate, adminUserId);
		}
		LOGGER.info("UserService :: create() :: end");
		return newUser;
	}

	/**
	 * 
	 * @param startDate
	 * @param endDate
	 * @return list of users
	 */
	public List<User> getAll(Timestamp startDate,Long adminUserId) {
		List<User> userList =  userRepository.findAll();
		Timestamp endDate = new Timestamp(System.currentTimeMillis());
		requestToAuditService("GET", startDate, endDate,adminUserId);
		return userList;
	}

	/**
	 * 
	 * @param userId
	 * @param startDate
	 * @param endDate
	 * @return User object
	 */
	public User getById(Long userId,Timestamp startDate, Long adminUserId) {
		LOGGER.info("UserService :: getById() :: start");
		Optional<User> user = userRepository.findById(userId);
		Timestamp endDate = new Timestamp(System.currentTimeMillis());
		requestToAuditService("GET", startDate, endDate,adminUserId);
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
	public String update(Long userId,User user,Timestamp startDate, Long adminUserId) throws UserNotFoundException {
		LOGGER.info("UserService :: update() :: start");
		Optional<User> user1 = userRepository.findById(userId);
		if(user1.isPresent() && Objects.isNull(user1.get())) {
			throw new UserNotFoundException();
		}
		userRepository.save(user);
		Timestamp endDate = new Timestamp(System.currentTimeMillis());
		requestToAuditService("PUT", startDate, endDate,adminUserId);
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
	public String delete(Long userId,Timestamp startDate,Long adminUserId) throws UserNotFoundException {
		LOGGER.info("UserService :: delete() :: start");
		Optional<User> user = userRepository.findById(userId);
		if(user.isPresent() && Objects.isNull(user.get())) {
			throw new UserNotFoundException();
		}
		userRepository.deleteById(userId);
		Timestamp endDate = new Timestamp(System.currentTimeMillis());
		requestToAuditService("DELETE", startDate, endDate,adminUserId);
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


	public String getUserRole(Long userId) {
		Optional<User> user = userRepository.findById(userId);
		if(user.isPresent()) {
			return user.get().getRole();
		}
		return null;
	}

	private void requestToAuditService(String actionPerformed, Timestamp startDate, Timestamp endDate,Long userId) {
		auditService.create(actionPerformed, startDate, endDate,userId);
	}
}
