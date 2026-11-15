package com.staysync.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "leaves")
public class Leave {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id", nullable = false)
    private User student;

    @Column(nullable = false)
    private String leaveType; // HOME_VISIT, MEDICAL, ACADEMIC_CONFERENCE, EMERGENCY, VACATION

    @Column(nullable = false)
    private LocalDateTime departureDate;

    @Column(nullable = false)
    private LocalDateTime expectedReturnDate;

    private LocalDateTime actualReturnDate;

    @Column(nullable = false, length = 1000)
    private String reason;

    @Column(nullable = false)
    private String destinationAddress;

    @Column(nullable = false)
    private String parentContact;

    private String status = "PENDING"; // PENDING, APPROVED, REJECTED, CHECKED_OUT, CHECKED_IN

    private String wardenRemarks;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "approved_by_id")
    private User approvedBy;

    private String gatePassToken;
    private LocalDateTime createdAt = LocalDateTime.now();

    public Leave() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getStudent() { return student; }
    public void setStudent(User student) { this.student = student; }

    public String getLeaveType() { return leaveType; }
    public void setLeaveType(String leaveType) { this.leaveType = leaveType; }

    public LocalDateTime getDepartureDate() { return departureDate; }
    public void setDepartureDate(LocalDateTime departureDate) { this.departureDate = departureDate; }

    public LocalDateTime getExpectedReturnDate() { return expectedReturnDate; }
    public void setExpectedReturnDate(LocalDateTime expectedReturnDate) { this.expectedReturnDate = expectedReturnDate; }

    public LocalDateTime getActualReturnDate() { return actualReturnDate; }
    public void setActualReturnDate(LocalDateTime actualReturnDate) { this.actualReturnDate = actualReturnDate; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public String getDestinationAddress() { return destinationAddress; }
    public void setDestinationAddress(String destinationAddress) { this.destinationAddress = destinationAddress; }

    public String getParentContact() { return parentContact; }
    public void setParentContact(String parentContact) { this.parentContact = parentContact; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getWardenRemarks() { return wardenRemarks; }
    public void setWardenRemarks(String wardenRemarks) { this.wardenRemarks = wardenRemarks; }

    public User getApprovedBy() { return approvedBy; }
    public void setApprovedBy(User approvedBy) { this.approvedBy = approvedBy; }

    public String getGatePassToken() { return gatePassToken; }
    public void setGatePassToken(String gatePassToken) { this.gatePassToken = gatePassToken; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
