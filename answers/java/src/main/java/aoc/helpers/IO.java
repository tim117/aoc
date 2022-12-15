package aoc.helpers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.net.URISyntaxException;
import java.net.URL;

public final class IO {
    private static Reader reader = new Reader();

    public static List<String> readInput() throws FileNotFoundException, URISyntaxException {
        return reader.readFile("input.txt");
    }

    public static List<String> readResource(String fileName)
            throws FileNotFoundException, URISyntaxException {
        return reader.readFile(fileName);
    }

    private static class Reader {
        private File getFileFromResources(String fileName) throws URISyntaxException {
            ClassLoader loader = getClass().getClassLoader();
            URL resource = loader.getResource(fileName);
            if (resource == null) {
                throw new IllegalArgumentException(
                        String.format("No file named \"%s\" exists.", fileName));
            }
            return new File(resource.toURI());
        }

        public List<String> readFile(String fileName)
                throws FileNotFoundException, URISyntaxException {
            List<String> lines = new ArrayList<>();
            File inputTxt = getFileFromResources(fileName);
            try (Scanner reader = new Scanner(inputTxt)) {
                while (reader.hasNextLine()) {
                    lines.add(reader.nextLine());
                }
            }
            return lines;
        }
    }

    public static void writeAnswers(String answer1, String answer2) throws IOException {
        File answersTxt = new File("answers.txt");
        answersTxt.createNewFile();
        try (FileWriter writer = new FileWriter(answersTxt)) {
            writer.write(formatAnswers(answer1, answer2));
        }
    }

    public static String formatAnswers(String answer1, String answer2) {
        return new StringBuilder().append("Answer 1:\n").append(answer1).append("\n\nAnswer 2:\n")
                .append(answer2).append("\n").toString();
    }
}
