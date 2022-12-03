#include "write.cpp"
#include <iostream>
#include <fstream>
#include <string>
using namespace std;

string expectedAnswerTxt(string answer1, string answer2)
{
    return "Answer 1:\n" + answer1 + "\n\nAnswer 2:\n" + answer2 + "\n";
}

string readAnswers()
{
    string content = "";
    ifstream answersTxt("answers.txt");
    if (answersTxt.is_open())
    {
        string line;
        while (getline(answersTxt, line))
        {
            content += line + "\n";
        }
        answersTxt.close();
    }
    return content;
}

int main()
{
    string answer1 = "544";
    string answer2 = "998";

    WriteAnswers(answer1, answer2);

    string actual = readAnswers();
    if (actual != expectedAnswerTxt(answer1, answer2))
    {
        throw "Got [" + actual + "] expected [" + answer2 + "]";
    }
    cout << "Passed test" << endl;
    return 0;
}