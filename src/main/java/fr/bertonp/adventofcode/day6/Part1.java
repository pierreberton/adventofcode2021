package fr.bertonp.adventofcode.day6;

import fr.bertonp.adventofcode.day5.Point;

import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Part1 {

    public static void main(String[] args) {
        try {
            URL resource = Part1.class.getClassLoader().getResource("day6/input1.txt");

            if (resource == null) {
                throw new FileNotFoundException();
            }

            List<String> lines = Files.readAllLines(Paths.get(resource.toURI()), StandardCharsets.UTF_8);

            List<Integer> lanternfishList = Arrays.stream(
                    lines.get(0).split(Pattern.quote(","))
            ).filter(s -> !s.isEmpty()).map(Integer::valueOf).collect(Collectors.toList());

            for (int i = 0; i < 80; i++) {
                int nbToAddAfter = 0;
                for (int j = 0; j < lanternfishList.size(); j++) {
                    if(lanternfishList.get(j) == 0) {
                        lanternfishList.set(j, 6);
                        nbToAddAfter++;
                    } else {
                        lanternfishList.set(j, lanternfishList.get(j) - 1);
                    }
                }
                for (int j = 0; j < nbToAddAfter; j++) {
                    lanternfishList.add(8);
                }
            }

            System.out.println("Result : " + lanternfishList.size());

        } catch (Exception e) {
            System.err.println("Something went wrong :(");
            e.printStackTrace();
        }
    }

}
