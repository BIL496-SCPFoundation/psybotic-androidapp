package com.scpfoundation.psybotic.app.ui.psychologychat;


import com.stfalcon.chatkit.commons.models.IUser;

public class Author implements IUser {


    String id;
    String name;
    String avatar;

    public Author(){
        id = "1231";
        name = "adavar";
        avatar = "dfdf";
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