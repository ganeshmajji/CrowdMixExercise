package com.crowdmix.exercise.client;

import com.crowdmix.exercise.domain.Message;
import com.crowdmix.exercise.domain.User;
import org.junit.Test;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

import static java.lang.System.lineSeparator;
import static java.time.ZoneId.systemDefault;
import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class OutputFormatterTest {

    private final Instant now = Instant.now();
    private final Clock clock = Clock.fixed(now, systemDefault());

    private final OutputFormatter formatter = new OutputFormatter(clock);

    @Test
    public void shouldFormatMessagesAsString() {
        // Given
        List<Message> messages = asList(
                new Message(new User("foo"), "hello, world!", now.minus(Duration.ofDays(3))),
                new Message(new User("bar"), "good morning..", now.minus(Duration.ofDays(1))),
                new Message(new User("foo"), "i love this stuff :D", now.minus(Duration.ofHours(5))),
                new Message(new User("foo"), "~feeling high~", now.minus(Duration.ofHours(1))),
                new Message(new User("baz"), "hungry as hell :(", now.minus(Duration.ofMinutes(15))),
                new Message(new User("baz"), "hmm.. yummy curry :P", now.minus(Duration.ofMinutes(1))),
                new Message(new User("foo"), "need some more..", now.minusSeconds(10)),
                new Message(new User("bar"), "WFH", now.minusSeconds(1))
        );

        // When
        String result = formatter.format(messages);

        // Then
        assertThat(result, is(
                "foo -> hello, world! (3 days ago)" + lineSeparator() +
                "bar -> good morning.. (1 day ago)" + lineSeparator() +
                "foo -> i love this stuff :D (5 hours ago)" + lineSeparator() +
                "foo -> ~feeling high~ (1 hour ago)" + lineSeparator() +
                "baz -> hungry as hell :( (15 minutes ago)" + lineSeparator() +
                "baz -> hmm.. yummy curry :P (1 minute ago)" + lineSeparator() +
                "foo -> need some more.. (10 seconds ago)" + lineSeparator() +
                "bar -> WFH (1 second ago)"
        ));
    }

    @Test
    public void shouldFormatSingleMessage() {
        // Given
        List<Message> messages = asList(new Message(new User("foo"), "knock, knock!", now));

        // When
        String result = formatter.format(messages);

        // Then
        assertThat(result, is("foo -> knock, knock! (0 seconds ago)"));
    }

    @Test
    public void shouldFormatEmptyMessageList() {
        assertThat(formatter.format(emptyList()), is(""));
    }
}
