package com.demo.user.entity;

import java.sql.Timestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

/**
 * Audit persistent entity class
 * 
 * @author janbee
 *
 */
@Entity
@Data
public class Audit {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String actionPerformed;
	private Timestamp startDate;
	private Timestamp endDate;
	private Long userId;
	
	public Audit() {
		
	}
	
	public Audit(Long id,String actionPerformed,Timestamp startDate,Timestamp endDate, Long userId) {
		this.id = id;
		this.actionPerformed = actionPerformed;
		this.startDate = startDate;
		this.endDate = endDate;
		this.userId = userId;
	}
}
