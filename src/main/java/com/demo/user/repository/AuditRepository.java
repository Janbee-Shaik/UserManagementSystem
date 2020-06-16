package com.demo.user.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.user.entity.Audit;

/**
 * AuditRepository class
 * @author janbee
 *
 */
@Repository
public interface AuditRepository extends JpaRepository<Audit, Long>{
	
	public List<Audit> findByActionPerformed(String actionPerformed);
}
