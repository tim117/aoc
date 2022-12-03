#include <iostream>
#include <fstream>
#include <string>
#include "write.h"

using namespace std;

void WriteAnswers(string answer1, string answer2)
{
    ofstream answersTxt;
    answersTxt.open("answers.txt");
    answersTxt << "Answer 1:" << endl
               << answer1 << endl
               << endl
               << "Answer 2:" << endl
               << answer2 << endl;
    answersTxt.close();
}
