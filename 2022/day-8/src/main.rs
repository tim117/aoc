use aoc_write_answers::{read_input, write_answers};
use std::{
    collections::HashSet,
    io::Error,
    sync::atomic::{AtomicI32, Ordering},
};

static ID: AtomicI32 = AtomicI32::new(0);

#[derive(Clone, Copy, Debug)]
struct Tree {
    pub id: i32,
    pub height: i32,
    pub score: i32,
}

fn main() -> Result<(), Error> {
    let input = read_input()?;
    let trees = convert_to_tree_vec(input);
    let answer1 = answer1(trees.to_vec());
    let answer2 = answer2(trees.to_vec());
    write_answers(answer1, answer2)
}

fn convert_to_tree_vec(input: Vec<String>) -> Vec<Vec<Tree>> {
    let mut trees = Vec::new();
    for row in input.into_iter() {
        let mut tree_row = Vec::new();
        for tree in row.chars() {
            let tree_height = tree.to_string().parse::<i32>().unwrap();
            let id = ID.fetch_add(1, Ordering::SeqCst);
            tree_row.push(Tree {
                id,
                height: tree_height,
                score: 0,
            });
        }
        trees.push(tree_row);
    }
    trees
}

fn answer1(trees: Vec<Vec<Tree>>) -> String {
    let seen_from_top = seen_from_side(trees.to_vec());
    let right = rotate(trees);
    let seen_from_right = seen_from_side(right.to_vec());
    let bottom = rotate(right);
    let seen_from_bottom = seen_from_side(bottom.to_vec());
    let left = rotate(bottom);
    let seen_from_left = seen_from_side(left.to_vec());
    let mut seen = seen_from_top.clone();
    seen.extend(&seen_from_right);
    seen.extend(&seen_from_bottom);
    seen.extend(&seen_from_left);
    format!("{:#?}", seen.len())
}

fn seen_from_side(trees: Vec<Vec<Tree>>) -> HashSet<i32> {
    let mut seen = HashSet::new();
    let mut blocking = vec![-1; trees.len()];
    for row in 0..trees.len() {
        for col in 0..blocking.len() {
            let current = trees.get(row).unwrap().get(col).unwrap();
            if current.height > blocking.get(col).unwrap().clone() {
                seen.insert(current.id);
                blocking[col] = current.height;
            }
        }
    }
    seen
}

fn rotate(trees: Vec<Vec<Tree>>) -> Vec<Vec<Tree>> {
    let length = trees.len();
    let mut rotated = Vec::new();
    for _ in trees.to_vec().into_iter() {
        rotated.push(vec![
            Tree {
                height: -1,
                id: -1,
                score: -1,
            };
            trees.to_vec().len()
        ])
    }
    for row in 0..length {
        for index in 0..length {
            let col = length - index - 1;
            rotated[row][index] = trees[col][row];
        }
    }
    rotated
}

fn answer2(trees: Vec<Vec<Tree>>) -> String {
    let scored_trees = add_scores(trees.to_vec());
    let highest = highest_score(scored_trees);
    highest.to_string()
}

#[derive(Clone, Copy)]
enum Direction {
    DOWN,
    LEFT,
    RIGHT,
    UP,
}

fn add_scores(mut trees: Vec<Vec<Tree>>) -> Vec<Vec<Tree>> {
    for row in 0..trees.len() {
        for col in 0..trees.len() {
            trees[row][col].score = calculate_score((row, col), &trees, Direction::DOWN)
                * calculate_score((row, col), &trees, Direction::LEFT)
                * calculate_score((row, col), &trees, Direction::RIGHT)
                * calculate_score((row, col), &trees, Direction::UP);
            if row == 0 || row == trees.len() - 1 || col == 0 || col == trees.len() - 1 {
                trees[row][col].score = 0;
            }
        }
    }
    return trees;
}

fn calculate_score(location: (usize, usize), trees: &Vec<Vec<Tree>>, direction: Direction) -> i32 {
    let height = trees[location.0][location.1].height;
    let (mut next_x, mut next_y) =
        adjust_location((location.0 as i32, location.1 as i32), direction);
    if next_x < 0 || next_y < 0 || next_x >= trees.len() as i32 || next_y >= trees.len() as i32 {
        return 0;
    }
    let mut next_height = trees[next_x as usize][next_y as usize].height;
    let mut vision = 1;
    while next_x >= 0
        && next_y >= 0
        && next_x < trees.len() as i32
        && next_y < trees.len() as i32
        && height > next_height
    {
        (next_x, next_y) = adjust_location((next_x, next_y), direction);
        if next_x < 0 || next_y < 0 || next_x >= trees.len() as i32 || next_y >= trees.len() as i32
        {
            continue;
        }
        next_height = trees[next_x as usize][next_y as usize].height;
        vision += 1;
    }
    vision
}

fn adjust_location(current_location: (i32, i32), direction: Direction) -> (i32, i32) {
    let location = match direction {
        Direction::DOWN => (current_location.0, current_location.1 as i32 - 1),
        Direction::LEFT => (current_location.0 as i32 - 1, current_location.1),
        Direction::RIGHT => (current_location.0 as i32 + 1, current_location.1),
        Direction::UP => (current_location.0, current_location.1 as i32 + 1),
    };
    location
}

fn highest_score(trees: Vec<Vec<Tree>>) -> i32 {
    let mut highest = -1;
    for row in trees.into_iter() {
        for tree in row.into_iter() {
            if tree.score > highest {
                highest = tree.score;
            }
        }
    }
    highest
}
