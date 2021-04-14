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
    private boolean replied;
    private Date sendingDate;
    private Date replyDate;
    private ImageView imageView;

    @Override
    public String toString() {
        return "Notification{" +
                "notificationId='" + id + '\'' +
                ", userId='" + userId + '\'' +
                ", textHeader='" + textHeader + '\'' +
                ", text='" + text + '\'' +
                ", status=" + status +
                ", reply=" + replied +
                ", sendingDate=" + sendingDate +
                ", replyDate=" + replyDate +
                '}';
    }

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

    public boolean isReplied() {
        return replied;
    }

    public void setReplied(boolean replied) {
        this.replied = replied;
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
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Notification)) return false;
        Notification that = (Notification) o;
        return isStatus() == that.isStatus() && isReplied() == that.isReplied() && Objects.equals(getId(), that.getId()) && Objects.equals(getUserId(), that.getUserId()) && Objects.equals(getTextHeader(), that.getTextHeader()) && Objects.equals(getText(), that.getText()) && Objects.equals(getSendingDate(), that.getSendingDate()) && Objects.equals(getReplyDate(), that.getReplyDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getUserId(), getTextHeader(), getText(), isStatus(), isReplied(), getSendingDate(), getReplyDate());
    }

}
