
package com.scpfoundation.psybotic.app.ui.chatbot;

import com.stfalcon.chatkit.commons.models.IUser;

import java.util.Date;

public class Message implements com.stfalcon.chatkit.commons.models.IMessage {


    String id;
    String text;
    Author author;
    Date createdAt;

    public Message(){
        id = "1232";
        text = "pol";
        author = new Author();
        createdAt = new Date();
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