package fr.bertonp.adventofcode.day13;

import java.util.Arrays;
import java.util.Optional;

public class Folding {

    private int coord;

    private Direction direction;

    public Folding(int input, Direction output) {
        this.coord = input;
        this.direction = output;
    }

    public int getCoord() {
        return coord;
    }

    public void setCoord(int coord) {
        this.coord = coord;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public enum Direction {
        HORIZONTAL("y"), VERTICAL("x");

        String value;

        Direction(String dir) {
            value = dir;
        }

        public static Direction fromString(String str) {
            return Arrays.stream(values()).filter(d -> d.value.equals(str)).findFirst().orElse(null);
        }
    }
}
