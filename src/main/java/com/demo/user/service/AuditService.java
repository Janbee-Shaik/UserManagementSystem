package com.demo.user.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.user.entity.Audit;
import com.demo.user.repository.AuditRepository;

/**
 * 
 * @author janbee
 *
 */
@Service
public class AuditService {

	@Autowired
	AuditRepository auditRepository;

	/**
	 * Creates audit record
	 * 
	 * @param actionPerformed
	 * @param startDate
	 * @param endDate
	 */
	public void create(String actionPerformed, String startDate, String endDate) {
		Audit audit = new Audit();
		audit.setActionPerformed(actionPerformed);
		audit.setStartDate(startDate);
		audit.setEndDate(endDate);
		auditRepository.save(audit);
	}
}
