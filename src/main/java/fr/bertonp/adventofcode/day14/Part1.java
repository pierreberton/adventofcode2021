package fr.bertonp.adventofcode.day14;

import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Part1 {

    private static final Map<String, String> polymerReplacementRules = new HashMap<>();

    public static void main(String[] args) {
        try {
            URL resource = Part1.class.getClassLoader().getResource("day14/input1.txt");

            if (resource == null) {
                throw new FileNotFoundException();
            }

            List<String> lines = Files.readAllLines(Paths.get(resource.toURI()), StandardCharsets.UTF_8);

            String polymerTemplate = lines.remove(0);
            lines.remove(0); // Again for blank line

            for (String line : lines) {
                String[] parts = line.split(Pattern.quote(" -> "));
                polymerReplacementRules.put(parts[0], parts[0].charAt(0) + parts[1] + parts[0].charAt(1));
            }

            Map<Character, Integer> countMap = new HashMap<>();

            for (int i = 0; i < 10; i++) {
                List<String> pairs = getPairs(polymerTemplate);
                StringBuilder tmpPolymer = new StringBuilder();
                for (String pair : pairs) {
                    if (!tmpPolymer.toString().isEmpty()) {
                        tmpPolymer = new StringBuilder(tmpPolymer.substring(0, tmpPolymer.length() - 1));
                    }
                    tmpPolymer.append(polymerReplacementRules.get(pair));
                }
                polymerTemplate = tmpPolymer.toString();
            }

            for (Character c : polymerTemplate.toCharArray()) {
                countMap.put(c, countMap.getOrDefault(c, 0) + 1);
            }

            int max = Integer.MIN_VALUE;
            int min = Integer.MAX_VALUE;

            for (Character c : countMap.keySet()) {
                min = Math.min(countMap.get(c), min);
                max = Math.max(countMap.get(c), max);
            }

            System.out.println("Result : " + (max - min));

        } catch (Exception e) {
            System.err.println("Something went wrong :(");
            e.printStackTrace();
        }
    }

    private static List<String> getPairs(String polymer) {
        List<String> pairs = new ArrayList<>();
        for (int i = 0; i < polymer.length() - 1; i++) {
            pairs.add(polymer.substring(i, i+2));
        }
        return pairs;
    }

}
