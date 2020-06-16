package com.demo.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.user.entity.User;

/**
 * UserRepository class
 * @author janbee
 *
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long>{

	public User findByMailId(String mailId);
}
