package aoc.app;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Queue;

class FileSystem {
    public static int MAX_SPACE = 70000000;

    private Folder base;

    FileSystem(List<String> instructions) {
        base = Folder.create("/");
        buildFileSystem(new LinkedList<>(instructions));
    }

    public Folder base() {
        return base;
    };

    public int size() {
        return base().size();
    }

    private void buildFileSystem(Queue<String> instructions) {
        Folder currentFolder = base;
        while (!instructions.isEmpty()) {
            String instruction = instructions.remove();
            Optional<Command> maybeCommand = Command.fromString(instruction);
            if (maybeCommand.isEmpty()) {
                continue;
            }
            Command command = maybeCommand.get();
            if (command.type() == CommandType.CD) {
                currentFolder = goToDirectory(currentFolder, command.arguments().get(0));
            }
            if (command.type() == CommandType.LS) {
                readFileList(currentFolder, instructions);
            }
        }
    }

    private void readFileList(Folder folder, Queue<String> instructions) {
        while (!instructions.isEmpty()) {
            String instruction = instructions.peek();
            if (Command.isCommand(instruction)) {
                break;
            }
            instructions.remove();

            String[] fileParts = instruction.split(" ");
            if (fileParts[0].equals("dir")) {
                folder.putFolder(Folder.create(fileParts[1], folder));
                continue;
            }
            folder.putFile(File.builder().name(fileParts[1]).size(Integer.parseInt(fileParts[0])));
        }

        if (folder.parent().isPresent()) {
            Folder parent = folder.parent().get();
            parent.putFolder(folder);
        } else {
            base = folder;
        }
    }

    private Folder goToDirectory(Folder currentFolder, String folderName) {
        if (folderName.equals("/")) {
            return base;
        }
        if (folderName.equals("..")) {
            if (currentFolder.parent().isPresent()) {
                return currentFolder.parent().get();
            }
            return base;
        }
        return currentFolder.getFolder(folderName).get();
    }

    public String toString() {
        return base.toString();
    }
}
