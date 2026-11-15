package com.staysync.controller;

import com.staysync.model.MessMenu;
import com.staysync.service.MessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mess")
public class MessController {

    @Autowired
    private MessService messService;

    @GetMapping
    public ResponseEntity<List<MessMenu>> getAllMenus() {
        return ResponseEntity.ok(messService.getAllMenus());
    }

    @GetMapping("/today/{day}")
    public ResponseEntity<List<MessMenu>> getTodayMenu(@PathVariable String day) {
        return ResponseEntity.ok(messService.getMenuByDay(day));
    }

    @PutMapping
    @PreAuthorize("hasAnyRole('WARDEN', 'ADMINISTRATOR')")
    public ResponseEntity<MessMenu> updateMeal(@RequestBody MessMenu menu) {
        return ResponseEntity.ok(messService.updateMeal(menu));
    }
}
