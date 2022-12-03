use std::fs::File;
use std::io::{BufRead, BufReader, Error, Write};

const ANSWER_FILE: &str = "answers.txt";
const INPUT_FILE: &str = "input.txt";

pub fn write_answers(answer1: String, answer2: String) -> Result<(), Error> {
    let mut output = File::create(ANSWER_FILE)?;
    let content = format_answers(answer1, answer2);
    write!(output, "{}", content)?;
    Ok(())
}

pub fn read_input() -> Result<Vec<String>, Error> {
    read_file(INPUT_FILE)
}

fn read_file(input: &str) -> Result<Vec<String>, Error> {
    let answers_txt = File::open(input)?;
    let mut lines = Vec::new();
    let buffered = BufReader::new(answers_txt);
    for line in buffered.lines() {
        lines.push(line?);
    }
    Ok(lines)
}

fn format_answers(answer1: String, answer2: String) -> String {
    format!("Answer 1:\n{}\n\nAnswer 2:\n{}\n", answer1, answer2)
}

#[cfg(test)]
mod tests {
    use super::*;

    #[test]
    fn write_answers_writes_to_file() -> Result<(), Error> {
        let answer1 = "849";
        let answer2 = "092";
        write_answers(answer1.to_string(), answer2.to_string())?;
        let actual = read_answers()?;

        let expected = format_answers(answer1.to_string(), answer2.to_string());
        assert_eq!(actual, expected);
        Ok(())
    }

    fn read_answers() -> Result<String, Error> {
        let answers_txt = read_file(ANSWER_FILE)?;
        Ok(answers_txt.join("\n") + "\n")
    }
}
