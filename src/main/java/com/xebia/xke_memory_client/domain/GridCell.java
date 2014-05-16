package com.xebia.xke_memory_client.domain;

import java.util.Objects;

public class GridCell {
    private final GameCard gameCard;
    private final Point point;
    private final boolean alreadyFound;

    public GridCell(final GameCard gameCard, final Point point, final boolean found) {
        this.gameCard = gameCard;
        this.point = point;
        this.alreadyFound = found;
    }

    public GameCard getGameCard() {
        return gameCard;
    }

    public Point getPoint() {
        return point;
    }

    public boolean isAlreadyFound() {
        return alreadyFound;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final GridCell gridCell = (GridCell) o;
        return Objects.equals(gameCard, gridCell.gameCard) && Objects.equals(point, gridCell.point);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameCard, point);
    }
}
