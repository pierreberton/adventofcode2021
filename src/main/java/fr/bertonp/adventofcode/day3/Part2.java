package fr.bertonp.adventofcode.day3;

import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Part2 {

    public static void main(String[] args) {
        try {
            URL resource = Part2.class.getClassLoader().getResource("day3/input1.txt");

            if (resource == null) {
                throw new FileNotFoundException();
            }

            List<String> lines = Files.readAllLines(Paths.get(resource.toURI()), StandardCharsets.UTF_8);

            String oxygenLine = extracted(lines, true);
            String co2Line = extracted(lines, false);

            System.out.println("Result : " + (toLongThroughtBitSet(oxygenLine) * toLongThroughtBitSet(co2Line)));
        } catch (Exception e) {
            System.err.println("Something went wrong :(");
            e.printStackTrace();
        }
    }

    private static String extracted(List<String> lines, boolean most) {
        List<List<Boolean>> matrice = createMatrix(lines);
        List<String> remainingLines = new ArrayList<>(lines);
        List<String> remainingLinesLastStep;
        int j = 0;
        while(remainingLines.size() > 1 && j < matrice.size()) {
            List<Boolean> booleans = new ArrayList<>(matrice.get(j));
            List<Integer> collect = IntStream.range(0, booleans.size())
                    .filter(i -> booleans.get(i) == getMostCommonBit(booleans, most))
                    .boxed().collect(Collectors.toList());
            remainingLinesLastStep = new ArrayList<>(remainingLines);
            remainingLines.clear();
            for (int i = 0; i < remainingLinesLastStep.size(); i++) {
                if(collect.contains(i)) {
                    remainingLines.add(remainingLinesLastStep.get(i));
                }
            }
            j++;
            matrice = createMatrix(remainingLines);
        }
        return remainingLines.get(0);
    }

    private static List<List<Boolean>> createMatrix(List<String> lines) {
        List<List<Boolean>> matrice = new ArrayList<>();
        lines.forEach(line -> {
            List<Boolean> chars = line.chars().mapToObj(c -> (char) c).map(c -> c == '1').collect(Collectors.toList());
            for (int i = 0; i < chars.size(); i++) {
                if(matrice.size() == i) {
                    matrice.add(new ArrayList<>());
                }
                matrice.get(i).add(chars.get(i));
            }
        });
        return matrice;
    }

    private static boolean getMostCommonBit(List<Boolean> bools, boolean most) {
        List<Boolean> copy = new ArrayList<>(bools);
        copy.sort((o1, o2) -> o1 == o2 ? 0 : (o1 ? 1 : -1));
        if ((copy.lastIndexOf(copy.get(0)) + 1) == (copy.size() / 2)) {
            return most;
        }
        if(most) {
            return (copy.lastIndexOf(copy.get(0)) + 1) < (copy.size() / 2);
        }
        return (copy.lastIndexOf(copy.get(0)) + 1) > (copy.size() / 2);
    }

    private static long toLongThroughtBitSet(String str) {
        List<Boolean> collect = str.chars().mapToObj(c -> (char) c).map(c -> c == '1').collect(Collectors.toList());
        BitSet bs = new BitSet(collect.size());
        for (int i = 0; i < collect.size(); i++) {
            bs.set(collect.size() - 1 - i, collect.get(i));
        }
        return bs.toLongArray()[0];
    }
}
