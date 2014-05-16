package com.xebia.xke_memory_client.domain;

import java.util.Objects;

public class GameCard {
    private final String symbol;
    private final String color;

    public GameCard(String symbol, String color) {
        this.symbol = symbol;
        this.color = color;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getColor() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final GameCard gameCard = (GameCard) o;

        return Objects.equals(symbol, gameCard.symbol) && Objects.equals(color, gameCard.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(symbol, color);
    }
}
