import util.FileReadHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Day01 {
    public static void main(String[] args) {
        List<String> input1 = FileReadHelper.readFile("input/day01/input1.txt");
        int result1 = solve1(input1);
        System.out.printf("Result: %s%n", result1);

        List<String> input2 = FileReadHelper.readFile("input/day01/input2.txt");
        int result2 = solve2(input2);
        System.out.printf("Result: %s%n", result2);
    }

    // Two Pointer
    public static int solve1(List<String> lines) {

        long startTime = System.nanoTime();
        int sum = 0;
        for (String line : lines) {
            String num1 = "0";
            String num2 = "0";
            for (int i = 0; i < line.length(); i++) {

                int p2 = line.length() - 1 - i;
                if (Character.isDigit(line.charAt(i)) && num1.equals("0")) {
                    num1 = String.valueOf(line.charAt(i));
                }
                if (Character.isDigit(line.charAt(p2)) && num2.equals("0")) {
                    num2 = String.valueOf(line.charAt(p2));
                }

                if (!num1.equals("0") && !num2.equals("0")) break;
            }
            sum += Integer.parseInt(num1 + num2);

        }
        System.out.printf("ms: %s\n",
                          TimeUnit.MILLISECONDS.convert(System.nanoTime() - startTime, TimeUnit.NANOSECONDS));
        return sum;
    }

    // sliding window
    public static int solve2(List<String> lines) {
        long startTime = System.nanoTime();
        HashMap<String, Integer> numMap = new HashMap<>() {
            {
                put("one", 1);
                put("two", 2);
                put("three", 3);
                put("four", 4);
                put("five", 5);
                put("six", 6);
                put("seven", 7);
                put("eight", 8);
                put("nine", 9);
            }
        };

        int sum = 0;
        for (String line : lines) {
            String num1 = "0";
            String num2 = "0";
            for (int i = 0; i < line.length(); i++) {

                int p2 = line.length() - i - 1;

                if (num1.equals("0")) {
                    if (Character.isDigit(line.charAt(i))) {
                        num1 = String.valueOf(line.charAt(i));
                    }

                    for (Map.Entry<String, Integer> entry : numMap.entrySet()) {

                        int end = entry.getKey()
                                       .length() + i;

                        if (end > line.length()) continue;

                        String substring = line.substring(i, end);

                        if (entry.getKey()
                                 .equals(substring)) {
                            num1 = entry.getValue()
                                        .toString();
                            break;
                        }

                    }
                }

                if (num2.equals("0")) {
                    if (Character.isDigit(line.charAt(p2))) {
                        num2 = String.valueOf(line.charAt(p2));
                    }
                    for (Map.Entry<String, Integer> entry : numMap.entrySet()) {

                        int start = p2 - entry.getKey()
                                              .length() + 1;

                        if (start < 0) continue;

                        String substring = line.substring(start, p2 + 1);
                        if (entry.getKey()
                                 .equals(substring)) {
                            num2 = entry.getValue()
                                        .toString();
                            break;
                        }

                    }
                }

                if (!num1.equals("0") && !num2.equals("0")) break;
            }
            sum += Integer.parseInt(num1 + num2);
        }
        System.out.printf("ms: %s\n",
                          TimeUnit.MILLISECONDS.convert(System.nanoTime() - startTime, TimeUnit.NANOSECONDS));
        return sum;
    }

}