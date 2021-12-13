package fr.bertonp.adventofcode.day13;

import com.google.common.base.Objects;

import java.util.regex.Pattern;

public class Point {
    private int x;
    private int y;

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Point(String s) {
        String[] coordinates = s.trim().split(Pattern.quote(","));
        this.x = Integer.parseInt(coordinates[0]);
        this.y = Integer.parseInt(coordinates[1]);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point)) return false;
        Point point = (Point) o;
        return x == point.x && y == point.y;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(x, y);
    }
}
