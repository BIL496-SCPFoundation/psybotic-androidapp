package com.scpfoundation.psybotic.app.data;


import java.util.Date;
import java.util.Objects;

public class Notification {
    private String notificationId;
    private String userId;
    private String textHeader;
    private String text;
    private boolean status;
    private boolean reply;
    private Date sendingDate;
    private Date replyDate;

    @Override
    public String toString() {
        return "Notification{" +
                "notificationId='" + notificationId + '\'' +
                ", userId='" + userId + '\'' +
                ", textHeader='" + textHeader + '\'' +
                ", text='" + text + '\'' +
                ", status=" + status +
                ", reply=" + reply +
                ", sendingDate=" + sendingDate +
                ", replyDate=" + replyDate +
                '}';
    }


    public String getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
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
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Notification)) return false;
        Notification that = (Notification) o;
        return isStatus() == that.isStatus() && isReply() == that.isReply() && Objects.equals(getNotificationId(), that.getNotificationId()) && Objects.equals(getUserId(), that.getUserId()) && Objects.equals(getTextHeader(), that.getTextHeader()) && Objects.equals(getText(), that.getText()) && Objects.equals(getSendingDate(), that.getSendingDate()) && Objects.equals(getReplyDate(), that.getReplyDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getNotificationId(), getUserId(), getTextHeader(), getText(), isStatus(), isReply(), getSendingDate(), getReplyDate());
    }

}
