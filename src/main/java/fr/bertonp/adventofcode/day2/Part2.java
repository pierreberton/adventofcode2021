package fr.bertonp.adventofcode.day2;

import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.regex.Pattern;

public class Part2 {

    public static void main(String[] args) {
        try {
            URL resource = Part2.class.getClassLoader().getResource("day2/input1.txt");

            if (resource == null) {
                throw new FileNotFoundException();
            }

            List<String> lines = Files.readAllLines(Paths.get(resource.toURI()), StandardCharsets.UTF_8);
            int actualDepth = 0;
            int actualPosition = 0;
            int actualAim = 0;
            for(String line : lines) {
                String[] lineElems = line.split(Pattern.quote(" "));
                if(lineElems.length != 2) {
                    System.err.println("An input value is not an array : " + line + ". Ignoring !");
                    continue;
                }
                try {
                    int lineValue = Integer.parseInt(lineElems[1]);
                    switch (lineElems[0]) {
                        case "forward":
                            actualPosition += lineValue;
                            actualDepth += lineValue * actualAim;
                            break;
                        case "down":
                            actualAim += lineValue;
                            break;
                        case "up":
                            if (actualAim - lineValue < 0) {
                                actualAim = 0;
                            } else {
                                actualAim -= lineValue;
                            }
                            break;
                        default:
                            System.err.println("An input value does not contain a valid order : " + line + ". Ignoring !");
                    }
                } catch (NumberFormatException nfe) {
                    System.err.println("An input value is not an integer : " + line + ". Ignoring !");
                }
            }
            System.out.println("Result : " + (actualDepth * actualPosition));
        } catch (Exception e) {
            System.err.println("Something went wrong :(");
            e.printStackTrace();
        }
    }
}
