package com.xebia.xke_memory_client.api.out;

public class PlayResponse {
    /**
     * game round
     */
    private int gameId;
    /**
     * game progress, in percents
     */
    private float progress;

    /**
     * total score of the game
     */
    private int gameScore;

    private Turn turn;

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public float getProgress() {
        return progress;
    }

    public void setProgress(float progress) {
        this.progress = progress;
    }

    public int getGameScore() {
        return gameScore;
    }

    public void setGameScore(int gameScore) {
        this.gameScore = gameScore;
    }

    public Turn getTurn() {
        return turn;
    }

    public void setTurn(Turn turn) {
        this.turn = turn;
    }
}
