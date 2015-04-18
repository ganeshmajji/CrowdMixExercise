package com.crowdmix.exercise.client.command;

import com.crowdmix.exercise.client.OutputFormatter;
import com.crowdmix.exercise.domain.Message;
import com.crowdmix.exercise.domain.User;
import com.crowdmix.exercise.service.MessageService;

import java.util.List;
import java.util.Optional;

public class WallCommand implements Command {

    private final User user;
    private final OutputFormatter formatter;

    public WallCommand(User user, OutputFormatter formatter) {
        this.user = user;
        this.formatter = formatter;
    }

    @Override
    public Optional<? extends Result> execute(MessageService service) {
        List<Message> messages = service.getWall(user);
        return Optional.of(() -> formatter.format(messages));
    }
}
