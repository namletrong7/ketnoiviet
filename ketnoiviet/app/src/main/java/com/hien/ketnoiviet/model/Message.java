package com.hien.ketnoiviet.model;

import java.io.Serializable;

public class Message implements Serializable {
    private String messageId, message, senderId, imageUrl,recevierName;
    private String  timestamp, senderName;

    public Message() {
    }

    public Message(String message, String senderId, String timestamp,String recevierName,String senderName ) {
        this.message = message;
        this.senderId = senderId;
        this.timestamp = timestamp;
        this.recevierName=recevierName;
        this.senderName=senderName;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getRecevierName() {
        return recevierName;
    }

    public void setRecevierName(String recevierName) {
        this.recevierName = recevierName;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }


    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
