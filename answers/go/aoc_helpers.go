package write_aoc_answers

import (
	"fmt"
	"os"
	"strings"
)

func WriteAnswers(first, second string) error {
	output := formatAnswers(first, second)
	err := os.WriteFile("answers.txt", []byte(output), 0666)
	if err != nil {
		return err
	}

	return nil
}

func ReadInput() ([]string, error) {
	output, err := os.ReadFile("input.txt")
	if err != nil {
		return nil, err
	}
	return strings.Split(string(output), "\n"), nil
}

func formatAnswers(first, second string) string {
	return fmt.Sprintf("Answer 1:\n%s\n\nAnswer 2:\n%s\n", first, second)
}
