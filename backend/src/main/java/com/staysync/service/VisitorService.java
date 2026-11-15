package com.staysync.service;

import com.staysync.model.User;
import com.staysync.model.Visitor;
import com.staysync.repository.UserRepository;
import com.staysync.repository.VisitorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class VisitorService {

    @Autowired
    private VisitorRepository visitorRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Visitor> getAllVisitors() {
        return visitorRepository.findAll();
    }

    public List<Visitor> getActiveVisitors() {
        return visitorRepository.findByStatus("INSIDE");
    }

    public Visitor logEntry(Long studentId, Visitor visitorData, Long gateOfficerId) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        User officer = userRepository.findById(gateOfficerId)
                .orElseThrow(() -> new RuntimeException("Gate Officer not found"));

        visitorData.setStudent(student);
        visitorData.setGateOfficer(officer);
        visitorData.setCheckInTime(LocalDateTime.now());
        visitorData.setStatus("INSIDE");

        return visitorRepository.save(visitorData);
    }

    public Visitor logExit(Long visitorId) {
        Visitor visitor = visitorRepository.findById(visitorId)
                .orElseThrow(() -> new RuntimeException("Visitor record not found"));

        visitor.setCheckOutTime(LocalDateTime.now());
        visitor.setStatus("CHECKED_OUT");

        return visitorRepository.save(visitor);
    }
}
