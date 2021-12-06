package fr.bertonp.adventofcode.day6;

import java.io.FileNotFoundException;
import java.math.BigInteger;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
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

            List<Integer> lanternfishList = Arrays.stream(
                    lines.get(0).split(Pattern.quote(","))
            ).filter(s -> !s.isEmpty()).map(Integer::valueOf).collect(Collectors.toList());

            Lanternfish last = null;
            for (int lanternfish: lanternfishList) {
                last = new Lanternfish(lanternfish, last);
            }

            for (int i = 0; i < 256; i++) {
                Lanternfish current = last;
                BigInteger nbToAddAfter = BigInteger.valueOf(0L);
                while (current != null) {
                    if (current.getTimer() == 0) {
                        current.setTimer(6);
                        nbToAddAfter = nbToAddAfter.add(BigInteger.ONE);
                    } else {
                        current.setTimer(current.getTimer() - 1);
                    }
                    current = current.getNext();
                }
                while (!nbToAddAfter.equals(BigInteger.ZERO)) {
                    last = new Lanternfish(8, last);
                    nbToAddAfter = nbToAddAfter.subtract(BigInteger.ONE);
                }
            }

            BigInteger sum = BigInteger.valueOf(1L);
            Lanternfish current = last;
            while (current.getNext() != null) {
                sum = sum.add(BigInteger.ONE);
                current = current.getNext();
            }

            System.out.println("Result : " + sum);

        } catch (Exception e) {
            System.err.println("Something went wrong :(");
            e.printStackTrace();
        }
    }

}
