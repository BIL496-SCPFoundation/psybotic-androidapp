package com.scpfoundation.psybotic.app.ui.chatbot;

import java.util.Date;

public interface IMessage {

    String getId();
    String getText();
    Author getUser();
    Date getCreatedAt();
}
