package fr.bertonp.adventofcode.day9;

import com.google.common.base.Strings;
import fr.bertonp.adventofcode.day8.Pair;

import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Part1 {

    public static void main(String[] args) {
        try {
            URL resource = Part1.class.getClassLoader().getResource("day9/input1.txt");

            if (resource == null) {
                throw new FileNotFoundException();
            }

            List<String> lines = Files.readAllLines(Paths.get(resource.toURI()), StandardCharsets.UTF_8);

            List<List<Integer>> matrix = new ArrayList<>();

            for (String line : lines) {
                matrix.add(new ArrayList<>());
                matrix.get(matrix.size() - 1).add(Integer.MAX_VALUE);
                matrix.get(matrix.size() - 1).addAll(
                        Arrays.stream(line.trim().split(""))
                                .map(Integer::valueOf)
                                .collect(Collectors.toList())
                );
                matrix.get(matrix.size() - 1).add(Integer.MAX_VALUE);
            }

            matrix.add(0, Collections.nCopies(matrix.get(0).size(), Integer.MAX_VALUE));
            matrix.add(Collections.nCopies(matrix.get(0).size(), Integer.MAX_VALUE));

            List<Integer> lowPoints = new ArrayList<>();
            for (int i = 1; i < matrix.size() - 1; i++) {
                for (int j = 1; j < matrix.get(i).size() - 1; j++) {
                    int elem = matrix.get(i).get(j);
                    int elemXm = matrix.get(i-1).get(j);
                    int elemXp = matrix.get(i+1).get(j);
                    int elemYm = matrix.get(i).get(j-1);
                    int elemYp = matrix.get(i).get(j+1);
                    if (elem < elemXm
                        && elem < elemXp
                        && elem < elemYm
                        && elem < elemYp) {
                        lowPoints.add(elem);
                    }
                }
            }

            int sum = lowPoints.stream().map(i -> i+1).reduce(0, Integer::sum);

            System.out.println("Result : " + sum);

        } catch (Exception e) {
            System.err.println("Something went wrong :(");
            e.printStackTrace();
        }
    }

}
