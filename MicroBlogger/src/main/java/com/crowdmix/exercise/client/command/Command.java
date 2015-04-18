package com.crowdmix.exercise.client.command;

import com.crowdmix.exercise.service.MessageService;

import java.util.Optional;

public interface Command {
    Optional<? extends Result> execute(MessageService service);
}
