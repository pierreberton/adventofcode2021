package fr.bertonp.adventofcode.day6;

public class Lanternfish {

    private int timer;

    private Lanternfish next;

    public Lanternfish(int timer) {
        this.timer = timer;
        next = null;
    }

    public Lanternfish(int timer, Lanternfish next) {
        this.timer = timer;
        this.next = next;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public Lanternfish getNext() {
        return next;
    }

    public void setNext(Lanternfish next) {
        this.next = next;
    }
}
