package fr.bertonp.adventofcode.day8;

import com.google.common.collect.Sets;
import com.google.common.primitives.Chars;

import java.util.*;
import java.util.stream.Collectors;

public class Digit {

    private final Map<Set<String>, Integer> numbers = new HashMap<>();

    public Digit(List<String> input) {

        Set<String> zero = new HashSet<>(),
                one = new HashSet<>(),
                two = new HashSet<>(),
                three = new HashSet<>(),
                four = new HashSet<>(),
                five = new HashSet<>(),
                six = new HashSet<>(),
                seven = new HashSet<>(),
                eight = new HashSet<>(Arrays.asList("a", "b", "c", "d", "e", "f", "g")),
                nine = new HashSet<>();

        List<Set<String>> possibly0_6or9 = new ArrayList<>();
        List<Set<String>> possibly2_3or5 = new ArrayList<>();

        for (String s : input) {
            Set<String> sSet = Chars.asList(s.toCharArray()).stream().map(String::valueOf).collect(Collectors.toSet());
            switch (s.length()) {
                case 2:
                    one = sSet;
                    break;
                case 3:
                    seven = sSet;
                    break;
                case 4:
                    four = sSet;
                    break;
                case 5:
                    possibly2_3or5.add(sSet);
                    break;
                case 6:
                    possibly0_6or9.add(sSet);
                    break;
                default:
                    break;
            }
        }

        for (Set<String> sSet : possibly0_6or9) {
            if (one.contains((String) Sets.difference(eight, sSet).toArray()[0])) {
                six = sSet;
            } else if (Sets.difference(four, sSet).size() == 0) {
                nine = sSet;
            } else {
                zero = sSet;
            }
        }

        for (Set<String> sSet : possibly2_3or5) {
            if (Sets.difference(sSet, one).size() == 3) {
                three = sSet;
            } else if (Sets.difference(sSet, nine).size() == 0){
                five = sSet;
            } else {
                two = sSet;
            }
        }

        numbers.put(zero, 0);
        numbers.put(one, 1);
        numbers.put(two, 2);
        numbers.put(three, 3);
        numbers.put(four, 4);
        numbers.put(five, 5);
        numbers.put(six, 6);
        numbers.put(seven, 7);
        numbers.put(eight, 8);
        numbers.put(nine, 9);

    }

    public Integer getNumberFromSegments(String segments) {
        Set<String> sSet = Chars.asList(segments.toCharArray()).stream().map(String::valueOf).collect(Collectors.toSet());

        return numbers.get(sSet);
    }
}
