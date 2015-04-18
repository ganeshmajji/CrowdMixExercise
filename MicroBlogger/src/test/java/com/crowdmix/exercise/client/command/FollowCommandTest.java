package com.crowdmix.exercise.client.command;

import com.crowdmix.exercise.domain.User;
import com.crowdmix.exercise.service.MessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class FollowCommandTest {

    @Mock private MessageService messageService;

    @Test
    public void shouldFollow() {
        // Given
        User follower = new User("foo");
        User followee = new User("bar");

        FollowCommand command = new FollowCommand(follower, followee);

        // When
        Optional<? extends Result> result = command.execute(messageService);

        // Then
        assertFalse(result.isPresent());
        verify(messageService).follow(follower, followee);
    }
}
