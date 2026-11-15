package com.staysync.service;

import com.staysync.model.Fee;
import com.staysync.model.User;
import com.staysync.repository.FeeRepository;
import com.staysync.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
public class FeeService {

    @Autowired
    private FeeRepository feeRepository;

    @Autowired
    private UserRepository userRepository;

    public List<Fee> getAllFees() {
        return feeRepository.findAll();
    }

    public List<Fee> getFeesByStudent(Long studentId) {
        return feeRepository.findByStudentId(studentId);
    }

    public Fee recordPayment(Long feeId, String paymentMethod) {
        Fee fee = feeRepository.findById(feeId)
                .orElseThrow(() -> new RuntimeException("Fee record not found"));

        fee.setStatus("PAID");
        fee.setPaidDate(LocalDate.now());
        fee.setPaymentMethod(paymentMethod != null ? paymentMethod : "Online Gateway");
        fee.setTransactionRef("TXN-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase());
        fee.setReceiptNumber("REC-" + LocalDate.now().getYear() + "-" + UUID.randomUUID().toString().substring(0, 6).toUpperCase());

        return feeRepository.save(fee);
    }

    public Fee createFee(Long studentId, Fee feeData) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        feeData.setStudent(student);
        feeData.setStatus("PENDING");
        return feeRepository.save(feeData);
    }
}
