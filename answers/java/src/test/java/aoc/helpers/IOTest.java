package aoc.helpers;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Scanner;

class MessageUtilsTest {
    @Test
    void writeAnswers_writesToFile() throws IOException {
        String answer1 = "5094";
        String answer2 = "world";

        IO.writeAnswers(answer1, answer2);
        String actual = readFile("answers.txt");

        String expected = IO.formatAnswers(answer1, answer2);
        assertEquals(expected, actual);
    }

    @Test
    void readInput_readsFileSuccessfully() throws IOException, URISyntaxException {
        List<String> actual = IO.readInput();

        List<String> expected = List.of("hello ", "", "", "world");
        assertEquals(expected, actual);
    }

    @Test
    void readInput_fileNotExists_throwsArgumentException() throws IOException, URISyntaxException {
        String fileName = "does-not-exist";
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, () -> IO.readResource(fileName));

        assertEquals(true,
                exception.getMessage().contains(String.format("named \"%s\" exists", fileName)));
    }

    public void writeFile(String fileName, String contents) throws IOException {
        File file = new File(fileName);
        file.createNewFile();
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(contents);
        }
    }

    public String readFile(String fileName) throws FileNotFoundException {
        StringBuilder contents = new StringBuilder();
        File file = new File(fileName);
        try (Scanner reader = new Scanner(file)) {
            while (reader.hasNextLine()) {
                contents.append(reader.nextLine()).append("\n");
            }
        }

        return contents.toString();
    }
}
