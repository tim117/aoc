package aoc.app;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import aoc.helpers.IO;

public class App {
    private static int SPACE_NEEDED = 30000000;

    public static void main(String[] args) throws IOException, URISyntaxException {
        List<String> input = IO.readInput();
        FileSystem fs = new FileSystem(input);

        String answer1 = answer1(fs);
        String answer2 = answer2(fs);
        IO.writeAnswers(answer1, answer2);
    }

    public static String answer1(FileSystem fs) {
        List<Folder> flatFolders = findAllFolders(fs);
        List<Folder> smallFolders = foldersSmallerThan(100000, flatFolders);
        int size = sizeSum(smallFolders);
        return Integer.toString(size);
    }

    private static List<Folder> findAllFolders(FileSystem fs) {
        List<Folder> folders = new ArrayList<>();
        findFolders(fs.base(), folders);
        return folders;
    }

    private static void findFolders(Folder current, List<Folder> folders) {
        folders.add(current);
        current.folders().forEach(folder -> findFolders(folder, folders));
    }

    private static List<Folder> foldersSmallerThan(int size, List<Folder> folders) {
        return folders.stream().filter(folder -> folder.size() <= size).toList();
    }

    private static int sizeSum(List<Folder> list) {
        return list.stream().map(folder -> folder.size()).reduce(0,
                (current, next) -> current + next);
    }

    public static String answer2(FileSystem fs) {
        int remainingSpace = FileSystem.MAX_SPACE - fs.size();
        int spaceToFree = SPACE_NEEDED - remainingSpace;
        List<Folder> allFolders = findAllFolders(fs);
        List<Folder> largeFolders = foldersLargerThan(spaceToFree, allFolders);
        Folder folderToDelete = getSmallestFolder(largeFolders);
        return Integer.toString(folderToDelete.size());
    }

    private static List<Folder> foldersLargerThan(int size, List<Folder> folders) {
        return folders.stream().filter(folder -> folder.size() >= size).toList();
    }

    private static Folder getSmallestFolder(List<Folder> folders) {
        if (folders.isEmpty()) {
            throw new IllegalArgumentException("Folders must include at least one folder.");
        }
        Folder smallest = folders.get(0);
        for (Folder folder : folders) {
            int size = folder.size();
            if (size < smallest.size()) {
                smallest = folder;
            }
        }
        return smallest;
    }
}
