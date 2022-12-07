use aoc_write_answers::{read_input, write_answers};
use std::io::Error;

fn main() -> Result<(), Error> {
    let input = read_input()?;
    let answer1 = answer1(input.to_vec());
    let answer2 = answer2(input.to_vec());
    write_answers(answer1, answer2)
}

fn answer1(_input: Vec<String>) -> String {
    "".to_string()
}

fn answer2(_input: Vec<String>) -> String {
    "".to_string()
}
