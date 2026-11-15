package com.staysync.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rooms")
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String roomNumber;

    @Column(nullable = false)
    private String blockName;

    private Integer floorNumber;
    private String roomType; // SINGLE, DOUBLE, TRIPLE, DELUXE_AC
    private Integer capacity;
    private Integer occupiedCount = 0;
    private BigDecimal monthlyRent;
    private String amenities;
    private String status = "AVAILABLE"; // AVAILABLE, FULL, MAINTENANCE

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Bed> beds = new ArrayList<>();

    public Room() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }

    public String getBlockName() { return blockName; }
    public void setBlockName(String blockName) { this.blockName = blockName; }

    public Integer getFloorNumber() { return floorNumber; }
    public void setFloorNumber(Integer floorNumber) { this.floorNumber = floorNumber; }

    public String getRoomType() { return roomType; }
    public void setRoomType(String roomType) { this.roomType = roomType; }

    public Integer getCapacity() { return capacity; }
    public void setCapacity(Integer capacity) { this.capacity = capacity; }

    public Integer getOccupiedCount() { return occupiedCount; }
    public void setOccupiedCount(Integer occupiedCount) { this.occupiedCount = occupiedCount; }

    public BigDecimal getMonthlyRent() { return monthlyRent; }
    public void setMonthlyRent(BigDecimal monthlyRent) { this.monthlyRent = monthlyRent; }

    public String getAmenities() { return amenities; }
    public void setAmenities(String amenities) { this.amenities = amenities; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public List<Bed> getBeds() { return beds; }
    public void setBeds(List<Bed> beds) { this.beds = beds; }
}
