package com.crowdmix.exercise.client.command;

import com.crowdmix.exercise.client.OutputFormatter;
import com.crowdmix.exercise.domain.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.junit.Assert.assertSame;
import static org.powermock.api.mockito.PowerMockito.*;

@RunWith(PowerMockRunner.class)
@PrepareForTest({DefaultCommandFactory.class})
public class DefaultCommandFactoryTest {

    @Mock private OutputFormatter outputFormatter;
    private CommandFactory factory;

    @Before
    public void setUp() throws Exception {
        factory = new DefaultCommandFactory(outputFormatter);
    }

    @Test
    public void shouldCreatePublishCommand() throws Exception {
        // Given
        User publisher = new User("foo");
        String message = "hello, world!";

        PublishCommand expected = mock(PublishCommand.class);
        whenNew(PublishCommand.class).withArguments(publisher, message).thenReturn(expected);

        // When
        Command actual = factory.create("foo -> hello, world!");

        // Then
        assertSame(expected, actual);
        verifyNew(PublishCommand.class).withArguments(publisher, message);
    }

    @Test
    public void shouldCreateFollowCommand() throws Exception {
        // Given
        User follower = new User("foo");
        User followee = new User("bar");

        FollowCommand expected = mock(FollowCommand.class);
        whenNew(FollowCommand.class).withArguments(follower, followee).thenReturn(expected);

        // When
        Command actual = factory.create("foo follows bar");

        // Then
        assertSame(expected, actual);
        verifyNew(FollowCommand.class).withArguments(follower, followee);
    }

    @Test
    public void shouldCreateTimelineCommand() throws Exception {
        // Given
        User user = new User("foo");

        TimelineCommand expected = mock(TimelineCommand.class);
        whenNew(TimelineCommand.class).withArguments(user, outputFormatter).thenReturn(expected);

        // When
        Command actual = factory.create("foo");

        // Then
        assertSame(expected, actual);
        verifyNew(TimelineCommand.class).withArguments(user, outputFormatter);
    }

    @Test
    public void shouldCreateWallCommand() throws Exception {
        // Given
        User user = new User("foo");

        WallCommand expected = mock(WallCommand.class);
        whenNew(WallCommand.class).withArguments(user, outputFormatter).thenReturn(expected);

        // When
        Command actual = factory.create("foo wall");

        // Then
        assertSame(expected, actual);
        verifyNew(WallCommand.class).withArguments(user, outputFormatter);
    }

    @Test
    public void shouldCreateUnknownCommand() throws Exception {
        // Given
        String invalidCommand = "foo likes bar";

        UnknownCommand expected = mock(UnknownCommand.class);
        whenNew(UnknownCommand.class).withArguments(invalidCommand).thenReturn(expected);

        // When
        Command actual = factory.create(invalidCommand);

        // Then
        assertSame(expected, actual);
        verifyNew(UnknownCommand.class).withArguments(invalidCommand);
    }
}
