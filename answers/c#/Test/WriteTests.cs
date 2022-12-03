using AocWrite;
using System.IO;
using Microsoft.VisualStudio.TestTools.UnitTesting;

namespace test;

[TestClass]
public class UnitTest1
{
    [TestMethod]
    public void Write_WritesAnswerTxtFile()
    {
        string answer1 = "173", answer2 = "954";

        Answers.Write(answer1, answer2);

        var actual = ReadAnswers();
        var expected = FormatAnswers(answer1, answer2);
        Assert.AreEqual(expected, actual);
    }

    private string FormatAnswers(string answers1, string answers2)
    {
        return "Answer 1:" + answers1 + "Answer 2:" + answers2;
    }

    private string ReadAnswers()
    {
        var content = "";
        using (StreamReader reader = new StreamReader("answers.txt"))
        {
            while (!reader.EndOfStream)
            {
                content += reader.ReadLine();
            }
        }
        return content;
    }
}