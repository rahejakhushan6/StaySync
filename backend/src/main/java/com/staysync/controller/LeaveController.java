package com.staysync.controller;

import com.staysync.dto.LeaveRequest;
import com.staysync.model.Leave;
import com.staysync.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/leaves")
public class LeaveController {

    @Autowired
    private LeaveService leaveService;

    @GetMapping
    @PreAuthorize("hasAnyRole('WARDEN', 'ADMINISTRATOR', 'SECURITY')")
    public ResponseEntity<List<Leave>> getAllLeaves() {
        return ResponseEntity.ok(leaveService.getAllLeaves());
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Leave>> getStudentLeaves(@PathVariable Long studentId) {
        return ResponseEntity.ok(leaveService.getLeavesByStudent(studentId));
    }

    @PostMapping("/student/{studentId}")
    public ResponseEntity<?> applyLeave(@PathVariable Long studentId, @RequestBody LeaveRequest request) {
        try {
            Leave leave = leaveService.applyLeave(studentId, request);
            return ResponseEntity.ok(leave);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/{leaveId}/review")
    @PreAuthorize("hasAnyRole('WARDEN', 'ADMINISTRATOR')")
    public ResponseEntity<?> reviewLeave(
            @PathVariable Long leaveId,
            @RequestBody Map<String, Object> payload) {
        Long wardenId = Long.valueOf(payload.get("wardenId").toString());
        boolean approved = Boolean.parseBoolean(payload.get("approved").toString());
        String remarks = (String) payload.get("remarks");

        try {
            Leave reviewed = leaveService.reviewLeave(leaveId, wardenId, approved, remarks);
            return ResponseEntity.ok(reviewed);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/gate-check")
    @PreAuthorize("hasAnyRole('SECURITY', 'ADMINISTRATOR')")
    public ResponseEntity<?> processGateCheck(@RequestBody Map<String, String> payload) {
        String token = payload.get("gatePassToken");
        String action = payload.get("action"); // CHECK_OUT or CHECK_IN
        try {
            Leave updated = leaveService.processGateCheck(token, action);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
