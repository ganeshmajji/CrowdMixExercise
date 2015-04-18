package com.crowdmix.exercise.client.command;

import com.crowdmix.exercise.client.OutputFormatter;
import com.crowdmix.exercise.domain.Message;
import com.crowdmix.exercise.domain.User;
import com.crowdmix.exercise.service.MessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import static java.time.Instant.now;
import static java.util.Arrays.asList;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class TimelineCommandTest {

    @Mock private MessageService messageService;
    @Mock private OutputFormatter outputFormatter;

    @Test
    public void shouldGetTimeline() {
        // Given
        User user = new User("foo");
        List<Message> timeline = asList(new Message(user, "hello, world!", now()));

        given(messageService.getTimeline(user)).willReturn(timeline);
        given(outputFormatter.format(timeline)).willReturn("formatted timeline");

        TimelineCommand command = new TimelineCommand(user, outputFormatter);

        // When
        Optional<? extends Result> result = command.execute(messageService);

        // Then
        assertTrue(result.isPresent());
        assertThat(result.get().asString(), is("formatted timeline"));
    }
}
