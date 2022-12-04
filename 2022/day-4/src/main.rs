use aoc_write_answers::{read_input, write_answers};
use std::{any, io::Error};

fn main() -> Result<(), Error> {
    let ranges = get_ranges()?;
    let answer1 = answer1(ranges.to_vec());
    let answer2 = answer2(ranges.to_vec());
    write_answers(answer1, answer2)
}

fn answer1(ranges: Vec<(Range, Range)>) -> String {
    let overlapping = find_overlapping(ranges, false);
    overlapping.to_string()
}

#[derive(Clone, Copy)]
struct Range {
    start: i32,
    end: i32,
}

fn get_ranges() -> Result<Vec<(Range, Range)>, Error> {
    let input = read_input()?;
    let mut ranges = Vec::new();
    for line in input.iter() {
        let split: Vec<&str> = line.split(',').collect();
        let first_range = parse_range(split.first().unwrap());
        let second_range = parse_range(split.last().unwrap());
        ranges.push((first_range, second_range));
    }
    Ok(ranges)
}

fn parse_range(range_str: &str) -> Range {
    let range: Vec<&str> = range_str.split("-").collect();
    let start = range.first().unwrap().parse().unwrap();
    let end = range.last().unwrap().parse().unwrap();
    Range { start, end }
}

fn find_overlapping(ranges: Vec<(Range, Range)>, any_overlapping: bool) -> i32 {
    let mut count_overlapping = 0;
    for range_tuple in ranges.iter() {
        let first = range_tuple.0;
        let second = range_tuple.1;
        if !any_overlapping && is_fully_overlapping(first, second) {
            count_overlapping += 1;
        }
        if any_overlapping && is_overlapping(first, second) {
            count_overlapping += 1;
        }
    }
    count_overlapping
}

fn is_fully_overlapping(first: Range, second: Range) -> bool {
    first.start <= second.start && first.end >= second.end
        || second.start <= first.start && second.end >= first.end
}

fn is_overlapping(first: Range, second: Range) -> bool {
    // First starts within the second.
    first.start >= second.start && first.start <= second.end
    ||
    // First ends within the second.
    first.end >= second.start && first.end <= second.end
    ||
    // Second starts within the first.
    second.start >= first.start && second.end <= first.end
    ||
    // Second ends within the first.
    second.end >= first.start && second.end <= first.end
}

fn answer2(ranges: Vec<(Range, Range)>) -> String {
    let overlapping = find_overlapping(ranges, true);
    overlapping.to_string()
}
