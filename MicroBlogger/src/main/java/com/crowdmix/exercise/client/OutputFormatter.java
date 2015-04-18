package com.crowdmix.exercise.client;

import com.crowdmix.exercise.domain.Message;
import com.crowdmix.exercise.domain.MessageFormatter;
import com.crowdmix.exercise.domain.User;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;
import java.util.List;

import static java.lang.System.lineSeparator;
import static java.util.stream.Collectors.joining;

public class OutputFormatter implements MessageFormatter {

    private final Clock clock;

    public OutputFormatter(Clock clock) {
        this.clock = clock;
    }

    public String format(List<Message> messages) {
        return messages.stream().map(message -> message.toString(this)).collect(joining(lineSeparator()));
    }

    @Override
    public String format(User publisher, String message, Instant timestamp) {
        return publisher.getName() + " -> " + message + " (" + describe(timestamp) + ")";
    }

    private String describe(Instant start) {
        Duration duration = Duration.between(start, clock.instant());
        if (duration.toDays() > 0) return describe(duration.toDays(), "day");
        if (duration.toHours() > 0) return describe(duration.toHours(), "hour");
        if (duration.toMinutes() > 0) return describe(duration.toMinutes(), "minute");
        return describe(duration.getSeconds(), "second");
    }

    private String describe(long count, String unit) {
        return count + " " + unit + (count == 1 ? " ago" : "s ago");
    }
}
