package com.staysync.service;

import com.staysync.dto.ComplaintRequest;
import com.staysync.model.Complaint;
import com.staysync.model.User;
import com.staysync.repository.ComplaintRepository;
import com.staysync.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
public class ComplaintService {

    @Autowired
    private ComplaintRepository complaintRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GroqAiService groqAiService;

    public List<Complaint> getAllComplaints() {
        return complaintRepository.findAll();
    }

    public List<Complaint> getComplaintsByStudent(Long studentId) {
        return complaintRepository.findByStudentId(studentId);
    }

    public List<Complaint> getComplaintsByAssignee(Long technicianId) {
        return complaintRepository.findByAssignedToId(technicianId);
    }

    public Complaint submitComplaint(Long studentId, ComplaintRequest request) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Complaint complaint = new Complaint();
        complaint.setStudent(student);
        complaint.setTitle(request.getTitle());
        complaint.setDescription(request.getDescription());

        // AI-powered auto-categorization
        Map<String, Object> aiAnalysis = groqAiService.categorizeComplaint(request.getTitle(), request.getDescription());
        String aiCategory = (String) aiAnalysis.get("predictedCategory");
        String aiPriority = (String) aiAnalysis.get("suggestedPriority");
        Double urgencyScore = (Double) aiAnalysis.get("urgencyScore");
        String suggestedAction = (String) aiAnalysis.get("suggestedAction");

        complaint.setCategory(request.getCategory() != null ? request.getCategory() : aiCategory);
        complaint.setPriority(request.getPriority() != null ? request.getPriority() : aiPriority);
        complaint.setAiPredictedCategory(aiCategory);
        complaint.setAiUrgencyScore(BigDecimal.valueOf(urgencyScore));
        complaint.setAiSuggestedAction(suggestedAction);
        complaint.setStatus("SUBMITTED");

        return complaintRepository.save(complaint);
    }

    public Complaint updateStatus(Long complaintId, String status, String resolutionNotes) {
        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new RuntimeException("Complaint not found"));

        complaint.setStatus(status);
        if (resolutionNotes != null) {
            complaint.setResolutionNotes(resolutionNotes);
        }
        if ("RESOLVED".equalsIgnoreCase(status) || "CLOSED".equalsIgnoreCase(status)) {
            complaint.setResolvedAt(LocalDateTime.now());
        }
        complaint.setUpdatedAt(LocalDateTime.now());

        return complaintRepository.save(complaint);
    }

    public Complaint assignToStaff(Long complaintId, Long staffId) {
        Complaint complaint = complaintRepository.findById(complaintId)
                .orElseThrow(() -> new RuntimeException("Complaint not found"));
        User staff = userRepository.findById(staffId)
                .orElseThrow(() -> new RuntimeException("Staff not found"));

        complaint.setAssignedTo(staff);
        complaint.setStatus("IN_PROGRESS");
        complaint.setUpdatedAt(LocalDateTime.now());
        return complaintRepository.save(complaint);
    }
}
