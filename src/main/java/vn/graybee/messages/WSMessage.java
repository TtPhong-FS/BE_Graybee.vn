package vn.graybee.messages;

import vn.graybee.enums.ChatStatus;

public class WSMessage {

    private String sender;

    private String receiver;

    private String message;

    private String data;

    private ChatStatus status;

    public WSMessage() {
    }

    public WSMessage(String sender, String receiver, String message, String data, ChatStatus status) {
        this.sender = sender;
        this.receiver = receiver;
        this.message = message;
        this.data = data;
        this.status = status;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public ChatStatus getStatus() {
        return status;
    }

    public void setStatus(ChatStatus status) {
        this.status = status;
    }

}
