package aoc.app;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableList;

/** A representation of a {@link Command}}. */
@AutoValue
abstract class Command {
    /** The type of the {@link Command}}. */
    abstract CommandType type();

    /** Gets the argument for the {@link Command}}. */
    abstract ImmutableList<String> arguments();

    /**
     * Creates a {@link Command} from a string.
     * 
     * @param commandString The string containing the parts of the command.
     * @return The command parsed from the string.
     */
    public static Optional<Command> fromString(String commandString) {
        if (!isCommand(commandString)) {
            return Optional.empty();
        }
        String[] commandParts = commandString.split(" ");
        List<String> arguments = new ArrayList<>();
        for (int i = 2; i < commandParts.length; ++i) {
            arguments.add(commandParts[i]);
        }
        return Optional.of(new AutoValue_Command.Builder()
                .type(CommandType.fromString(commandParts[1])).arguments(arguments).build());
    }

    /** Checks whether a line is a command. */
    static boolean isCommand(String line) {
        return line.startsWith("$");
    }

    /** Creates a new {@link Builder}. */
    public Builder builder() {
        return new AutoValue_Command.Builder();
    }

    /** Builder for a {@link Command}. */
    @AutoValue.Builder
    public static abstract class Builder {
        abstract Builder type(CommandType commandType);

        abstract Builder arguments(Iterable<String> argument);

        abstract Command build();
    }
}
