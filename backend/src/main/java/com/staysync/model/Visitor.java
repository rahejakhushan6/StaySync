package com.staysync.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "visitors")
public class Visitor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String visitorName;

    @Column(nullable = false)
    private String visitorPhone;

    @Column(nullable = false)
    private String idProofType; // AADHAAR, PASSPORT, DRIVING_LICENSE, VOTER_ID, OTHER

    @Column(nullable = false)
    private String idProofNumber;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @Column(nullable = false)
    private String relationToStudent;

    @Column(nullable = false, length = 1000)
    private String purpose;

    @Column(nullable = false)
    private LocalDateTime checkInTime = LocalDateTime.now();

    private LocalDateTime checkOutTime;
    private String status = "INSIDE"; // INSIDE, CHECKED_OUT

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "gate_officer_id")
    private User gateOfficer;

    private LocalDateTime createdAt = LocalDateTime.now();

    public Visitor() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getVisitorName() { return visitorName; }
    public void setVisitorName(String visitorName) { this.visitorName = visitorName; }

    public String getVisitorPhone() { return visitorPhone; }
    public void setVisitorPhone(String visitorPhone) { this.visitorPhone = visitorPhone; }

    public String getIdProofType() { return idProofType; }
    public void setIdProofType(String idProofType) { this.idProofType = idProofType; }

    public String getIdProofNumber() { return idProofNumber; }
    public void setIdProofNumber(String idProofNumber) { this.idProofNumber = idProofNumber; }

    public User getStudent() { return student; }
    public void setStudent(User student) { this.student = student; }

    public String getRelationToStudent() { return relationToStudent; }
    public void setRelationToStudent(String relationToStudent) { this.relationToStudent = relationToStudent; }

    public String getPurpose() { return purpose; }
    public void setPurpose(String purpose) { this.purpose = purpose; }

    public LocalDateTime getCheckInTime() { return checkInTime; }
    public void setCheckInTime(LocalDateTime checkInTime) { this.checkInTime = checkInTime; }

    public LocalDateTime getCheckOutTime() { return checkOutTime; }
    public void setCheckOutTime(LocalDateTime checkOutTime) { this.checkOutTime = checkOutTime; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public User getGateOfficer() { return gateOfficer; }
    public void setGateOfficer(User gateOfficer) { this.gateOfficer = gateOfficer; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
