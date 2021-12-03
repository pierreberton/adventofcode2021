package fr.bertonp.adventofcode.day3;

import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Part1 {

    public static void main(String[] args) {
        try {
            URL resource = Part1.class.getClassLoader().getResource("day3/input1.txt");

            if (resource == null) {
                throw new FileNotFoundException();
            }

            List<String> lines = Files.readAllLines(Paths.get(resource.toURI()), StandardCharsets.UTF_8);

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

            BitSet gamma = new BitSet(matrice.size());
            BitSet epsilon;
            for (int i = 0; i < matrice.size(); i++) {
                gamma.set(matrice.size() - 1 - i, getMostCommonBit(matrice.get(i)));
            }

            BitSet allOnes = new BitSet(matrice.size());
            allOnes.set(0, matrice.size(), true);

            epsilon = (BitSet) gamma.clone();
            epsilon.xor(allOnes);

            System.out.println("Result : " + toLong(gamma) * toLong(epsilon));
        } catch (Exception e) {
            System.err.println("Something went wrong :(");
            e.printStackTrace();
        }
    }

    private static boolean getMostCommonBit(List<Boolean> bools) {
        List<Boolean> copy = new ArrayList<>(bools);
        copy.sort((o1, o2) -> o1 == o2 ? 0 : (o1 ? 1 : -1));
        return copy.lastIndexOf(copy.get(0)) < (copy.size() / 2);
    }

    private static long toLong(BitSet bitset) {
        return bitset.toLongArray()[0];
    }
}
