package com.demo.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.demo.user.dto.UserReqDto;
import com.demo.user.entity.User;
import com.demo.user.exception.UserExistsException;
import com.demo.user.exception.UserNotFoundException;
import com.demo.user.repository.UserRepository;


@SpringBootTest
@RunWith(value = SpringRunner.class)
public class UserServiceTest {

	@InjectMocks
	UserService userService;
	
	@Mock
	UserRepository userRepository;
	
	List<User> users ;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Before
	public void setup() {
		users = new ArrayList<>();
		User user1 = new User(1L,"xyz","xyz@gmail.com","773483848", "user");
		User user2 = new User(2L,"abc","abc@gmail.com","883483889", "user");
		users.add(user1);
		users.add(user2);
	}
	
	@Test
	public void testCreate() {
		User user = new User(4L,"xxx","xxx@gmail.com","423434777", "user");
		when(userRepository.save(user)).thenReturn(user);
		assertEquals("xxx@gmail.com", user.getMailId());
	}
	
	@Test(expected = UserNotFoundException.class)
	public void testUserNotFoundException() {
		userService.getById(3L, "20-02-2020", "21-02-2020");
	}
	
	@Test(expected = UserExistsException.class)
	public void testUserExistsException() throws UserExistsException {
		UserReqDto userReqDto = new UserReqDto("xyz","xyz@gmail.com","773483848", "user");
		userService.create(userReqDto, "20-02-2020", "21-02-2020");
	}
	
	@Test
	public void testGetAll() {
		Mockito.when(userRepository.findAll()).thenReturn(users);
		assertEquals(2,users.size());
	}
	
	@Test
	public void testGetById() {
		Optional<User> user = Optional.of(users.get(0));
		when(userRepository.findById(user.get().getId())).thenReturn(user);
		assertEquals("xyz", user.get().getName());
	}
	
	/*
	 * @Test public void testUpdate() {
	 * 
	 * when(userRepository.save()) }
	 */
	
	@Test
	public void testDelete() {
		verify(userRepository,times(1)).deleteById(users.get(0).getId());
	}
	
}
