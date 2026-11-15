package com.staysync.controller;

import com.staysync.model.Fee;
import com.staysync.service.FeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/fees")
public class FeeController {

    @Autowired
    private FeeService feeService;

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'WARDEN')")
    public ResponseEntity<List<Fee>> getAllFees() {
        return ResponseEntity.ok(feeService.getAllFees());
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<Fee>> getStudentFees(@PathVariable Long studentId) {
        return ResponseEntity.ok(feeService.getFeesByStudent(studentId));
    }

    @PostMapping("/{feeId}/pay")
    public ResponseEntity<?> payFee(@PathVariable Long feeId, @RequestBody(required = false) Map<String, String> payload) {
        String method = payload != null ? payload.get("paymentMethod") : "Online Card/UPI";
        try {
            Fee updated = feeService.recordPayment(feeId, method);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/student/{studentId}")
    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public ResponseEntity<?> createFeeForStudent(@PathVariable Long studentId, @RequestBody Fee fee) {
        try {
            Fee created = feeService.createFee(studentId, fee);
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}
