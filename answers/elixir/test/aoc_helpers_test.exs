defmodule AocHelpersTest do
  use ExUnit.Case
  doctest AocHelpers

  test "writes output to answers.txt" do
    answer1 = "504"
    answer2 = "024"

    AocHelpers.write_answers!(answer1, answer2)
    actual = File.read!("answers.txt")

    expected = AocHelpers.format_answers(answer1, answer2)
    assert actual == expected |> Enum.join()
  end

  test "reads input from input.txt" do
    input = ["This \n", " \n", " is a test\n", "\n", "\n"]
    File.write!("input.txt", input |> Enum.join())

    actual = AocHelpers.read_input!() |> Enum.take_every(1)

    expected = input |> Enum.map(&String.trim_trailing/1)
    assert actual == expected
  end
end
