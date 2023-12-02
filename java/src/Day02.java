import util.FileReadHelper;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Day02 {
    public static void main(String[] args) {

        List<String> input1 = FileReadHelper.readFile("input/day02/input1.txt");
        int result1 = solve1_faster(input1);
        System.out.printf("Result: %s%n", result1);

        List<String> input2 = FileReadHelper.readFile("input/day02/input2.txt");
        int result2 = solve2_faster(input2);
        System.out.printf("Result: %s%n", result2);

    }

    public static int solve1(List<String> lines) {

        long startTime = System.nanoTime();

        HashMap<String, Integer> blockMap = new HashMap<>() {{
            put("red", 12);
            put("green", 13);
            put("blue", 14);

        }};
        int sum = 0;
        for (String line : lines) {
            String[] splitted = line.replaceAll(";", "")
                                    .replaceAll(",", "")
                                    .replace(":", "")
                                    .split(" ");
            boolean valid = false;
            for (int i = 0; i < splitted.length; i++) {

                if (blockMap.containsKey(splitted[i])) {
                    if (blockMap.get(splitted[i]) < Integer.parseInt(splitted[i - 1])) {
                        valid = false;
                        break;
                    }
                }
                valid = true;

            }
            if (valid) {
                sum += Integer.parseInt(splitted[1]);
            }
        }
        System.out.printf("ms: %s\n",
                          TimeUnit.MILLISECONDS.convert(System.nanoTime() - startTime, TimeUnit.NANOSECONDS));
        return sum;
    }

    public static int solve2(List<String> lines) {
        long startTime = System.nanoTime();

        int sum = 0;
        for (String line : lines) {

            HashMap<String, Integer> blockMap = new HashMap<>() {{
                put("red", 0);
                put("green", 0);
                put("blue", 0);

            }};
            String[] splitted = line.replaceAll(";", "")
                                    .replaceAll(",", "")
                                    .replace(":", "")
                                    .split(" ");
            for (int i = 0; i < splitted.length; i++) {

                if (blockMap.containsKey(splitted[i])) {
                    if (blockMap.get(splitted[i]) < Integer.parseInt(splitted[i - 1])) {
                        blockMap.replace(splitted[i], Integer.parseInt(splitted[i - 1]));
                    }
                }

            }
            sum += blockMap.values()
                           .stream()
                           .reduce(1, (a, b) -> a * b);
        }

        System.out.printf("ms: %s\n",
                          TimeUnit.MILLISECONDS.convert(System.nanoTime() - startTime, TimeUnit.NANOSECONDS));
        return sum;
    }

    public static int solve1_faster(List<String> lines) {

        long startTime = System.nanoTime();

        HashMap<String, Integer> blockMap = new HashMap<>() {{
            put("red", 12);
            put("green", 13);
            put("blue", 14);

        }};
        int sum = 0;
        for (String line : lines) {

            StringBuilder numCache = new StringBuilder();
            boolean valid = true;
            for (int i = 7; i < line.length(); i++) {

                if (Character.isDigit(line.charAt(i))) numCache.append(line.charAt(i));

                if (line.charAt(i) == 'r') {
                    if (blockMap.get("red") < Integer.parseInt(numCache.toString())) {
                        valid = false;
                        break;
                    }
                    i += 3;
                    numCache.delete(0, numCache.length());
                    continue;
                }
                if (line.charAt(i) == 'b') {
                    if (blockMap.get("blue") < Integer.parseInt(numCache.toString())) {
                        valid = false;
                        break;
                    }
                    i += 4;
                    numCache.delete(0, numCache.length());
                    continue;
                }
                if (line.charAt(i) == 'g') {
                    if (blockMap.get("green") < Integer.parseInt(numCache.toString())) {
                        valid = false;
                        break;
                    }
                    i += 5;
                    numCache.delete(0, numCache.length());
                }

            }

            if (valid) {
                sum += Integer.parseInt(line.split(" ")[1].split(":")[0]);
            }


        }
        System.out.printf("ms: %s\n",
                          TimeUnit.MILLISECONDS.convert(System.nanoTime() - startTime, TimeUnit.NANOSECONDS));
        return sum;
    }

    public static int solve2_faster(List<String> lines) {

        long startTime = System.nanoTime();

        int sum = 0;
        for (String line : lines) {
            HashMap<String, Integer> blockMap = new HashMap<>() {{
                put("red", 0);
                put("green", 0);
                put("blue", 0);

            }};
            boolean start = false;
            StringBuilder numCache = new StringBuilder();
            for (int i = 0; i < line.length(); i++) {

                if (line.charAt(i) == ':') start = true;

                if (!start) continue;


                if (Character.isDigit(line.charAt(i))) numCache.append(line.charAt(i));

                if (line.charAt(i) == 'r') {
                    if (blockMap.get("red") < Integer.parseInt(numCache.toString())) {
                        blockMap.replace("red", Integer.parseInt(numCache.toString()));
                    }
                    numCache.delete(0, numCache.length());
                    i += 3;
                    continue;
                }
                if (line.charAt(i) == 'b') {
                    if (blockMap.get("blue") < Integer.parseInt(numCache.toString())) {
                        blockMap.replace("blue", Integer.parseInt(numCache.toString()));
                    }
                    numCache.delete(0, numCache.length());
                    i += 4;
                    continue;
                }
                if (line.charAt(i) == 'g') {
                    if (blockMap.get("green") < Integer.parseInt(numCache.toString())) {
                        blockMap.replace("green", Integer.parseInt(numCache.toString()));
                    }
                    numCache.delete(0, numCache.length());
                    i += 5;
                }

            }

            sum += blockMap.values()
                           .stream()
                           .reduce(1, (a, b) -> a * b);


        }
        System.out.printf("ms: %s\n",
                          TimeUnit.MILLISECONDS.convert(System.nanoTime() - startTime, TimeUnit.NANOSECONDS));
        return sum;
    }
}