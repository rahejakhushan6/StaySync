package com.staysync.controller;

import com.staysync.model.Bed;
import com.staysync.model.Room;
import com.staysync.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @GetMapping
    public ResponseEntity<List<Room>> getAllRooms() {
        return ResponseEntity.ok(roomService.getAllRooms());
    }

    @GetMapping("/beds")
    public ResponseEntity<List<Bed>> getAllBeds() {
        return ResponseEntity.ok(roomService.getAllBeds());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getRoomById(@PathVariable Long id) {
        return roomService.getRoomById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/allocate")
    @PreAuthorize("hasAnyRole('WARDEN', 'ADMINISTRATOR')")
    public ResponseEntity<?> allocateBed(@RequestBody Map<String, Long> payload) {
        Long bedId = payload.get("bedId");
        Long studentId = payload.get("studentId");
        try {
            Bed bed = roomService.allocateBed(bedId, studentId);
            return ResponseEntity.ok(bed);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/deallocate/{bedId}")
    @PreAuthorize("hasAnyRole('WARDEN', 'ADMINISTRATOR')")
    public ResponseEntity<?> deallocateBed(@PathVariable Long bedId) {
        try {
            Bed bed = roomService.deallocateBed(bedId);
            return ResponseEntity.ok(bed);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
