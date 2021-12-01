package fr.bertonp.adventofcode.day1;

import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class Part2 {

    public static void main(String[] args) {
        try {
            URL resource = Part2.class.getClassLoader().getResource("day1/input1.txt");

            if (resource == null) {
                throw new FileNotFoundException();
            }

            List<String> lines = Files.readAllLines(Paths.get(resource.toURI()), StandardCharsets.UTF_8);
            Integer lastSumOfDepths = null;
            int numberOfTimesDepthIncreased = 0;
            for(int i=0; i<lines.size()-2; i++) {
                try {
                    int firstLineValue = Integer.parseInt(lines.get(i));
                    int secondLineValue = Integer.parseInt(lines.get(i + 1));
                    int thirdLineValue = Integer.parseInt(lines.get(i + 2));
                    int sumOfDepths = firstLineValue + secondLineValue + thirdLineValue;
                    if(lastSumOfDepths != null && lastSumOfDepths < sumOfDepths) {
                        numberOfTimesDepthIncreased++;
                    }
                    lastSumOfDepths = sumOfDepths;
                } catch (NumberFormatException nfe) {
                    System.err.println("An input value is not an integer : "
                            + lines.get(i)
                            + ", "
                            + lines.get(i + 1)
                            + " or "
                            + lines.get(i + 2)
                            + ". Ignoring !");
                }
            }
            System.out.println("Number of times sum of depth was increased : " + numberOfTimesDepthIncreased);
        } catch (Exception e) {
            System.err.println("Something went wrong :(");
            e.printStackTrace();
        }
    }
}
