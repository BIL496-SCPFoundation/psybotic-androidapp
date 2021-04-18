package com.scpfoundation.psybotic.app.data;


import android.widget.ImageView;

import java.util.Date;
import java.util.Objects;

public class Notification {
    private String id;
    private String userId;
    private String textHeader;
    private String text;
    private boolean status;
    private boolean reply;
    private Date sendingDate;
    private Date replyDate;
    private boolean bildiri;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTextHeader() {
        return textHeader;
    }

    public void setTextHeader(String textHeader) {
        this.textHeader = textHeader;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public boolean isReply() {
        return reply;
    }

    public void setReply(boolean reply) {
        this.reply = reply;
    }

    public Date getSendingDate() {
        return sendingDate;
    }

    public void setSendingDate(Date sendingDate) {
        this.sendingDate = sendingDate;
    }

    public Date getReplyDate() {
        return replyDate;
    }

    public void setReplyDate(Date replyDate) {
        this.replyDate = replyDate;
    }

    public boolean isBildiri() {
        return bildiri;
    }

    public void setBildiri(boolean bildiri) {
        this.bildiri = bildiri;
    }
}
