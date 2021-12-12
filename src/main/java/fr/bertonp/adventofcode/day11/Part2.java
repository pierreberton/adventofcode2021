package fr.bertonp.adventofcode.day11;

import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Part2 {

    private static final List<List<Integer>> matrix = new ArrayList<>();

    public static void main(String[] args) {
        try {
            URL resource = Part2.class.getClassLoader().getResource("day11/input1.txt");

            if (resource == null) {
                throw new FileNotFoundException();
            }

            List<String> lines = Files.readAllLines(Paths.get(resource.toURI()), StandardCharsets.UTF_8);

            for (String line : lines) {
                matrix.add(new ArrayList<>());
                Arrays.stream(line.split("")).forEach(c -> matrix.get(matrix.size() - 1).add(Integer.valueOf(c)));
            }

            int flashes = 0;

            int bcl = 0;
            while (true) {
                bcl++;
                for (List<Integer> integers : matrix) {
                    for (int j = 0; j < integers.size(); j++) {
                        integers.set(j, integers.get(j) + 1);
                    }
                }
                boolean thereWasFlashes;

                do {
                    thereWasFlashes = false;
                    for (int i = 0; i < matrix.size(); i++) {
                        for (int j = 0; j < matrix.get(i).size(); j++) {
                            if(matrix.get(i).get(j) != null && matrix.get(i).get(j) > 9) {
                                matrix.get(i).set(j, null);
                                thereWasFlashes = true;
                                for (int newI = i-1; newI <= i+1; newI++) {
                                    if(newI >= 0 && newI < matrix.size()) {
                                        for (int newJ = j - 1; newJ <= j + 1; newJ++) {
                                            if (newJ >= 0 && newJ < matrix.size()) {
                                                if(matrix.get(newI).get(newJ) != null) {
                                                    matrix.get(newI).set(newJ, matrix.get(newI).get(newJ) + 1);
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                } while (thereWasFlashes);

                int numberOfFlashes = 0;
                for (List<Integer> integers : matrix) {
                    for (int j = 0; j < integers.size(); j++) {
                        if (integers.get(j) == null) {
                            integers.set(j, 0);
                            flashes++;
                            numberOfFlashes++;
                        }
                    }
                }

                if (numberOfFlashes == (matrix.size() * matrix.get(0).size())) {
                    break;
                }
            }

            System.out.println("Result : " + bcl);

        } catch (Exception e) {
            System.err.println("Something went wrong :(");
            e.printStackTrace();
        }
    }

}
