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
	private String mobNum;
	private String role;

	public UserReqDto() {

	}

	public UserReqDto(String name,String mailId,String mobNum,String role) {
		this.name = name;
		this.mailId = mailId;
		this.mobNum = mobNum;
		this.role = role;
	}
}
