package com.staysync.repository;

import com.staysync.model.Visitor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisitorRepository extends JpaRepository<Visitor, Long> {
    List<Visitor> findByStatus(String status);
    List<Visitor> findByStudentId(Long studentId);
}
