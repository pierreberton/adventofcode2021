package fr.bertonp.adventofcode.day7;

import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Part1 {

    public static void main(String[] args) {
        try {
            URL resource = Part1.class.getClassLoader().getResource("day7/input1.txt");

            if (resource == null) {
                throw new FileNotFoundException();
            }

            List<String> lines = Files.readAllLines(Paths.get(resource.toURI()), StandardCharsets.UTF_8);

            List<Integer> horizontalPositions = Arrays.stream(lines.get(0).split(Pattern.quote(",")))
                    .filter(s -> !s.isEmpty())
                    .map(Integer::valueOf)
                    .collect(Collectors.toList());

            System.out.println("Result : " + minimumAlignFuel(horizontalPositions));

        } catch (Exception e) {
            System.err.println("Something went wrong :(");
            e.printStackTrace();
        }
    }

    public static int minimumAlignFuel(List<Integer> positions)
    {
        int maxPos = Collections.max(positions);
        int minFuel = Integer.MAX_VALUE;
        for (int end = 0; end < maxPos; end++) {
            int fuel = 0;
            for (int start : positions) {
                fuel += Math.abs(end - start);
            }
            if (fuel < minFuel) {
                minFuel = fuel;
            }
        }
        return minFuel;
    }

}
