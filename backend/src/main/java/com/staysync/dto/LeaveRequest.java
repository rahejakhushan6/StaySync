package com.staysync.dto;

import java.time.LocalDateTime;

public class LeaveRequest {
    private String leaveType;
    private LocalDateTime departureDate;
    private LocalDateTime expectedReturnDate;
    private String reason;
    private String destinationAddress;
    private String parentContact;

    public LeaveRequest() {}

    public String getLeaveType() { return leaveType; }
    public void setLeaveType(String leaveType) { this.leaveType = leaveType; }

    public LocalDateTime getDepartureDate() { return departureDate; }
    public void setDepartureDate(LocalDateTime departureDate) { this.departureDate = departureDate; }

    public LocalDateTime getExpectedReturnDate() { return expectedReturnDate; }
    public void setExpectedReturnDate(LocalDateTime expectedReturnDate) { this.expectedReturnDate = expectedReturnDate; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public String getDestinationAddress() { return destinationAddress; }
    public void setDestinationAddress(String destinationAddress) { this.destinationAddress = destinationAddress; }

    public String getParentContact() { return parentContact; }
    public void setParentContact(String parentContact) { this.parentContact = parentContact; }
}
