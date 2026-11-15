package com.staysync.controller;

import com.staysync.dto.ComplaintRequest;
import com.staysync.model.Complaint;
import com.staysync.service.ComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/complaints")
public class ComplaintController {

    @Autowired
    private ComplaintService complaintService;

    @GetMapping
    @PreAuthorize("hasAnyRole('WARDEN', 'ADMINISTRATOR', 'MAINTENANCE')")
    public ResponseEntity<List<Complaint>> getAllComplaints() {
        return ResponseEntity.ok(complaintService.getAllComplaints());
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Complaint>> getComplaintsByStudent(@PathVariable Long studentId) {
        return ResponseEntity.ok(complaintService.getComplaintsByStudent(studentId));
    }

    @GetMapping("/maintenance/{staffId}")
    @PreAuthorize("hasAnyRole('MAINTENANCE', 'ADMINISTRATOR', 'WARDEN')")
    public ResponseEntity<List<Complaint>> getMaintenanceComplaints(@PathVariable Long staffId) {
        return ResponseEntity.ok(complaintService.getComplaintsByAssignee(staffId));
    }

    @PostMapping("/student/{studentId}")
    public ResponseEntity<?> submitComplaint(@PathVariable Long studentId, @RequestBody ComplaintRequest request) {
        try {
            Complaint complaint = complaintService.submitComplaint(studentId, request);
            return ResponseEntity.ok(complaint);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PatchMapping("/{complaintId}/status")
    @PreAuthorize("hasAnyRole('MAINTENANCE', 'WARDEN', 'ADMINISTRATOR')")
    public ResponseEntity<?> updateStatus(@PathVariable Long complaintId, @RequestBody Map<String, String> payload) {
        String status = payload.get("status");
        String resolutionNotes = payload.get("resolutionNotes");
        try {
            Complaint updated = complaintService.updateStatus(complaintId, status, resolutionNotes);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/{complaintId}/assign")
    @PreAuthorize("hasAnyRole('WARDEN', 'ADMINISTRATOR')")
    public ResponseEntity<?> assignStaff(@PathVariable Long complaintId, @RequestBody Map<String, Long> payload) {
        Long staffId = payload.get("staffId");
        try {
            Complaint updated = complaintService.assignToStaff(complaintId, staffId);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
