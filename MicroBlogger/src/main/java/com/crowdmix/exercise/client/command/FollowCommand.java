package com.crowdmix.exercise.client.command;

import com.crowdmix.exercise.domain.User;
import com.crowdmix.exercise.service.MessageService;

import java.util.Optional;

public class FollowCommand implements Command {

    private final User follower;
    private final User followee;

    public FollowCommand(User follower, User followee) {
        this.follower = follower;
        this.followee = followee;
    }

    @Override
    public Optional<? extends Result> execute(MessageService service) {
        service.follow(follower, followee);
        return Optional.empty();
    }
}
