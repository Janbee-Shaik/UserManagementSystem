package com.demo.user.dto;

import lombok.Data;

/**
 * UserReqDto class
 * @author janbee
 *
 */
@Data
public class UserReqDto {

	private String name;
	private String mailId;
	private String role;

	public UserReqDto() {

	}

	public UserReqDto(String name,String mailId,String role) {
		this.name = name;
		this.mailId = mailId;
		this.role = role;
	}
}
