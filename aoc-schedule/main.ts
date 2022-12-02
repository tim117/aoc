#!/usr/bin/env node
import yargs from 'yargs';
import { appendFile, writeFile } from 'fs/promises';
import { oraPromise } from 'ora';
import { resolve } from 'path';

async function getArgs() {
  const { languages, filePath } = await yargs(process.argv.slice(2))
    .usage('$0 -l typescript go rust c++ -f schedule.txt')
    .epilogue(
      'create-advent-schedule creates a randomized schedule for Advent of Code based on the languages that were given to the cli and outputs it to the specified --file-path.'
    )
    .help()
    .string('file-path')
    .describe('file-path', 'The path to output the schedule.')
    .alias('file-path', 'f')
    .array('languages')
    .describe(
      'languages',
      'The languages to include in your Advent of Code schedule.'
    )
    .alias('languages', 'l').argv;
  return {
    languages: languages?.map((val) => val.toString()) ?? ['typescript'],
    filePath: filePath ?? 'schedule.txt'
  };
}

function makeSchedule(languages: string[]) {
  const schedule: Record<number, string> = {};
  for (let i = 1; i < 26; ++i) {
    const todaysLanguage = getRandomValue(languages);
    schedule[i] = todaysLanguage;
  }
  return schedule;
}

function getRandomValue<T>(list: T[]): T {
  const length = list.length;
  const index = Math.floor(Math.random() * length);
  return list[index];
}

type WriteScheduleOptions = {
  outputPath: string;
  schedule: Record<number, string>;
};
async function writeSchedule({ outputPath, schedule }: WriteScheduleOptions) {
  await writeFile(outputPath, '');
  for (const key in schedule) {
    await appendFile(outputPath, `Dec ${key}\t\t${schedule[key]}\n`);
  }
}

async function main() {
  const { languages, filePath } = await getArgs();
  const schedule = makeSchedule(languages);
  oraPromise(writeSchedule({ outputPath: filePath, schedule }), {
    text: 'Creating schedule',
    successText: `Schedule was successfully created at ${resolve(filePath)}.`,
    failText: 'Unable to create schedule.'
  });
}

main();
