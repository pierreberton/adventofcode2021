package fr.bertonp.adventofcode.day9;

import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Part2 {

    private static final List<List<Integer>> matrix = new ArrayList<>();

    public static void main(String[] args) {
        try {
            URL resource = Part2.class.getClassLoader().getResource("day9/input1.txt");

            if (resource == null) {
                throw new FileNotFoundException();
            }

            List<String> lines = Files.readAllLines(Paths.get(resource.toURI()), StandardCharsets.UTF_8);

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

            List<Point> lowPoints = new ArrayList<>();
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
                        lowPoints.add(new Point(j, i, elem));
                    }
                }
            }

            Map<Point, Integer> basins = new HashMap<>();

            List<Point> testedPoints = new ArrayList<>();

            for (Point p : lowPoints) {
                Set<Point> remainingPoints = getNeighbours(p, testedPoints);
                testedPoints.add(p);
                int sum = 1;
                while (remainingPoints.size() > 0) {
                    Point removed = remainingPoints.stream().findFirst().get();
                    remainingPoints.remove(removed);
                    testedPoints.add(removed);
                    remainingPoints.addAll(getNeighbours(removed, testedPoints));
                    sum++;
                }
                basins.put(p, sum);
            }

            List<Integer> maxs = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                Point maxPoint = null;
                int max = 0;
                for (Point p : basins.keySet()) {
                    if(basins.get(p) > max) {
                        max = basins.get(p);
                        maxPoint = p;
                    }
                }
                basins.remove(maxPoint);
                maxs.add(max);
            }

            System.out.println("Result : " + maxs.stream().reduce((a, b) -> a * b));

        } catch (Exception e) {
            System.err.println("Something went wrong :(");
            e.printStackTrace();
        }
    }

    public enum Direction {
        N, S, E, O
    }

    private static Set<Point> getNeighbours(Point p, List<Point> testedPoints) {
        Set<Point> neighbours = new HashSet<>();
        for (Direction d : Direction.values()) {
            Point neighbour = getPointFromDirection(p, d);
            if (neighbour.getValue() < 9 && !testedPoints.contains(neighbour)) {
                neighbours.add(neighbour);
            }
        }
        return neighbours;
    }

    private static Point getPointFromDirection(Point p, Direction d) {
        switch (d) {
            case N:
                return new Point(p.getX(), p.getY() - 1, matrix.get(p.getY() - 1).get(p.getX()));
            case S:
                return new Point(p.getX(), p.getY() + 1, matrix.get(p.getY() + 1).get(p.getX()));
            case E:
                return new Point(p.getX() + 1, p.getY(), matrix.get(p.getY()).get(p.getX() + 1));
            case O:
                return new Point(p.getX() - 1, p.getY(), matrix.get(p.getY()).get(p.getX() - 1));
        }

        return p;
    }

}
