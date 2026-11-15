package com.staysync.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "complaints")
public class Complaint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 2000)
    private String description;

    @Column(nullable = false)
    private String category; // PLUMBING, ELECTRICAL, FURNITURE, CLEANING, WIFI_NETWORK, OTHER

    private String priority = "MEDIUM"; // LOW, MEDIUM, HIGH, URGENT

    private String aiPredictedCategory;
    private BigDecimal aiUrgencyScore;
    private String aiSuggestedAction;

    private String status = "SUBMITTED"; // SUBMITTED, IN_PROGRESS, RESOLVED, CLOSED, REJECTED

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "assigned_to_id")
    private User assignedTo;

    @Column(length = 2000)
    private String resolutionNotes;

    private LocalDateTime resolvedAt;
    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    public Complaint() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getStudent() { return student; }
    public void setStudent(User student) { this.student = student; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getPriority() { return priority; }
    public void setPriority(String priority) { this.priority = priority; }

    public String getAiPredictedCategory() { return aiPredictedCategory; }
    public void setAiPredictedCategory(String aiPredictedCategory) { this.aiPredictedCategory = aiPredictedCategory; }

    public BigDecimal getAiUrgencyScore() { return aiUrgencyScore; }
    public void setAiUrgencyScore(BigDecimal aiUrgencyScore) { this.aiUrgencyScore = aiUrgencyScore; }

    public String getAiSuggestedAction() { return aiSuggestedAction; }
    public void setAiSuggestedAction(String aiSuggestedAction) { this.aiSuggestedAction = aiSuggestedAction; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public User getAssignedTo() { return assignedTo; }
    public void setAssignedTo(User assignedTo) { this.assignedTo = assignedTo; }

    public String getResolutionNotes() { return resolutionNotes; }
    public void setResolutionNotes(String resolutionNotes) { this.resolutionNotes = resolutionNotes; }

    public LocalDateTime getResolvedAt() { return resolvedAt; }
    public void setResolvedAt(LocalDateTime resolvedAt) { this.resolvedAt = resolvedAt; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
