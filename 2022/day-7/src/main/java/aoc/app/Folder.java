package aoc.app;

import static com.google.common.collect.ImmutableList.toImmutableList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import com.google.common.collect.ImmutableList;

/** A mutable representation of a folder. */
class Folder {
    private String name;
    private Optional<Folder> parent;
    private Map<String, Folder> folders;
    private Map<String, File> files;

    public static Folder create(String name) {
        return new Folder(name, Optional.empty());
    }

    public static Folder create(String name, Folder parent) {
        return new Folder(name, Optional.of(parent));
    }

    private Folder(String name, Optional<Folder> parent) {
        this.name = name;
        this.parent = parent;
        folders = new HashMap<>();
        files = new HashMap<>();
    }

    /** The name of the folder. */
    String name() {
        return name;
    }

    /** The {@link Folder} directly underneath the {@link Folder} */
    Optional<Folder> parent() {
        return parent;
    }

    /**
     * An ordered {@link List} of the parents of the {@link Folder}. The last item is the direct
     * parent and the first item is the base {@link Folder}.
     */
    ImmutableList<Folder> folders() {
        return folders.entrySet().stream().map(folderSet -> folderSet.getValue())
                .collect(toImmutableList());
    }

    Folder putFolder(Folder folder) {
        folders.put(folder.name, folder);
        return this;
    }

    /** A list of files that the folder directly contains. */
    ImmutableList<File> files() {
        return files.entrySet().stream().map(fileSet -> fileSet.getValue())
                .collect(toImmutableList());
    }

    /** Adds a file to the folder. */
    Folder putFile(File.Builder file) {
        files.put(file.name(), file.build());
        return this;
    }

    /** Gets a file or empty if the file does not exist. */
    Optional<File> getFile(String name) {
        if (files.containsKey(name)) {
            return Optional.of(files.get(name));
        }
        return Optional.empty();
    }

    /** Gets a folder or empty if the folder does not exist. */
    Optional<Folder> getFolder(String name) {
        if (folders.containsKey(name)) {
            return Optional.of(folders.get(name));
        }
        return Optional.empty();
    }

    /** Gets a file or empty if the file does not exist. */
    boolean contains(String name) {
        return getFolder(name).isPresent() || getFile(name).isPresent();
    }

    /** Check if the name is an existing file. */
    boolean isFile(String name) {
        return getFile(name).isPresent();
    }

    /** Check if the name is an existing folder. */
    boolean isFolder(String name) {
        return getFolder(name).isPresent();
    }

    /**
     * The size of the folder.
     * 
     * Includes all subfolders.
     */
    public int size() {
        return folders().stream().map(folder -> folder.size()).reduce(0,
                (current, next) -> current + next)
                + files().stream().map(file -> file.size()).reduce(0,
                        (current, next) -> current + next);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        // builder.append("Folder{name=").append(name()).append(",parent=")
        // .append(parent().isPresent() ? parent().get().name() : "null").append(",files=[")
        // .append(files().size()).append("],folders=[").append(String.join(",",
        // folders().stream().map(folder -> folder.toString()).toList()))
        // .append("]}");
        builder.append("- ").append(name).append(" ").append(size()).append("\n");
        addContents(builder, this, Optional.of(0));

        return builder.toString();
    }

    private void addContents(StringBuilder builder, Folder current, Optional<Integer> depth) {
        String prepend = "  ";
        if (depth.isPresent()) {
            int currentDepth = 0;
            while (currentDepth < depth.get()) {
                prepend += "  ";
                currentDepth++;
            }
        }
        for (Folder folder : current.folders()) {
            builder.append(prepend).append("- ").append(folder.name()).append(" (dir,")
                    .append(folder.size()).append(")\n");
            addContents(builder, folder, Optional.of(depth.orElse(0) + 1));
        }
        for (File file : current.files()) {
            builder.append(prepend).append("- ").append(file.name()).append(" (file,")
                    .append(file.size()).append(")\n");
        }
    }
}
