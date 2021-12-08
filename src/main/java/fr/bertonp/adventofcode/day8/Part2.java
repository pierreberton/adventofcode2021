package fr.bertonp.adventofcode.day8;

import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Part2 {

    public static void main(String[] args) {
        try {
            URL resource = Part2.class.getClassLoader().getResource("day8/input1.txt");

            if (resource == null) {
                throw new FileNotFoundException();
            }

            List<String> lines = Files.readAllLines(Paths.get(resource.toURI()), StandardCharsets.UTF_8);

            List<Pair<List<String>, List<String>>> globalInput = new ArrayList<>();

            for(String line : lines) {
                String[] parts = line.split(Pattern.quote(" | "));
                globalInput.add(new Pair<>(
                        Arrays.stream(parts[0].split(Pattern.quote(" "))).filter(s -> !s.isBlank()).collect(Collectors.toList()),
                        Arrays.stream(parts[1].split(Pattern.quote(" "))).filter(s -> !s.isBlank()).collect(Collectors.toList())
                ));
            }

            int count = 0;
            for (Pair<List<String>, List<String>> pair : globalInput) {
                Digit digits = new Digit(pair.getInput());
                StringBuilder sb = new StringBuilder();
                for (String s : pair.getOutput()) {
                    sb.append(digits.getNumberFromSegments(s));
                }
                count += Integer.parseInt(sb.toString());
            }

            System.out.println("Result : " + count);

        } catch (Exception e) {
            System.err.println("Something went wrong :(");
            e.printStackTrace();
        }
    }

}
