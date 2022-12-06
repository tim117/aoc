defmodule AocHelpers do
  @moduledoc """
  Helpers for advent of code problems solved with elixir.
  """

  @doc """
  Writes answers to a file named `answers.txt` in the current working directory.

  ## Examples

      iex> AocHelpers.write_answers!("answer 1", "answer 2")
      :ok

  """
  def write_answers!(answer1, answer2) do
    File.write!("answers.txt", format_answers(answer1, answer2))
    :ok
  end

  @doc """
  Reads lines from a file named `input.txt` in the current working directory.
  """
  def read_input!() do
    File.stream!("input.txt") |> Stream.map(&String.trim_trailing/1)
  end

  @doc """
  Formats the answers to be displayed in a readable way.
  """
  def format_answers(answer1, answer2) do
    ["Answer 1:\n", answer1, "\n\nAnswer 2:\n", answer2, "\n"]
  end
end
