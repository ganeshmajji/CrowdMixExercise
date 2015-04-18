package com.crowdmix.exercise.domain;

import java.time.Instant;

public class Message {

    private final User publisher;
    private final String text;
    private final Instant timestamp;

    public Message(User publisher, String text, Instant timestamp) {
        this.publisher = publisher;
        this.text = text;
        this.timestamp = timestamp;
    }

    public User getPublisher() {
        return publisher;
    }

    public String getText() {
        return text;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public String toString(MessageFormatter formatter) {
        return formatter.format(publisher, text, timestamp);
    }
}
