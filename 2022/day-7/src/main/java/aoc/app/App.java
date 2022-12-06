package aoc.app;

import java.io.IOException;
import java.util.List;

import aoc.helpers.IO;

public class App {
    public static void main(String[] args) throws IOException {
        List<String> input = IO.readInput();

        String answer1 = answer1(input);
        String answer2 = answer2(input);
        IO.writeAnswers(answer1, answer2);
    }

    public static String answer1(List<String> input) {
        return "";
    }

    public static String answer2(List<String> input) {
        return "";
    }
}
