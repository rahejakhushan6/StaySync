package com.staysync.service;

import com.staysync.dto.LeaveRequest;
import com.staysync.model.Leave;
import com.staysync.model.User;
import com.staysync.repository.LeaveRepository;
import com.staysync.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class LeaveService {

    @Autowired
    private LeaveRepository leaveRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Leave> getAllLeaves() {
        return leaveRepository.findAll();
    }

    public List<Leave> getLeavesByStudent(Long studentId) {
        return leaveRepository.findByStudentId(studentId);
    }

    public Leave applyLeave(Long studentId, LeaveRequest request) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Leave leave = new Leave();
        leave.setStudent(student);
        leave.setLeaveType(request.getLeaveType());
        leave.setDepartureDate(request.getDepartureDate());
        leave.setExpectedReturnDate(request.getExpectedReturnDate());
        leave.setReason(request.getReason());
        leave.setDestinationAddress(request.getDestinationAddress());
        leave.setParentContact(request.getParentContact());
        leave.setStatus("PENDING");

        return leaveRepository.save(leave);
    }

    public Leave reviewLeave(Long leaveId, Long wardenId, boolean approved, String remarks) {
        Leave leave = leaveRepository.findById(leaveId)
                .orElseThrow(() -> new RuntimeException("Leave application not found"));
        User warden = userRepository.findById(wardenId)
                .orElseThrow(() -> new RuntimeException("Warden not found"));

        leave.setApprovedBy(warden);
        leave.setWardenRemarks(remarks);
        if (approved) {
            leave.setStatus("APPROVED");
            leave.setGatePassToken("GP-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        } else {
            leave.setStatus("REJECTED");
        }

        return leaveRepository.save(leave);
    }

    public Leave processGateCheck(String gatePassToken, String action) {
        Leave leave = leaveRepository.findByGatePassToken(gatePassToken)
                .orElseThrow(() -> new RuntimeException("Invalid Gate Pass Token: " + gatePassToken));

        if ("CHECK_OUT".equalsIgnoreCase(action)) {
            leave.setStatus("CHECKED_OUT");
        } else if ("CHECK_IN".equalsIgnoreCase(action)) {
            leave.setStatus("CHECKED_IN");
            leave.setActualReturnDate(LocalDateTime.now());
        }

        return leaveRepository.save(leave);
    }
}
