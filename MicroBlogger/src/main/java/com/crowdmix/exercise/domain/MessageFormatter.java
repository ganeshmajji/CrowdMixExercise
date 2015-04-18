package com.crowdmix.exercise.domain;

import java.time.Instant;

public interface MessageFormatter {
    String format(User publisher, String message, Instant timestamp);
}
