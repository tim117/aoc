package aoc.helpers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class IO {
    public static List<String> readInput() throws FileNotFoundException {
        List<String> lines = new ArrayList<>();
        File inputTxt = new File("input.txt");
        try (Scanner reader = new Scanner(inputTxt)) {
            while (reader.hasNextLine()) {
                lines.add(reader.nextLine());
            }
        }
        return lines;
    }

    public static void writeAnswers(String answer1, String answer2) throws IOException {
        File answersTxt = new File("answers.txt");
        answersTxt.createNewFile();
        try (FileWriter writer = new FileWriter(answersTxt)) {
            writer.write(formatAnswers(answer1, answer2));
        }
    }

    public static String formatAnswers(String answer1, String answer2) {
        return new StringBuilder().append("Answer 1:\n").append(answer1).append("\n\nAnswer 2:\n").append(answer2)
                .append("\n").toString();
    }
}
