package com.scpfoundation.psybotic.app.ui.chatbot;

import java.util.Date;

public class Message implements IMessage, com.stfalcon.chatkit.commons.models.IMessage {


    String id;
    String text;
    Author author;
    Date createdAt;

    public Message(){
        id = null;
        text = null;
        author = null;
        createdAt = null;
    }
    @Override
    public String getId() {

        return id;
    }

    @Override
    public String getText() {
        return text;
    }

    @Override
    public Author getUser() {
        return author;
    }

    @Override
    public Date getCreatedAt() {
        return createdAt;
    }
}
