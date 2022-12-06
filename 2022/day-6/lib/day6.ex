defmodule Day6 do
  @moduledoc """
  Elixir code for Advent of Code (AOC) Day6.
  """
  alias AocHelpers

  @doc """
  Solves problem 1 and 2 for AOC day 6.
  """
  def main do
    input =
      AocHelpers.read_input!()
      |> Stream.map(&String.trim_trailing/1)
      |> Enum.take(1)

    answer1 = answer1(input)
    answer2 = answer2(input)
    AocHelpers.write_answers!("#{answer1}", "#{answer2}")
  end

  defp answer1(input) do
    {start, _} = find_sequences(input |> Enum.at(0)) |> Enum.at(0)
    # The last character in the sequence is the answer for part 1.
    start + 4
  end

  defp answer2(input) do
    signal = 14
    {start, _} = find_sequences(input |> Enum.at(0), signal) |> Enum.at(0)
    start + signal
  end

  defp find_sequences(txt, signal \\ 4) do
    txt_list = txt |> String.graphemes()

    sequences =
      txt_list
      |> Enum.drop(-signal)
      |> Enum.with_index()
      |> Enum.map(fn {_, i} ->
        next_x = txt_list |> Enum.drop(i) |> Enum.take(signal)

        {i, MapSet.new(next_x)}
      end)
      |> Enum.filter(fn {_, set} ->
        String.length(Enum.join(set)) == signal
      end)
      |> Enum.map(fn {start, set} -> {start, Enum.join(set)} end)

    sequences
  end
end

Day6.main()
