package com.company.smirnov.common;

import java.io.Serializable;
import java.time.LocalDateTime;


import static java.util.Objects.requireNonNull;

public class Message implements Serializable {
    private LocalDateTime timeOfSending;
    private String text;
    private String sender;

    public Message(String sender) {
        this.sender = requireNonNull(sender);
    }

    public LocalDateTime getTimeOfSending() {
        return timeOfSending;
    }

    public String getText() {
        return text;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setTimeOfSending(LocalDateTime timeOfSending) {
        this.timeOfSending = timeOfSending;
    }

    public void setText(String text) {
        this.text = text;
    }
}

