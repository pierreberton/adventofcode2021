package fr.bertonp.adventofcode.day14;

import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Part2 {

    private static final Map<String, String> polymerReplacementRules = new HashMap<>();

    public static void main(String[] args) {
        try {
            URL resource = Part2.class.getClassLoader().getResource("day14/input1.txt");

            if (resource == null) {
                throw new FileNotFoundException();
            }

            List<String> lines = Files.readAllLines(Paths.get(resource.toURI()), StandardCharsets.UTF_8);

            String polymerTemplate = lines.remove(0);
            lines.remove(0); // Again for blank line

            for (String line : lines) {
                String[] parts = line.split(Pattern.quote(" -> "));
                polymerReplacementRules.put(parts[0], parts[1]);
            }

            Map<String, BigInteger> countPairsMap = new HashMap<>();
            List<String> pairs = getPairs(polymerTemplate);
            for (String pair : pairs) {
                countPairsMap.put(pair, countPairsMap.getOrDefault(pair, BigInteger.ZERO).add(BigInteger.ONE));
            }

            Map<String, BigInteger> countLettersMap = new HashMap<>();
            List<String> letters = getLetters(polymerTemplate);
            for (String letter : letters) {
                countLettersMap.put(letter, countLettersMap.getOrDefault(letter, BigInteger.ZERO).add(BigInteger.ONE));
            }

            for (int i = 0; i < 40; i++) {
                Map<String, BigInteger> previousCountPairs = new HashMap<>(countPairsMap);

                for (String key : polymerReplacementRules.keySet()) {
                    String value = polymerReplacementRules.get(key);
                    String newPairLeft = key.charAt(0) + value;
                    String newPairRight = value + key.charAt(1);
                    BigInteger nbPrevPair = previousCountPairs.getOrDefault(key, BigInteger.ZERO);
                    countPairsMap.put(key, (countPairsMap.get(key) != null ? countPairsMap.get(key).subtract(nbPrevPair) : BigInteger.ZERO));
                    countPairsMap.put(newPairLeft, countPairsMap.getOrDefault(newPairLeft, BigInteger.ZERO).add(nbPrevPair));
                    countPairsMap.put(newPairRight, countPairsMap.getOrDefault(newPairRight, BigInteger.ZERO).add(nbPrevPair));
                    countLettersMap.put(value, countLettersMap.getOrDefault(value, BigInteger.ZERO).add(nbPrevPair));
                }
            }

            BigInteger max = BigInteger.ZERO;
            BigInteger min = BigInteger.valueOf(Long.MAX_VALUE);

            for (String c : countLettersMap.keySet()) {
                min = min.min(countLettersMap.get(c));
                max = max.max(countLettersMap.get(c));
            }

            System.out.println("Result : " + (max.subtract(min)));

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

    private static List<String> getLetters(String polymer) {
        List<String> letters = new ArrayList<>();
        for (int i = 0; i < polymer.length(); i++) {
            letters.add(polymer.substring(i, i+1));
        }
        return letters;
    }

}
