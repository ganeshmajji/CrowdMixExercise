package com.crowdmix.exercise.service;

import com.crowdmix.exercise.domain.Message;
import com.crowdmix.exercise.domain.User;

import java.util.List;

public interface MessageService {
    void publish(User publisher, String text);
    void follow(User follower, User followee);
    List<Message> getTimeline(User user);
    List<Message> getWall(User user);
}
