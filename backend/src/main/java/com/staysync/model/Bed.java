package com.staysync.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "beds")
public class Bed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    @JsonIgnore
    private Room room;

    @Column(nullable = false)
    private String bedNumber;

    private Boolean isOccupied = false;

    @OneToOne
    @JoinColumn(name = "student_id")
    private User student;

    private LocalDate assignedDate;

    public Bed() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Room getRoom() { return room; }
    public void setRoom(Room room) { this.room = room; }

    public String getBedNumber() { return bedNumber; }
    public void setBedNumber(String bedNumber) { this.bedNumber = bedNumber; }

    public Boolean getIsOccupied() { return isOccupied; }
    public void setIsOccupied(Boolean isOccupied) { this.isOccupied = isOccupied; }

    public User getStudent() { return student; }
    public void setStudent(User student) { this.student = student; }

    public LocalDate getAssignedDate() { return assignedDate; }
    public void setAssignedDate(LocalDate assignedDate) { this.assignedDate = assignedDate; }
}
