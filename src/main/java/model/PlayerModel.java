package model;

public class PlayerModel {
    private String name;
    private int score;
    private int roundsPlayed;

    public PlayerModel(String name, int score, int rounds) {
        this.name = name;
        this.score = score;
        this.roundsPlayed = rounds;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void addScore(int points) {
        this.score += points;
    }

    public int getRoundsPlayed() {
        return roundsPlayed;
    }

    public void incrementRounds() {
        this.roundsPlayed++;
    }

    public void resetPlayer() {
        this.score = 0;
        this.roundsPlayed = 0;
    }

    @Override
    public String toString() {
        return name + "," + score + "," + roundsPlayed;
    }

    public static PlayerModel fromString(String playerString) {
        String[] parts = playerString.split(",");
        String name = parts[0];
        int score = Integer.parseInt(parts[1]);
        int roundsPlayed = Integer.parseInt(parts[2]);
        return new PlayerModel(name, score, roundsPlayed);
    }

}