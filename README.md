# Countdown Game
My implementation of a Countdown game through Java. I use MVC, controller to handle game logic, model to handle game data and view for interacting with user.

## Table of Contents
- [Overview](#overview)
- [Usage](#usage)
- [Running the Game](#running-the-game)

## Overview

The Countdown game consists of four rounds where the player needs to form words using randomly generated letters. 
The game has a timer, and the player scores points based on the length of the word they can form. 
After each round, the player's score is updated and shown, and after all rounds are completed, the final score is displayed, 
along with a ranking of the top players.

The project uses a **Model-View-Controller** (MVC) architecture:
- **Model**: Represents the game data (player scores, words, letters).
- **View**: Handles displaying the game interface and receiving user input.
- **Controller**: Contains the game logic, connecting the Model and View.

## Usage

1. **Start the Game**: The game will start by showing an introduction and asking for the player's name.
2. **Rounds**: Each round, the player will be given a set of letters (vowels and consonants), and they need to form a valid word using those letters within a time limit.
3. **Scoring**: Each word's score is based on its length. After every round, the score is updated.
4. **Final Results**: After all rounds, the final score is displayed along with the top players' rankings.
5. **Save Scores**: At the end of the game, the player's score is saved to a text file.

## Running the Game

### Requirements
- Java 8 or higher

### To run the game:
1. Clone or download the repository.
2. Compile and run the program.
3. The game will prompt you for inputs such as the player's name and letter choices.

```bash
$ git clone https://github.com/babiiish/countdownGame.git
$ cd src
$ cd main
$ cd java
$ java -cp bin Main.java
```


