package com.xebia.xke_memory_client.api.out;

import java.util.List;

public class Turn {
    /**
     * score in this turn
     */
    private int turnScore;

    private List<TurnCard> cards;
    /**
     * warn message if exists
     */
    private String message;

    public int getTurnScore() {
        return turnScore;
    }

    public void setTurnScore(int turnScore) {
        this.turnScore = turnScore;
    }

    public List<TurnCard> getCards() {
        return cards;
    }

    public void setCards(List<TurnCard> cards) {
        this.cards = cards;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
