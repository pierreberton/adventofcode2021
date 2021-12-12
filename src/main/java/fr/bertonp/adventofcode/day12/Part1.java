package fr.bertonp.adventofcode.day12;

import java.io.FileNotFoundException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Part1 {

    static Map<String, Set<String>> caveConnections = new HashMap<>();

    static Integer pathCount = 0;

    public static void main(String[] args) {
        try {
            URL resource = Part1.class.getClassLoader().getResource("day12/input1.txt");

            if (resource == null) {
                throw new FileNotFoundException();
            }

            List<String> lines = Files.readAllLines(Paths.get(resource.toURI()), StandardCharsets.UTF_8);

            lines.forEach(Part1::createCave);
            lines.forEach(Part1::createConnections);
            findPaths("start", "end", new LinkedList<>());

            System.out.println("Result : " + pathCount);

        } catch (Exception e) {
            System.err.println("Something went wrong :(");
            e.printStackTrace();
        }
    }

    private static void findPaths(String from, String to, List<String> visited) {
        visited.add(from);
        if (from.equals(to)) {
            pathCount++;
            return;
        }

        for (var conn : caveConnections.get(from)) {
            if (canVisit(conn, visited)) {
                List<String> currentPath = new LinkedList<>(visited);
                findPaths(conn, to, visited);
                visited = new LinkedList<>(currentPath);
            }
        }
    }

    private static boolean canVisit(String conn, List<String> visited) {
        if (conn.equals("start")) {
            return false;
        }

        return conn.matches("^[A-Z]+$") || !visited.contains(conn);
    }

    private static void createConnections(String line) {
        List<String> caves = Arrays.stream(line.split("-")).collect(Collectors.toList());
        caveConnections.get(caves.get(0)).add(caves.get(1));
        caveConnections.get(caves.get(1)).add(caves.get(0));
    }

    static void createCave(String line) {
        List<String> caves = Arrays.stream(line.split("-")).collect(Collectors.toList());
        caveConnections.put(caves.get(0), new HashSet<>(Set.of(caves.get(1))));
        caveConnections.put(caves.get(1), new HashSet<>(Set.of(caves.get(0))));
    }

}
