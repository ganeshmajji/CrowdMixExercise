package com.crowdmix.exercise;

import com.crowdmix.exercise.client.ConsoleApplication;
import com.crowdmix.exercise.client.OutputFormatter;
import com.crowdmix.exercise.client.command.CommandFactory;
import com.crowdmix.exercise.client.command.DefaultCommandFactory;
import com.crowdmix.exercise.service.InMemoryMessageService;
import com.crowdmix.exercise.service.MessageService;

import java.time.Clock;

public class Launcher {

    public static void main(String[] args) {
        Clock clock = Clock.systemUTC();
        MessageService service = new InMemoryMessageService(clock);
        OutputFormatter formatter = new OutputFormatter(clock);
        CommandFactory commandFactory = new DefaultCommandFactory(formatter);

        new ConsoleApplication(service, commandFactory).run();
    }
}
