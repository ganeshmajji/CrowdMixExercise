package com.crowdmix.exercise.client;

import com.crowdmix.exercise.client.command.Command;
import com.crowdmix.exercise.client.command.CommandFactory;
import com.crowdmix.exercise.client.command.Result;
import com.crowdmix.exercise.service.MessageService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.*;
import java.util.Optional;

import static java.lang.System.lineSeparator;
import static java.nio.charset.Charset.defaultCharset;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;

@RunWith(MockitoJUnitRunner.class)
public class ConsoleApplicationTest {

    private static final String prompt = "> ";

    private final InputStream systemIn = System.in;
    private final PrintStream systemOut = System.out;

    @Mock CommandFactory commandFactory;
    @Mock MessageService messageService;

    private ConsoleApplication application;

    @Before
    public void setUp() throws Exception {
        application = new ConsoleApplication(messageService, commandFactory);
    }

    @After
    public void tearDown() throws Exception {
        System.setIn(systemIn);
        System.setOut(systemOut);
    }

    @Test
    public void shouldInteractivelyExecuteCommands() throws UnsupportedEncodingException {
        // Given
        String input = "command one" + lineSeparator() + "command two" + lineSeparator();

        ByteArrayInputStream inputStream = new ByteArrayInputStream(input.getBytes(defaultCharset()));
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        System.setIn(inputStream);
        System.setOut(new PrintStream(outputStream));

        Command commandOne = mock(Command.class);
        Command commandTwo = mock(Command.class);

        given(commandOne.execute(messageService)).willAnswer(invocation -> Optional.empty());
        given(commandTwo.execute(messageService)).willAnswer(invocation -> Optional.of((Result) () -> "output from command two"));

        given(commandFactory.create("command one")).willReturn(commandOne);
        given(commandFactory.create("command two")).willReturn(commandTwo);

        // When
        application.run();

        // Then
        assertThat(outputStream.toString(), is(
                prompt +
                prompt +
                "output from command two" + lineSeparator() +
                prompt
        ));
    }
}
