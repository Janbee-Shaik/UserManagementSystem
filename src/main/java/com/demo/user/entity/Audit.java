package com.demo.user.entity;

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
	private String startDate;
	private String endDate;
	
	public Audit() {
		
	}
	
	public Audit(Long id,String actionPerformed,String startDate,String endDate) {
		this.id = id;
		this.actionPerformed = actionPerformed;
		this.startDate = startDate;
		this.endDate = endDate;
	}
}
