package com.staysync.repository;

import com.staysync.model.Leave;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LeaveRepository extends JpaRepository<Leave, Long> {
    List<Leave> findByStudentId(Long studentId);
    List<Leave> findByStatus(String status);
    Optional<Leave> findByGatePassToken(String token);
}
