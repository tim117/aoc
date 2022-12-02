package write_aoc_answers

import (
	"os"
	"testing"
)

func TestWrite(t *testing.T) {
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

// Reads the output from a file as a string.
func readAnswers() (string, error) {
	output, err := os.ReadFile("answers.txt")
	if err != nil {
		return "", err
	}
	return string(output), nil
}
