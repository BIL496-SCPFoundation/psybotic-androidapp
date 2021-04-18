package com.scpfoundation.psybotic.app.ui.realUserChat;

import com.scpfoundation.psybotic.app.data.ChatMessage;
import com.scpfoundation.psybotic.app.data.User;

public class ChatroomResponse {
    private User user;
    private ChatMessage lastMessage;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ChatMessage getLastMessage() {
        return lastMessage;
    }

    public void setLastMessage(ChatMessage lastMessage) {
        this.lastMessage = lastMessage;
    }
}
