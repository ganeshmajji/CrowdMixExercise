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
public class PublishCommandTest {

    @Mock private MessageService messageService;

    @Test
    public void shouldPublish() {
        // Given
        User publisher = new User("foo");
        String message = "hello, world!";

        PublishCommand command = new PublishCommand(publisher, message);

        // When
        Optional<? extends Result> result = command.execute(messageService);

        // Then
        assertFalse(result.isPresent());
        verify(messageService).publish(publisher, message);
    }
}
