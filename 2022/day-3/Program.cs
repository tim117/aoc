using AocWrite;
using System.Collections;
using System.Linq;

const int lowerCaseAdjustment = 96;
const int upperCaseAdjustment = 38;

List<string> ReadInput()
{
    var input = new List<string>();
    using (StreamReader reader = new StreamReader("input.txt"))
    {
        while (!reader.EndOfStream)
        {
            input.Add(reader.ReadLine()!);
        }
    }
    return input;
}

List<int> ToPriority(List<char> overlapping)
{
    var priorities = new List<int>();
    foreach (var character in overlapping)
    {
        if (Char.IsUpper(character))
        {
            priorities.Add(character - upperCaseAdjustment);
        }
        else
        {
            priorities.Add(character - lowerCaseAdjustment);
        }
    }
    return priorities;
}

string Answer1(List<string> input)
{
    var sacks = ReadSacks(input);
    var overlapping = CompareRows(sacks);
    var priorities = ToPriority(overlapping);
    return priorities.Sum().ToString();
}

List<List<string>> ReadSacks(List<string> input)
{
    var sacks = new List<List<string>>();
    foreach (var line in input)
    {
        var evenSacks = new List<string>();
        var half = line.Length / 2;
        evenSacks.Add(new string(line.Take(half).ToArray()));
        evenSacks.Add(new string(line.Skip(half).ToArray()));
        sacks.Add(evenSacks);
    }
    return sacks;
}

List<char> CompareRows(List<List<string>> sacks)
{
    var matching = new List<char>();
    foreach (var sack in sacks)
    {
        var overlap = sack[0].Intersect(sack[1]).ToHashSet();
        matching.Add(overlap.First());
    }
    return matching;
}

string Answer2(List<string> input)
{
    var groups = GroupElfs(input);
    var ids = FindGroupIds(groups);
    var priorities = ToPriority(ids);
    return priorities.Sum().ToString();
}

List<List<string>> GroupElfs(List<string> input)
{
    var groups = new List<List<string>>() { new List<string>() };
    var count = 0;
    foreach (var line in input)
    {
        if (count == 3)
        {
            groups.Add(new List<string>());
            count = 0;
        }
        groups.Last().Add(line);
        count++;
    }
    return groups;
}

List<char> FindGroupIds(List<List<string>> groups)
{
    var ids = new List<char>();
    foreach (var group in groups)
    {
        ids.Add(group[0].Intersect(group[1]).Intersect(group[2]).First());
    }
    Console.WriteLine(string.Join(", ", ids));
    return ids;
}

void Main()
{
    var input = ReadInput();
    var answer1 = Answer1(input);
    var answer2 = Answer2(input);
    Answers.Write(answer1, answer2);
}

Main();