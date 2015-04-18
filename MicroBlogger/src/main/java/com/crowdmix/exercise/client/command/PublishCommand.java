package com.crowdmix.exercise.client.command;

import com.crowdmix.exercise.domain.User;
import com.crowdmix.exercise.service.MessageService;

import java.util.Optional;

public class PublishCommand implements Command {

    private final User publisher;
    private final String text;

    public PublishCommand(User publisher, String text) {
        this.publisher = publisher;
        this.text = text;
    }

    @Override
    public Optional<? extends Result> execute(MessageService service) {
        service.publish(publisher, text);
        return Optional.empty();
    }
}
