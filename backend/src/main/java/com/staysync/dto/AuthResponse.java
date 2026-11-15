package com.staysync.dto;

import com.staysync.model.Role;

public class AuthResponse {
    private String token;
    private String type = "Bearer";
    private Long id;
    private String name;
    private String email;
    private Role role;
    private String roomNumber;
    private String bedNumber;

    public AuthResponse() {}

    public AuthResponse(String token, Long id, String name, String email, Role role, String roomNumber, String bedNumber) {
        this.token = token;
        this.id = id;
        this.name = name;
        this.email = email;
        this.role = role;
        this.roomNumber = roomNumber;
        this.bedNumber = bedNumber;
    }

    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }

    public String getBedNumber() { return bedNumber; }
    public void setBedNumber(String bedNumber) { this.bedNumber = bedNumber; }
}
