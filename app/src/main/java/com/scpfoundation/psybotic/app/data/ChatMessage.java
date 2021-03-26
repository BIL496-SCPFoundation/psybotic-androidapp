package com.scpfoundation.psybotic.app.data;

import java.util.Date;

public class ChatMessage {
    /*
    * "data": {
    "id": "string",
    "receiverId": "string",
    "senderId": "string",
    "message": "string",
    "senderFirstName": "string",
    "senderLastName": "string",
    "date": "2021-03-26T18:55:47.541Z"
  }*/


    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderFirstName() {
        return senderFirstName;
    }

    public void setSenderFirstName(String senderFirstName) {
        this.senderFirstName = senderFirstName;
    }

    public String getSenderLastName() {
        return senderLastName;
    }

    public void setSenderLastName(String senderLastName) {
        this.senderLastName = senderLastName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    private String message;

    public ChatMessage(String message, String senderFirstName, String senderLastName, String date,
                        String receiverId, String senderId) {
        this.message = message;
        this.senderFirstName = senderFirstName;
        this.senderLastName = senderLastName;
        this.date = date;
        this.receiverId = receiverId;
        this.senderId = senderId;
    }

    private String senderFirstName;
    private String senderLastName;
    private String date;
    private String id;
    private String receiverId;
    private String senderId;
    /*variables */


}
