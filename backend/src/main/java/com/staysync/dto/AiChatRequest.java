package com.staysync.dto;

public class AiChatRequest {
    private String prompt;
    private String contextRole; // e.g., STUDENT, WARDEN
    private String category;    // e.g., COMPLAINT_CLASSIFY, FAQ, ROOM_RECOMMEND

    public AiChatRequest() {}
    public AiChatRequest(String prompt) {
        this.prompt = prompt;
    }

    public String getPrompt() { return prompt; }
    public void setPrompt(String prompt) { this.prompt = prompt; }

    public String getContextRole() { return contextRole; }
    public void setContextRole(String contextRole) { this.contextRole = contextRole; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}
