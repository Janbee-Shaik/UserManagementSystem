package com.demo.user.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

/**
 * User persistent entity class
 * 
 * @author janbee
 *
 */
@Entity
@Data
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long userId;
	private String name;
	private String mailId;
	private String role;

	public User() {

	}

	public User(Long id, String name,String mailId,String role) {
		this.userId= id;
		this.mailId= mailId;
		this.name = name;
		this.role = role;
	}
}
