package com.staysync.repository;

import com.staysync.model.Bed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BedRepository extends JpaRepository<Bed, Long> {
    List<Bed> findByRoomId(Long roomId);
    List<Bed> findByIsOccupiedFalse();
    Optional<Bed> findByStudentId(Long studentId);
}
