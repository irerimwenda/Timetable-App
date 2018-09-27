package com.example.ireribrian.compscience;

import java.util.Date;

/**
 * Created by ireribrian on 9/26/2018.
 */

public class chatGroup {

    private String MessageText;
    private String messageUser;
    private long messageTime;

    public chatGroup(String messageText, String messageUser) {
        this.MessageText = messageText;
        this.messageUser = messageUser;

        messageTime = new Date().getTime();
    }

    public chatGroup() {
    }

    public String getMessageText() {
        return MessageText;
    }

    public void setMessageText(String messageText) {
        this.MessageText = messageText;
    }

    public String getMessageUser() {
        return messageUser;
    }

    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }

    public long getMessageTime() {
        return messageTime;
    }

    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }
}
