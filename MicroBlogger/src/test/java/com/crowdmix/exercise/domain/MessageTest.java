package com.crowdmix.exercise.domain;

import org.junit.Test;

import java.time.Instant;

import static java.time.Instant.now;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

public class MessageTest {

    @Test
    public void construction() {
        // Given
        User publisher = new User("Foo");
        String text = "Hello, world!";
        Instant timestamp = now();

        // When
        Message message = new Message(publisher, text, timestamp);

        // Then
        assertThat(message.getPublisher(), is(publisher));
        assertThat(message.getText(), is(text));
        assertThat(message.getTimestamp(), is(timestamp));
    }

    @Test
    public void shouldFormatUsingMessageFormatter() {
        // Given
        User user = new User("foo");
        String text = "Hello, world!";
        Instant timestamp = now();

        MessageFormatter formatter = mock(MessageFormatter.class);
        given(formatter.format(user, text, timestamp)).willReturn("formatted message");

        Message message = new Message(user, text, timestamp);

        // When
        String formattedMessage = message.toString(formatter);

        // Then
        assertThat(formattedMessage, is("formatted message"));
    }
}
