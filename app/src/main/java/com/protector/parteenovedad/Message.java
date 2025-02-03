package com.protector.parteenovedad;

public class Message {
    private String senderId;
    private String senderName;
    private String content;
    private long timestamp;

    public Message() {
        // Constructor vacío requerido para Firebase
    }

    public Message(String senderId, String senderName, String content, long timestamp) {
        this.senderId = senderId;
        this.senderName = senderName;
        this.content = content;
        this.timestamp = timestamp;
    }

    // Getters
    public String getSenderId() { return senderId; }
    public String getSenderName() { return senderName; }
    public String getContent() { return content; }
    public long getTimestamp() { return timestamp; }

    // Setters
    public void setSenderId(String senderId) { this.senderId = senderId; }
    public void setSenderName(String senderName) { this.senderName = senderName; }
    public void setContent(String content) { this.content = content; }
    public void setTimestamp(long timestamp) { this.timestamp = timestamp; }
}