package fr.bertonp.adventofcode.day9;

import com.google.common.base.Objects;

import java.util.regex.Pattern;

public class Point {
    private int x;
    private int y;
    private int value;

    public Point(int x, int y, int value) {
        this.x = x;
        this.y = y;
        this.value = value;
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

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point)) return false;
        Point point = (Point) o;
        return x == point.x && y == point.y && value == point.value;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(x, y, value);
    }
}
