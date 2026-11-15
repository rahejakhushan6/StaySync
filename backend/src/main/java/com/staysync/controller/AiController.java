package com.staysync.controller;

import com.staysync.dto.AiChatRequest;
import com.staysync.service.GroqAiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ai")
public class AiController {

    @Autowired
    private GroqAiService groqAiService;

    @PostMapping("/chat")
    public ResponseEntity<?> chat(@RequestBody AiChatRequest request) {
        Map<String, Object> reply = groqAiService.askAssistant(
                request.getPrompt(),
                request.getContextRole() != null ? request.getContextRole() : "STUDENT"
        );
        return ResponseEntity.ok(reply);
    }

    @PostMapping("/categorize-complaint")
    public ResponseEntity<?> categorizeComplaint(@RequestBody Map<String, String> payload) {
        String title = payload.getOrDefault("title", "");
        String description = payload.getOrDefault("description", "");
        return ResponseEntity.ok(groqAiService.categorizeComplaint(title, description));
    }

    @PostMapping("/recommend-room")
    public ResponseEntity<?> recommendRoom(@RequestBody Map<String, String> payload) {
        String year = payload.getOrDefault("yearOfStudy", "1");
        String habit = payload.getOrDefault("studyHabit", "MODERATE");
        String budget = payload.getOrDefault("budgetTier", "MEDIUM");
        String roomType = payload.getOrDefault("preferredRoomType", "DOUBLE");

        return ResponseEntity.ok(groqAiService.recommendRoom(year, habit, budget, roomType));
    }
}
