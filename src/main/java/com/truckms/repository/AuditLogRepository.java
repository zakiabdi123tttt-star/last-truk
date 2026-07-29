package com.truckms.repository;

import com.truckms.entity.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface AuditLogRepository extends JpaRepository<AuditLog, Integer> {
    List<AuditLog> findByEntityNameAndEntityId(String entityName, Integer entityId);
    List<AuditLog> findTop50ByOrderByCreatedAtDesc();
}
