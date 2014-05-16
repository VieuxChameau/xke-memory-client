package com.xebia.xke_memory_client.api.in;


import com.xebia.xke_memory_client.domain.Point;

public class PlayRequest {
    private final Point firstPoint;
    private final Point secondPoint;

    public PlayRequest(final Point first, final Point secondPoint) {
        this.firstPoint = first;
        this.secondPoint = secondPoint;
    }

    public int[][] asArray() {
        final int[][] pos = {{firstPoint.getX(), firstPoint.getY()}, {secondPoint.getX(), secondPoint.getY()}};
        return pos;
    }

    public Point getFirstPoint() {
        return firstPoint;
    }

    public Point getSecondPoint() {
        return secondPoint;
    }
}
