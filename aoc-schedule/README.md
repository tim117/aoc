# create-advent-schedule

`create-advent-schedule` is a cli tool to create a randomized schedule for you to complete each day of advent of code. This is designed to help you get more familiar with different programming languages by challenging you to solve each days problem with a different programming language.

## Usage

```
create-advent-schedule -l typescript rust go c++ elixir c# -f schedule.txt
```

## Arguments

| Option    | Alias | Type   | Description                                             | Default        |
| --------- | ----- | ------ | ------------------------------------------------------- | -------------- |
| file-path | f     | string | The output destination for the schedule.                | `schedule.txt` |
| languages | l     | list   | The list of languages to use to populate your schedule. | `[typescript]` |
