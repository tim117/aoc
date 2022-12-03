namespace AocWrite;

public class Answers
{
    public static void Write(string answer1, string answer2)
    {
        using (StreamWriter writer = new StreamWriter("answers.txt"))
        {
            writer.WriteLine("Answer 1:");
            writer.WriteLine(answer1);
            writer.WriteLine();
            writer.WriteLine("Answer 2:");
            writer.WriteLine(answer2);
        }
    }
}
