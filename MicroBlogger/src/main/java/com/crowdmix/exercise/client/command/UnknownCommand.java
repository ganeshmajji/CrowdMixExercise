package com.crowdmix.exercise.client.command;

import com.crowdmix.exercise.service.MessageService;

import java.util.Optional;

public class UnknownCommand implements Command {

    private final String command;

    public UnknownCommand(String command) {
        this.command = command;
    }

    @Override
    public Optional<? extends Result> execute(MessageService service) {
        return Optional.of(() -> "Unknown command: " + command);
    }
}
