package main

import (
	"fmt"
	"os"
	"strconv"
	"strings"

	aoc_write "github.com/tim117/go-aoc-write-answers"
)

func main() {
	input, err := os.ReadFile("input.txt")
	if (err) != nil {
		fmt.Printf("There was an error reading the input.txt file: %v.\n", err)
		return
	}
	lines := strings.Split(string(input), "\n")
	amounts := getElfTotals(lines)
	top, second, third := getTopElfs(amounts)
	answer1 := fmt.Sprint(top)
	answer2 := fmt.Sprint(top + second + third)
	if err := aoc_write.WriteAnswers(answer1, answer2); err != nil {
		fmt.Printf("Unable to write the answers for day 1: %s.\n", err)
	}
}

func getElfTotals(allCalories []string) []int {
	elfCalories := []int{}
	currentElfCalories := 0
	for _, calories := range allCalories {
		if strings.TrimSpace(calories) == "" {
			elfCalories = append(elfCalories, currentElfCalories)
			currentElfCalories = 0
			continue
		}

		intCalories, _ := strconv.Atoi(calories)
		currentElfCalories += intCalories
	}
	return elfCalories
}

func getTopElfs(elfCalories []int) (int, int, int) {
	top := -1
	second := -1
	third := -1
	for _, calories := range elfCalories {
		if calories > top {
			third = second
			second = top
			top = calories
			continue
		}
		if calories > second {
			third = second
			second = calories
			continue
		}
		if calories > third {
			third = calories
		}
	}
	return top, second, third
}
