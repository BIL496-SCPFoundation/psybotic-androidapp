package com.scpfoundation.psybotic.app.ui.realUserChat;

import com.scpfoundation.psybotic.app.ui.chatbot.Author;

import java.util.Date;

public class Message implements com.stfalcon.chatkit.commons.models.IMessage {


    private String id;
    private String text;
    private com.scpfoundation.psybotic.app.ui.chatbot.Author author;

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    Date createdAt;

    public void setId(String id) {
        this.id = id;
    }

    public com.scpfoundation.psybotic.app.ui.chatbot.Author getAuthor() {
        return author;
    }

    public void setAuthor(com.scpfoundation.psybotic.app.ui.chatbot.Author author) {
        this.author = author;
    }

    public Message(String text){
        id = "12123";
        this.text = text;
        author = new com.scpfoundation.psybotic.app.ui.chatbot.Author();
        createdAt = new Date();

    }
    @Override
    public String getId() {

        return id;
    }
    public void setText(String text) {

        this.text = text;
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