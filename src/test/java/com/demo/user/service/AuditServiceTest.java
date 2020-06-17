package com.demo.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.sql.Timestamp;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.demo.user.entity.Audit;
import com.demo.user.entity.User;
import com.demo.user.repository.AuditRepository;

@SpringBootTest
@RunWith(value = SpringRunner.class)
public class AuditServiceTest {

	@InjectMocks
	AuditService auditService;
	
	@Mock
	AuditRepository auditRepository;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void testCreate() {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		Audit audit = new Audit(1L, "POST",timestamp,timestamp ,2L);
		when(auditRepository.save(audit)).thenReturn(audit);
		assertEquals("POST", audit.getActionPerformed());
	}
}
