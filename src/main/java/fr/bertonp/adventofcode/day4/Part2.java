package fr.bertonp.adventofcode.day4;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;

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
            URL resource = Part2.class.getClassLoader().getResource("day4/input1.txt");

            if (resource == null) {
                throw new FileNotFoundException();
            }

            List<String> lines = Files.readAllLines(Paths.get(resource.toURI()), StandardCharsets.UTF_8);

            List<Integer> callSet = Arrays.stream(
                    lines.get(0).split(Pattern.quote(","))
            ).map(s -> Integer.valueOf(s.trim())).collect(Collectors.toList());

            Map<Integer, List<Set<Integer>>> winPossibilities = new HashMap<>();

            int cptBoards = 0;

            for (int i = 1; i < lines.size(); i++) {

                String line = lines.get(i);

                if(line == null || line.isEmpty() || line.isBlank()) {
                    winPossibilities.put(cptBoards, new ArrayList<>());
                    for (int j = 0; j < 5; j++) {
                        winPossibilities.get(cptBoards).add(new HashSet<>());
                    }
                    cptBoards++;
                    continue;
                }

                List<Integer> lineIntegers = Arrays.stream(
                        line.trim().split(Pattern.quote(" "))
                ).filter(s -> !s.isEmpty()).map(s -> Integer.valueOf(s.trim())).collect(Collectors.toList());

                for (int j = 0; j < 5; j++) {
                    winPossibilities.get(cptBoards - 1).get(j).add(lineIntegers.get(j));
                }

                winPossibilities.get(cptBoards - 1).add(Set.copyOf(lineIntegers));
            }

            List<Integer> drawnNumbers = new ArrayList<>(callSet.subList(0, 4));
            int callSetIdx = 4;
            List<Integer> alreadyWon = new ArrayList<>();
            int lastAddedInteger;

            while (drawnNumbers.size() < callSet.size()) {
                lastAddedInteger = callSet.get(callSetIdx++);
                drawnNumbers.add(lastAddedInteger);
                Set<Set<Integer>> sortedPossibilities = getSortedPossibilities(drawnNumbers);

                for (int i = 0; i < winPossibilities.size(); i++) {
                    if(alreadyWon.contains(i)) {
                        continue;
                    }
                    for (Set<Integer> possibility : sortedPossibilities) {
                        if (winPossibilities.get(i).contains(possibility)) {
                            alreadyWon.add(i);
                            if(alreadyWon.size() == winPossibilities.size()) {
                                int notDrawn = 0;
                                for (Set<Integer> winPoss : winPossibilities.get(i)) {
                                    notDrawn += winPoss.stream().filter(elem -> !drawnNumbers.contains(elem)).reduce(0, Integer::sum);
                                }
                                System.out.println("Result : " + (notDrawn / 2) * drawnNumbers.get(drawnNumbers.size() - 1));
                                System.exit(0);
                            }
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Something went wrong :(");
            e.printStackTrace();
        }
    }

    private static Set<Set<Integer>> getSortedPossibilities(List<Integer> input) {
        if(input.size() < 5) {
            return new HashSet<>();
        }

        return Sets.combinations(ImmutableSet.copyOf(input), 5);
    }

}
