package fr.bertonp.adventofcode.day8;

public class Pair <T, U> {

    private T input;

    private U output;

    public Pair(T input, U output) {
        this.input = input;
        this.output = output;
    }

    public T getInput() {
        return input;
    }

    public void setInput(T input) {
        this.input = input;
    }

    public U getOutput() {
        return output;
    }

    public void setOutput(U output) {
        this.output = output;
    }
}
