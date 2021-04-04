package com.scpfoundation.psybotic.app.data;

public class FireBaseSendMessageAO {


    public FireBaseSendMessageAO(String title, String message, String topic, String token, ChatMessage
            chatMessage, String userId) {
        this.title = title;
        this.message = message;
        this.topic = topic;
        this.token = token;
        this.chatMessage = chatMessage;
        this.userId = userId;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public ChatMessage getChatMessage() {
        return chatMessage;
    }

    public void setChatMessage(ChatMessage chatMessage) {
        this.chatMessage = chatMessage;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    private String title;
    private String message;
    private String topic;
    private String token;
    private String userId;
    private ChatMessage chatMessage;
}
