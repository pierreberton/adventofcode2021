package fr.bertonp.adventofcode.day5;

import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class Part2 {

    public static void main(String[] args) {
        try {
            URL resource = Part2.class.getClassLoader().getResource("day5/input1.txt");

            if (resource == null) {
                throw new FileNotFoundException();
            }

            List<String> lines = Files.readAllLines(Paths.get(resource.toURI()), StandardCharsets.UTF_8);

            List<Point> points = new ArrayList<>();
            for(String line : lines) {
                String[] lineParts = line.split(Pattern.quote(" -> "));
                Point p1 = new Point(lineParts[0]);
                Point p2 = new Point(lineParts[1]);

                List<Point> pointsBetweenTwoPoints = pointsBetweenTwoPoints(p1, p2);

                points.add(p1);
                points.add(p2);
                points.addAll(pointsBetweenTwoPoints);
            }

            Map<Point, Integer> countPoints = new HashMap<>();

            for (Point p : points) {
                countPoints.put(p, countPoints.getOrDefault(p, 0) + 1);
            }

            int count = (int) countPoints.values().stream().filter(v -> v >= 2).count();

            System.out.println("Result : " + count);

        } catch (Exception e) {
            System.err.println("Something went wrong :(");
            e.printStackTrace();
        }
    }

    private static List<Point> pointsBetweenTwoPoints(Point p1, Point p2) {
        List<Point> points = new ArrayList<>();
        if (p1.getY() == p2.getY()) {
            if(p1.getX() > p2.getX()) {
                for (int i = p2.getX() + 1; i < p1.getX(); i++) {
                    points.add(new Point(i, p1.getY()));
                }
            } else {
                for (int i = p1.getX() + 1; i < p2.getX(); i++) {
                    points.add(new Point(i, p1.getY()));
                }
            }
        } else if (p1.getX() == p2.getX()) {
            if(p1.getY() > p2.getY()) {
                for (int i = p2.getY() + 1; i < p1.getY(); i++) {
                    points.add(new Point(p1.getX(), i));
                }
            } else {
                for (int i = p1.getY() + 1; i < p2.getY(); i++) {
                    points.add(new Point(p1.getX(), i));
                }
            }
        } else {
            if((p1.getX() > p2.getX() && p1.getY() > p2.getY()) ||
                    (p1.getX() < p2.getX() && p1.getY() < p2.getY())) {
                for (int i = 1; i < Math.abs(p1.getX() - p2.getX()); i++) {
                    points.add(new Point(Math.min(p2.getX(), p1.getX()) + i, Math.min(p2.getY(), p1.getY()) + i));
                }
            } else if (p1.getX() > p2.getX() && p1.getY() < p2.getY()){
                for (int i = 1; i < p1.getX() - p2.getX(); i++) {
                    points.add(new Point(p1.getX() - i, p1.getY() + i));
                }
            } else if (p1.getX() < p2.getX() && p1.getY() > p2.getY()){
                for (int i = 1; i < p2.getX() - p1.getX(); i++) {
                    points.add(new Point(p1.getX() + i, p1.getY() - i));
                }
            }
        }

        return points;
    }

}
