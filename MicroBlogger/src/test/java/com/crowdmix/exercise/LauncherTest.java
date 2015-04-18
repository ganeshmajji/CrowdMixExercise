package com.crowdmix.exercise;

import com.crowdmix.exercise.client.ConsoleApplication;
import com.crowdmix.exercise.client.OutputFormatter;
import com.crowdmix.exercise.client.command.DefaultCommandFactory;
import com.crowdmix.exercise.service.InMemoryMessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.time.Clock;

import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.whenNew;

@RunWith(PowerMockRunner.class)
@PrepareForTest({Launcher.class})
public class LauncherTest {

    @Mock private InMemoryMessageService messageService;
    @Mock private OutputFormatter outputFormatter;
    @Mock private DefaultCommandFactory commandFactory;
    @Mock private ConsoleApplication consoleApplication;

    @Test
    public void shouldRunConsoleApplication() throws Exception {
        // Given
        Clock clock = Clock.systemUTC();

        whenNew(InMemoryMessageService.class).withArguments(clock).thenReturn(messageService);
        whenNew(OutputFormatter.class).withArguments(clock).thenReturn(outputFormatter);
        whenNew(DefaultCommandFactory.class).withArguments(outputFormatter).thenReturn(commandFactory);

        whenNew(ConsoleApplication.class).withArguments(messageService, commandFactory).thenReturn(consoleApplication);

        // When
        Launcher.main(null);

        // Then
        verify(consoleApplication).run();
    }
}
