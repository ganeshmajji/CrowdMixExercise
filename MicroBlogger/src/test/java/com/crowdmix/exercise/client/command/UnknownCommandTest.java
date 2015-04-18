package com.crowdmix.exercise.client.command;

import com.crowdmix.exercise.client.OutputFormatter;
import com.crowdmix.exercise.service.MessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verifyZeroInteractions;

@RunWith(MockitoJUnitRunner.class)
public class UnknownCommandTest {

    @Mock private MessageService messageService;
    @Mock private OutputFormatter outputFormatter;

    @Test
    public void shouldDoNothing() {
        // Given
        UnknownCommand command = new UnknownCommand("foo likes bar");

        // When
        Optional<? extends Result> result = command.execute(messageService);

        // Then
        assertTrue(result.isPresent());
        assertThat(result.get().asString(), is("Unknown command: foo likes bar"));

        verifyZeroInteractions(messageService);
    }
}
