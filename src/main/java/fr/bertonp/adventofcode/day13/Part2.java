package fr.bertonp.adventofcode.day13;

import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Part2 {

    private static final Set<Point> points = new HashSet<>();
    private static final String PREFIX = "fold along ";

    public static void main(String[] args) {
        try {
            URL resource = Part2.class.getClassLoader().getResource("day13/input1.txt");

            if (resource == null) {
                throw new FileNotFoundException();
            }

            List<String> lines = Files.readAllLines(Paths.get(resource.toURI()), StandardCharsets.UTF_8);

            List<Folding> folds = new ArrayList<>();

            for (String line : lines) {
                if(!line.isBlank()) {
                    if (line.startsWith(PREFIX)) {
                        String[] parts = line.split(Pattern.quote(PREFIX))[1].split(Pattern.quote("="));
                        folds.add(new Folding(Integer.parseInt(parts[1]), Folding.Direction.fromString(parts[0])));
                    } else {
                        points.add(new Point(line));
                    }
                }
            }

            for (Folding folding : folds) {
                boolean horizontal = Folding.Direction.HORIZONTAL.equals(folding.getDirection());

                Set<Point> pointsToKeep = points.stream()
                        .filter(p -> (horizontal ? p.getY() : p.getX()) < folding.getCoord()).collect(Collectors.toSet());
                Set<Point> pointsToFlip = points.stream()
                        .filter(p -> (horizontal ? p.getY() : p.getX()) > folding.getCoord()).collect(Collectors.toSet());

                pointsToKeep.addAll(pointsToFlip.stream().peek(p -> {
                    if (horizontal) {
                        p.setY(folding.getCoord() - (p.getY() - folding.getCoord()));
                    } else {
                        p.setX(folding.getCoord() - (p.getX() - folding.getCoord()));
                    }
                }).collect(Collectors.toSet()));
                points.clear();
                points.addAll(pointsToKeep);
            }

            int maxX = 0;
            int maxY = 0;
            for (Point p : points) {
                maxX = Math.max(maxX, p.getX());
                maxY = Math.max(maxY, p.getY());
            }

            List<List<Character>> result = new ArrayList<>();
            for (int i = 0; i<= maxY; i++) {
                result.add(new ArrayList<>());
                for (int j = 0; j <= maxX; j++) {
                    result.get(i).add('.');
                }
            }
            points.forEach(p -> result.get(p.getY()).set(p.getX(), '#'));
            result.forEach(System.out::println);
        } catch (Exception e) {
            System.err.println("Something went wrong :(");
            e.printStackTrace();
        }
    }

}
