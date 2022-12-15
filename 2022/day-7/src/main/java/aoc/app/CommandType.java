package aoc.app;

public enum CommandType {
    CD("cd"), LS("ls"), NONE(null);

    String command;

    CommandType(String command) {
        this.command = command;
    }

    public static CommandType fromString(String commandTypeStr) {
        if (commandTypeStr.equals(CommandType.CD.command)) {
            return CD;
        }
        if (commandTypeStr.equals(CommandType.LS.command)) {
            return LS;
        }
        return NONE;
    }
}
