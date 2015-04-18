package com.crowdmix.exercise.client.command;

import com.crowdmix.exercise.client.OutputFormatter;
import com.crowdmix.exercise.domain.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DefaultCommandFactory implements CommandFactory {

    private static final Pattern pattern = Pattern.compile("^(\\w+)\\s*(\\S*)\\s*(.*)$");

    private final OutputFormatter formatter;

    public DefaultCommandFactory(OutputFormatter formatter) {
        this.formatter = formatter;
    }

    @Override
    public Command create(String command) {
        Matcher matcher = pattern.matcher(command.trim());
        if (matcher.matches()) {
            User user = new User(matcher.group(1));
            switch (matcher.group(2)) {
                case "->": return new PublishCommand(user, matcher.group(3));
                case "": return new TimelineCommand(user, formatter);
                case "follows": return new FollowCommand(user, new User(matcher.group(3)));
                case "wall": return new WallCommand(user, formatter);
                default: break;
            }
        }
        return new UnknownCommand(command);
    }
}
