package fr.bertonp.adventofcode.day1;

import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Part1 {

    public static void main(String[] args) {
        try {
            URL resource = Part1.class.getClassLoader().getResource("day1/input1.txt");

            if (resource == null) {
                throw new FileNotFoundException();
            }

            List<String> lines = Files.readAllLines(Paths.get(resource.toURI()), StandardCharsets.UTF_8);
            Integer actualDepth = null;
            int numberOfTimesDepthIncreased = 0;
            for(String line : lines) {
                try {
                    int lineValue = Integer.parseInt(line);
                    if(actualDepth != null && actualDepth < lineValue) {
                        numberOfTimesDepthIncreased++;
                    }
                    actualDepth = lineValue;
                } catch (NumberFormatException nfe) {
                    System.err.println("An input value is not an integer : " + line + ". Ignoring !");
                }
            }
            System.out.println("Number of times depth was increased : " + numberOfTimesDepthIncreased);
        } catch (Exception e) {
            System.err.println("Something went wrong :(");
            e.printStackTrace();
        }
    }
}
