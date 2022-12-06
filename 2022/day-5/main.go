package main

import (
	"fmt"
	"regexp"
	"sort"
	"strconv"
	"strings"

	aoc_helpers "github.com/tim117/go-aoc-write-answers"
)

func main() {
	input, err := aoc_helpers.ReadInput()
	if err != nil {
		fmt.Printf("There was an error reading the input.txt file: %v.\n", err)
		return
	}

	answer1 := answer1(getInstructions(input))
	answer2 := answer2(getInstructions(input))
	err = aoc_helpers.WriteAnswers(answer1, answer2)
	if err != nil {
		fmt.Printf("Unable to write the answers for day 1: %s.\n", err)
	}
}

type instruction struct {
	from   int
	to     int
	amount int
}

type crate = string

type stack = []crate

func move(stacks map[int]stack, instruction instruction) map[int]stack {
	to := stacks[instruction.to]
	from := stacks[instruction.from]
	cratesToMove := from[len(from)-instruction.amount:]
	numCrateToMove := len(cratesToMove)
	for i := 0; i < numCrateToMove; i++ {
		to = append(to, cratesToMove[numCrateToMove-i-1])
	}
	stacks[instruction.to] = to
	stacks[instruction.from] = from[:len(from)-instruction.amount]
	return stacks
}

func move_all(stacks map[int]stack, instruction instruction) map[int]stack {
	to := stacks[instruction.to]
	from := stacks[instruction.from]
	cratesToMove := from[len(from)-instruction.amount:]
	stacks[instruction.to] = append(to, cratesToMove...)
	stacks[instruction.from] = from[:len(from)-instruction.amount]
	return stacks
}

func getInstructions(input []string) (stacks map[int]stack, instructions []instruction) {
	stacks = map[int]stack{}
	instructions = []instruction{}
	lastCrateLine := 0
	for lineNumber, line := range input {
		// The first line that does not contain '[' has no crates.
		if !strings.Contains(line, "[") {
			lastCrateLine = lineNumber
			break
		}
		crates := crateRowFromString(line)
		stacks = addCratesToStacks(stacks, crates)
	}

	for _, line := range input[lastCrateLine+2:] {
		instructions = append(instructions, parseInstruction(line))
	}
	return
}

func crateRowFromString(line string) (row []string) {
	for index, char := range fmt.Sprintf("  %s  ", line) {
		if (index+1)%4 == 0 {
			row = append(row, string(char))
		}
	}
	return
}

func addCratesToStacks(stacks map[int]stack, crates []string) map[int]stack {
	for index, crate := range crates {
		if crate == " " {
			continue
		}
		if _, exists := stacks[index]; !exists {
			stacks[index] = []string{crate}
			continue
		}
		stacks[index] = append([]string{crate}, stacks[index]...)
	}
	return stacks
}

func parseInstruction(line string) instruction {
	numbers := regexp.MustCompile("[0-9]{1,}")
	values := numbers.FindAllString(line, -1)
	amount, _ := strconv.Atoi(values[0])
	from, _ := strconv.Atoi(values[1])
	to, _ := strconv.Atoi(values[2])
	return instruction{
		amount: amount,
		from:   from - 1,
		to:     to - 1,
	}
}

func answer1(stacks map[int]stack, instructions []instruction) string {
	for _, instruction := range instructions {
		stacks = move(stacks, instruction)
	}
	tops := []string{}
	keys := []int{}
	for key := range stacks {
		keys = append(keys, key)
	}
	sort.Ints(keys)
	for key := range keys {
		stack := stacks[key]
		if len(stack) > 0 {
			tops = append(tops, stack[len(stack)-1])
			continue
		}
		tops = append(tops, " ")
	}
	return strings.Join(tops, "")
}

func answer2(stacks map[int]stack, instructions []instruction) string {
	tops := []string{}
	keys := []int{}
	for key := range stacks {
		keys = append(keys, key)
	}
	sort.Ints(keys)
	for _, instruction := range instructions {
		stacks = move_all(stacks, instruction)
	}
	for key := range keys {
		stack := stacks[key]
		if len(stack) > 0 {
			tops = append(tops, stack[len(stack)-1])
			continue
		}
		tops = append(tops, " ")
	}
	return strings.Join(tops, "")
}
