package com.staysync.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class GroqAiService {

    @Value("${groq.api.key:}")
    private String groqApiKey;

    @Value("${groq.api.model:llama-3.3-70b-versatile}")
    private String groqModel;

    @Value("${groq.api.url:https://api.groq.com/openai/v1/chat/completions}")
    private String groqUrl;

    private final RestTemplate restTemplate = new RestTemplate();

    /**
     * General chat with StaySync Hostel AI Assistant
     */
    public Map<String, Object> askAssistant(String prompt, String userRole) {
        String systemInstruction = "You are StaySync AI, an intelligent 24/7 Hostel Operations & Student Life Assistant. " +
                "You assist students, wardens, admins, maintenance workers, and gate security. " +
                "Key Hostel Rules: Curfew is 10:00 PM; Mess breakfast is 7:30-9:30 AM, Lunch 12:30-2:30 PM, Dinner 7:45-9:45 PM; " +
                "Gate passes require warden approval; Fee payment deadline is 5th of every month; Emergency contact: Ext 9901. " +
                "Be polite, concise, structured, and helpful.";

        String aiResponse = callGroqApi(systemInstruction, prompt);
        if (aiResponse == null || aiResponse.isBlank()) {
            aiResponse = getHeuristicFallbackResponse(prompt, userRole);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("reply", aiResponse);
        response.put("model", (groqApiKey != null && !groqApiKey.isBlank()) ? groqModel : "StaySync Heuristic AI Engine");
        response.put("timestamp", new Date());
        return response;
    }

    /**
     * AI Automated Complaint Categorization & Urgency Analyzer
     */
    public Map<String, Object> categorizeComplaint(String title, String description) {
        String combined = (title + " " + description).toLowerCase();

        String predictedCategory = "OTHER";
        String priority = "MEDIUM";
        double urgencyScore = 0.50;
        String suggestedAction = "Standard technician review during working hours.";

        if (combined.contains("leak") || combined.contains("water") || combined.contains("pipe") || combined.contains("tap") || combined.contains("toilet") || combined.contains("drain")) {
            predictedCategory = "PLUMBING";
            if (combined.contains("flood") || combined.contains("overflow") || combined.contains("burst") || combined.contains("severe")) {
                priority = "URGENT";
                urgencyScore = 0.95;
                suggestedAction = "Emergency plumbing dispatch: isolate floor water valve immediately.";
            } else {
                priority = "HIGH";
                urgencyScore = 0.75;
                suggestedAction = "Plumbing kit inspection and washer replacement.";
            }
        } else if (combined.contains("spark") || combined.contains("shock") || combined.contains("short circuit") || combined.contains("smoke")) {
            predictedCategory = "ELECTRICAL";
            priority = "URGENT";
            urgencyScore = 0.98;
            suggestedAction = "URGENT: Trip main MCB breaker on floor board and dispatch electrician immediately.";
        } else if (combined.contains("fan") || combined.contains("light") || combined.contains("switch") || combined.contains("ac") || combined.contains("plug") || combined.contains("socket")) {
            predictedCategory = "ELECTRICAL";
            priority = "MEDIUM";
            urgencyScore = 0.60;
            suggestedAction = "Electrician maintenance check with capacitor/lamp kit.";
        } else if (combined.contains("wifi") || combined.contains("internet") || combined.contains("lan") || combined.contains("router") || combined.contains("network")) {
            predictedCategory = "WIFI_NETWORK";
            priority = "MEDIUM";
            urgencyScore = 0.50;
            suggestedAction = "Check floor access point signal and reset DHCP lease.";
        } else if (combined.contains("bed") || combined.contains("chair") || combined.contains("table") || combined.contains("cupboard") || combined.contains("door") || combined.contains("lock")) {
            predictedCategory = "FURNITURE";
            priority = combined.contains("lock") ? "HIGH" : "LOW";
            urgencyScore = combined.contains("lock") ? 0.80 : 0.35;
            suggestedAction = combined.contains("lock") ? "Locksmith dispatch for student room access." : "Carpentry repair ticket.";
        } else if (combined.contains("clean") || combined.contains("garbage") || combined.contains("smell") || combined.contains("cockroach") || combined.contains("pest")) {
            predictedCategory = "CLEANING";
            priority = "MEDIUM";
            urgencyScore = 0.55;
            suggestedAction = "Housekeeping sanitize and pest treatment schedule.";
        }

        Map<String, Object> result = new HashMap<>();
        result.put("predictedCategory", predictedCategory);
        result.put("suggestedPriority", priority);
        result.put("urgencyScore", urgencyScore);
        result.put("suggestedAction", suggestedAction);
        return result;
    }

    /**
     * AI Room Recommendation Engine based on student study habits and preferences
     */
    public Map<String, Object> recommendRoom(String yearOfStudy, String studyHabit, String budgetTier, String roomType) {
        Map<String, Object> recommendation = new HashMap<>();
        
        if ("DELUXE_AC".equalsIgnoreCase(roomType) || "HIGH".equalsIgnoreCase(budgetTier)) {
            recommendation.put("recommendedBlock", "Da Vinci Block (Executive Suite D-401)");
            recommendation.put("floorPreference", "Top Floor (Quiet Study Zone, high-speed fiber)");
            recommendation.put("matchReason", "Matches executive comfort with single-occupancy, air conditioning, and sound isolation.");
        } else if ("SINGLE".equalsIgnoreCase(roomType) || "NIGHT_OWL".equalsIgnoreCase(studyHabit)) {
            recommendation.put("recommendedBlock", "Aryabhata Block (East Wing, A-101/A-102)");
            recommendation.put("floorPreference", "1st Floor corner rooms");
            recommendation.put("matchReason", "Provides private study balcony and individual power backup for late-night academic focus.");
        } else {
            recommendation.put("recommendedBlock", "Bhabha Block (West Wing, B-204/B-205)");
            recommendation.put("floorPreference", "2nd Floor mid-corridor");
            recommendation.put("matchReason", "Affordable dual sharing with high collaborative peer engagement for engineering batches.");
        }

        return recommendation;
    }

    private String callGroqApi(String systemInstruction, String userMessage) {
        if (groqApiKey == null || groqApiKey.isBlank() || groqApiKey.startsWith("gsk_demo")) {
            return null; // Fall back to smart heuristic
        }

        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.setBearerAuth(groqApiKey);

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", groqModel);
            requestBody.put("messages", List.of(
                    Map.of("role", "system", "content", systemInstruction),
                    Map.of("role", "user", "content", userMessage)
            ));
            requestBody.put("temperature", 0.5);

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
            ResponseEntity<Map> response = restTemplate.postForEntity(groqUrl, entity, Map.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                List choices = (List) response.getBody().get("choices");
                if (choices != null && !choices.isEmpty()) {
                    Map firstChoice = (Map) choices.get(0);
                    Map message = (Map) firstChoice.get("message");
                    return (String) message.get("content");
                }
            }
        } catch (Exception e) {
            System.err.println("Groq API Call warning (using fallback): " + e.getMessage());
        }
        return null;
    }

    private String getHeuristicFallbackResponse(String prompt, String userRole) {
        String p = prompt.toLowerCase();
        if (p.contains("mess") || p.contains("food") || p.contains("menu") || p.contains("dinner") || p.contains("breakfast")) {
            return "🍽️ **Mess Schedule & Food Guidelines:**\n- Breakfast: 07:30 - 09:30 AM\n- Lunch: 12:30 - 02:30 PM\n- Evening Snacks: 05:00 - 06:15 PM\n- Dinner: 07:45 - 09:45 PM\nMenu feedback and dietary preferences can be updated via your Mess tab.";
        } else if (p.contains("leave") || p.contains("gate pass") || p.contains("outing") || p.contains("curfew")) {
            return "🚪 **Leave & Gate Pass Policy:**\n- Standard campus curfew is **10:00 PM**.\n- Submit your Leave Application at least 6 hours in advance for Warden review.\n- Once approved, show your digital QR Gate Pass to the gate security officer at Gate 1.";
        } else if (p.contains("fee") || p.contains("payment") || p.contains("receipt") || p.contains("due")) {
            return "💳 **Hostel & Mess Fee Policies:**\n- Hostel rent & mess fees are due by the **5th of every month**.\n- You can pay online with simulated instant receipt generation in the 'Fee Tracker' module.";
        } else if (p.contains("complaint") || p.contains("leak") || p.contains("fan") || p.contains("wifi") || p.contains("repair")) {
            return "🛠️ **Maintenance & Complaints:**\n- Submit a complaint in the Complaints section. Our AI will automatically categorize urgency and assign it to Maintenance (Marcus Fixer).\n- Electrical sparks or water flooding are treated as URGENT priority.";
        } else if (p.contains("room") || p.contains("bed") || p.contains("allocation")) {
            return "🛏️ **Room & Bed Allocation:**\n- Rooms are available in Single, Double, Triple, and Deluxe AC suites across Aryabhata, Bhabha, and Curie blocks. Wardens can reassign beds via the Warden Dashboard.";
        } else {
            return "👋 Hello! I am your **StaySync Assistant**. I can help you with:\n1. 🛏️ Room & Bed allocations\n2. 💳 Fee deadlines and receipts\n3. 🛠️ Lodging and tracking repair complaints\n4. 🚪 Leave requests & Gate Pass verification\n5. 🍽️ Today's Mess meal timings\n\nHow may I help you today?";
        }
    }
}
