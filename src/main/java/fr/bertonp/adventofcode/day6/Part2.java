package fr.bertonp.adventofcode.day6;

import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Part2 {

    public static void main(String[] args) {
        try {
            URL resource = Part2.class.getClassLoader().getResource("day6/input1.txt");

            if (resource == null) {
                throw new FileNotFoundException();
            }

            List<String> lines = Files.readAllLines(Paths.get(resource.toURI()), StandardCharsets.UTF_8);

            List<BigInteger> listeEtats = new ArrayList<>();
            for (int i = 0; i < 9; i++) {
                listeEtats.add(BigInteger.ZERO);
            }

            Arrays.stream(lines.get(0).split(Pattern.quote(",")))
                    .filter(s -> !s.isEmpty())
                    .map(Integer::valueOf)
                    .forEach(i -> listeEtats.set(i, listeEtats.get(i).add(BigInteger.ONE)));

            for (int i = 0; i < 256; i++) {
                List<BigInteger> listeEtatsTmp = new ArrayList<>(listeEtats);
                for (int j = 1; j < listeEtats.size(); j++) {
                    listeEtatsTmp.set(j - 1, listeEtats.get(j));
                }
                listeEtatsTmp.set(6, listeEtats.get(0).add(listeEtatsTmp.get(6)));
                listeEtatsTmp.set(8, listeEtats.get(0));
                listeEtats.clear();
                listeEtats.addAll(listeEtatsTmp);
            }

            BigInteger sum = BigInteger.ZERO;
            for (BigInteger listeEtat : listeEtats) {
                sum = sum.add(listeEtat);
            }

            System.out.println("Result : " + sum);

        } catch (Exception e) {
            System.err.println("Something went wrong :(");
            e.printStackTrace();
        }
    }

}
