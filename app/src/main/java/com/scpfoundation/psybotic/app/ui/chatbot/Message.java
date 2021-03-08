package com.scpfoundation.psybotic.app.ui.chatbot;

import java.util.Date;

import static java.lang.Math.random;

public class Message implements com.stfalcon.chatkit.commons.models.IMessage {


    private String id;
    private String text;
    private Author author;

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    Date createdAt;

    public void setId(String id) {
        this.id = id;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Message(String text){
        id = "12123";
        this.text = text;
        author = new Author();
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