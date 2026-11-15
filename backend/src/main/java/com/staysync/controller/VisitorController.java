package com.staysync.controller;

import com.staysync.model.Visitor;
import com.staysync.service.VisitorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/visitors")
public class VisitorController {

    @Autowired
    private VisitorService visitorService;

    @GetMapping
    @PreAuthorize("hasAnyRole('SECURITY', 'WARDEN', 'ADMINISTRATOR')")
    public ResponseEntity<List<Visitor>> getAllVisitors() {
        return ResponseEntity.ok(visitorService.getAllVisitors());
    }

    @GetMapping("/active")
    @PreAuthorize("hasAnyRole('SECURITY', 'WARDEN', 'ADMINISTRATOR')")
    public ResponseEntity<List<Visitor>> getActiveVisitors() {
        return ResponseEntity.ok(visitorService.getActiveVisitors());
    }

    @PostMapping("/entry")
    @PreAuthorize("hasAnyRole('SECURITY', 'ADMINISTRATOR')")
    public ResponseEntity<?> logEntry(
            @RequestParam Long studentId,
            @RequestParam Long gateOfficerId,
            @RequestBody Visitor visitor) {
        try {
            Visitor logged = visitorService.logEntry(studentId, visitor, gateOfficerId);
            return ResponseEntity.ok(logged);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/{visitorId}/exit")
    @PreAuthorize("hasAnyRole('SECURITY', 'ADMINISTRATOR')")
    public ResponseEntity<?> logExit(@PathVariable Long visitorId) {
        try {
            Visitor exited = visitorService.logExit(visitorId);
            return ResponseEntity.ok(exited);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
