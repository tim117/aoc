package write_aoc_answers

import (
	"os"
	"strings"
	"testing"
)

func TestWriteAnswers(t *testing.T) {
	answer1, answer2 := "532", "543"

	WriteAnswers(answer1, answer2)

	expectedAnswers := formatAnswers(answer1, answer2)
	output, err := readAnswers()
	if err != nil {
		t.Fatalf("Unable to read answers: %s", err)
	}
	if output != expectedAnswers {
		t.Fatalf("Got %s expected %s", output, expectedAnswers)
	}
}

func TestReadInput(t *testing.T) {
	inputTxt := "This is\na\n\nTest"
	os.WriteFile("input.txt", []byte(inputTxt), 0666)

	actual, err := ReadInput()

	if err != nil {
		t.Fatalf("Received an error when trying to read `input.txt`: %s", err)
	}
	if strings.Join(actual, "\n") != inputTxt {
		t.Fatalf("Got %s expected %s", actual, inputTxt)
	}
}

// Reads the output from a file as a string.
func readAnswers() (string, error) {
	output, err := os.ReadFile("answers.txt")
	if err != nil {
		return "", err
	}
	return string(output), nil
}
