#include <iostream>
#include <fstream>
#include <string>
#include <sstream>
#include <vector>
#include "../../answers/c++/write.cpp"
using namespace std;

const int rock_value = 1;
const int paper_value = 2;
const int scissors_value = 3;

class Move
{
public:
    int value;
    int beats;
    int loses;

    Move() {}
    Move(int value, int beats, int loses)
    {
        this->value = value;
        this->beats = beats;
        this->loses = loses;
    }

    int score(Move move)
    {
        if (value == rock_value)
        {
            if (move.value == rock_value)
            {
                return 3;
            }
            if (move.value == paper_value)
            {
                return 0;
            }
            return 6;
        }
        if (value == paper_value)
        {
            if (move.value == rock_value)
            {
                return 6;
            }
            if (move.value == paper_value)
            {
                return 3;
            }
            return 0;
        }
        if (move.value == rock_value)
        {
            return 0;
        }
        if (move.value == paper_value)
        {
            return 6;
        }
        return 3;
    }
};

const Move rock(1, 3, 2);
const Move paper(2, 1, 3);
const Move scissors(3, 2, 1);

struct game
{
    Move opponent;
    Move you;
    int propper;
};

class Match
{
private:
    vector<game> games;

public:
    void add(game game)
    {
        games.push_back(game);
    }

    int calculate_score()
    {
        int score = 0;
        for (auto &game : games)
        {
            score += game.you.value + game.you.score(game.opponent);
        }
        return score;
    }

    int calculate_propper_score()
    {
        int score = 0;
        for (auto &game : games)
        {
            score += game.propper;
        }
        return score;
    }
};

Match read_match();
Move get_move(char);
int get_propper_score(Move, char);
Move get_move_for_value(int);

int main()
{
    Match match = read_match();
    WriteAnswers(to_string(match.calculate_score()), to_string(match.calculate_propper_score()));
    return 0;
}

Match read_match()
{
    Match match;
    ifstream input("input.txt");
    if (input.is_open())
    {
        string line;
        while (getline(input, line))
        {
            game game;
            game.opponent = get_move(line[0]);
            game.you = get_move(line[2]);
            game.propper = get_propper_score(game.opponent, line[2]);
            match.add(game);
        }
        input.close();
    }
    return match;
}

int get_propper_score(Move move, char code)
{
    if (code == 'X')
    {
        return move.beats;
    }
    if (code == 'Y')
    {
        return move.value + 3;
    }
    return move.loses + 6;
}

Move get_move_for_value(int value)
{
    if (value == 1)
    {
        return rock;
    }
    if (value = 2)
    {
        return paper;
    }
    return scissors;
}

Move get_move(char code)
{
    if (code == 'A' || code == 'X')
    {
        return rock;
    }
    if (code == 'B' || code == 'Y')
    {
        return paper;
    }
    return scissors;
}
