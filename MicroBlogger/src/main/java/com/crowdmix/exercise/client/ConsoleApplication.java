package com.crowdmix.exercise.client;

import com.crowdmix.exercise.client.command.Command;
import com.crowdmix.exercise.client.command.CommandFactory;
import com.crowdmix.exercise.service.MessageService;

import java.util.NoSuchElementException;
import java.util.Scanner;

public class ConsoleApplication {

    private static final String prompt = "> ";

    private final MessageService service;
    private final CommandFactory commandFactory;

    public ConsoleApplication(MessageService service, CommandFactory commandFactory) {
        this.service = service;
        this.commandFactory = commandFactory;
    }

    public void run() {
        try (Scanner scanner = new Scanner(System.in)) {
            for(Command command = readCommand(scanner); command != null; command = readCommand(scanner)) {
                command.execute(service).ifPresent(result -> System.out.println(result.asString()));
            }
        }
    }

    private Command readCommand(Scanner scanner) {
        System.out.print(prompt);
        try {
            return commandFactory.create(scanner.nextLine());
        } catch (NoSuchElementException ignored) {
            return null;
        }
    }
}
