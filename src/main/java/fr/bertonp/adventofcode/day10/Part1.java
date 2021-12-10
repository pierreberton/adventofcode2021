package fr.bertonp.adventofcode.day10;

import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class Part1 {

    public enum In {
        PARENTHESIS, BRACES, BRACKETS, CHEVRONS
    }

    public static void main(String[] args) {
        try {
            URL resource = Part1.class.getClassLoader().getResource("day10/input1.txt");

            if (resource == null) {
                throw new FileNotFoundException();
            }

            List<String> lines = Files.readAllLines(Paths.get(resource.toURI()), StandardCharsets.UTF_8);

            int totalError = 0;

            for (String line : lines) {
                List<String> chars = Arrays.stream(line.trim().split("")).collect(Collectors.toList());

                List<In> actualPosition = new ArrayList<>();

                for (String c : chars) {
                    boolean errOccured = false;
                    switch (c) {
                        case "{":
                            actualPosition.add(In.BRACES);
                            break;
                        case "<":
                            actualPosition.add(In.CHEVRONS);
                            break;
                        case "(":
                            actualPosition.add(In.PARENTHESIS);
                            break;
                        case "[":
                            actualPosition.add(In.BRACKETS);
                            break;
                        case "}":
                            if(!In.BRACES.equals(actualPosition.get(actualPosition.size() - 1))) {
                                totalError += 1197;
                                errOccured = true;
                                break;
                            }
                            actualPosition.remove(actualPosition.lastIndexOf(In.BRACES));
                            break;
                        case ">":
                            if(!In.CHEVRONS.equals(actualPosition.get(actualPosition.size() - 1))) {
                                totalError += 25137;
                                errOccured = true;
                                break;
                            }
                            actualPosition.remove(actualPosition.lastIndexOf(In.CHEVRONS));
                            break;
                        case ")":
                            if(!In.PARENTHESIS.equals(actualPosition.get(actualPosition.size() - 1))) {
                                totalError += 3;
                                errOccured = true;
                                break;
                            }
                            actualPosition.remove(actualPosition.lastIndexOf(In.PARENTHESIS));
                            break;
                        case "]":
                            if(!In.BRACKETS.equals(actualPosition.get(actualPosition.size() - 1))) {
                                totalError += 57;
                                errOccured = true;
                                break;
                            }
                            actualPosition.remove(actualPosition.lastIndexOf(In.BRACKETS));
                            break;
                    }

                    if(errOccured) {
                        break;
                    }
                }
            }

            System.out.println("Result : " + totalError);

        } catch (Exception e) {
            System.err.println("Something went wrong :(");
            e.printStackTrace();
        }
    }

}
