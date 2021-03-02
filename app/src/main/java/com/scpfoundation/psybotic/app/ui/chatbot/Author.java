package com.scpfoundation.psybotic.app.ui.chatbot;

public class Author implements IUser {

    String id;
    String name;
    String avatar;

    public Author(){
        id = null;
        name = null;
        avatar = null;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getAvatar() {
        return avatar;
    }
}