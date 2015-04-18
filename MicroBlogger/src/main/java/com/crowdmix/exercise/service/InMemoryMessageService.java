package com.crowdmix.exercise.service;

import com.crowdmix.exercise.domain.Message;
import com.crowdmix.exercise.domain.User;

import java.time.Clock;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

import static java.util.Collections.*;
import static java.util.stream.Collectors.toList;

public class InMemoryMessageService implements MessageService {

    private final Clock clock;

    private final Map<User, List<Message>> messages;
    private final Map<User, Set<User>> subscriptions;

    public InMemoryMessageService(Clock clock) {
        this.clock = clock;
        messages = new ConcurrentHashMap<>();
        subscriptions = new ConcurrentHashMap<>();
    }

    @Override
    public void publish(User publisher, String text) {
       messages.computeIfAbsent(publisher, key -> synchronizedList(new ArrayList<Message>())).add(new Message(publisher, text, Instant.now(clock)));
   }

    @Override
    public void follow(User follower, User followee) {
        subscriptions.computeIfAbsent(follower, key -> newSetFromMap(new ConcurrentHashMap<User, Boolean>())).add(followee);
    }

    @Override
    public List<Message> getTimeline(User user) {
        return unmodifiableList(messages.getOrDefault(user, emptyList()));
    }

    @Override
    public List<Message> getWall(User user) {
        Set<User> wallUsers = subscriptions.getOrDefault(user, new HashSet<>());
        wallUsers.add(user); // Self

        return wallUsers.stream()
                .map(this::getTimeline)
                .flatMap(messages -> messages.stream())
                .sorted((left, right) -> left.getTimestamp().compareTo(right.getTimestamp()))
                .collect(toList());
    }
}
