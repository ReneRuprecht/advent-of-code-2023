import util.FileReadHelper;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class Day03 {

    public static void main(String[] args) {
        List<String> input1 = FileReadHelper.readFile("input/day03/input1.txt");
        int result1 = solve1(input1);
        System.out.printf("Result: %s%n", result1);

        List<String> input2 = FileReadHelper.readFile("input/day03/input2.txt");
        int result2 = solve2(input2);
        System.out.printf("Result: %s%n", result2);
    }

    public static int solve1(List<String> lines) {

        long startTime = System.nanoTime();

        // row, checkedIndexes
        HashMap<Integer, HashMap<Integer, Integer>> numMap = new HashMap<>();

        int row = -1;
        for (String line : lines) {
            row++;
            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);

                if (!Character.isDigit(c) && c != '.') {
                    // current line
                    putValidNumsIntoMap(numMap, line, row, i);

                    // top
                    boolean isSpaceToTop = row > 0;

                    if (isSpaceToTop) {
                        String rowOnTop = lines.get(row - 1);
                        putValidNumsIntoMap(numMap, rowOnTop, row - 1, i);
                    }

                    boolean isSpaceToBottom = row + 1 < lines.size();

                    if (isSpaceToBottom) {
                        String rowOnBottom = lines.get(row + 1);
                        putValidNumsIntoMap(numMap, rowOnBottom, row + 1, i);
                    }
                }
            }
        }

        int sum = 0;
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < numMap.keySet()
                                  .size(); i++) {
            int holder = 0;

            Map<Integer, Integer> sortedNums = new TreeMap<>(numMap.get(i));

            for (Map.Entry<Integer, Integer> entry : sortedNums.entrySet()) {
                Integer key = entry.getKey();

                if (holder != 0 && key - 1 != holder) {
                    sum += Integer.parseInt(stringBuilder.toString());
                    stringBuilder.delete(0, stringBuilder.length());
                }

                holder = key;
                Integer value = entry.getValue();
                stringBuilder.append(value);
            }
            if (stringBuilder.isEmpty()) {
                stringBuilder.append(0);
            }
            sum += Integer.parseInt(stringBuilder.toString());
            stringBuilder.delete(0, stringBuilder.length());

        }


        System.out.printf("ms: %s\n",
                          TimeUnit.MILLISECONDS.convert(System.nanoTime() - startTime, TimeUnit.NANOSECONDS));

        return sum;
    }

    public static void putValidNumsIntoMap(HashMap<Integer, HashMap<Integer, Integer>> numMap, String row, Integer rowIdx, Integer charIdx) {
        HashMap<Integer, Integer> tmp = new HashMap<>();

        boolean isSpaceToLeft = charIdx > 0;
        boolean isSpaceToRight = charIdx < row.length() - 1;

        if (Character.isDigit(row.charAt(charIdx))) {
            tmp.putAll(getMapFromPos(row, charIdx));
        }

        if (tmp.isEmpty()) {
            // left
            if (isSpaceToLeft && Character.isDigit(row.charAt(charIdx - 1))) {
                tmp.putAll(getMapFromPos(row, charIdx - 1));
            }

            //right
            if (isSpaceToRight && Character.isDigit(row.charAt(charIdx + 1))) {
                tmp.putAll(getMapFromPos(row, charIdx + 1));
            }
        }

        if (numMap.containsKey(rowIdx)) {
            tmp.putAll(numMap.get(rowIdx));
        }
        numMap.put(rowIdx, tmp);
    }

    public static HashMap<Integer, Integer> getMapFromPos(String line, Integer startIdx) {
        HashMap<Integer, Integer> tmp = new HashMap<>();
        Integer idx = startIdx;

        for (int i = startIdx; i >= 0; i--) {
            idx = i;
            if (i > line.length() - 1 || !Character.isDigit(line.charAt(i))) {
                idx = i + 1;
                break;
            }
        }

        char c = line.charAt(idx);
        while (Character.isDigit(c)) {
            tmp.put(idx, Integer.parseInt(String.valueOf(line.charAt(idx))));
            if (idx + 1 > line.length() - 1 || !Character.isDigit(line.charAt(idx + 1))) {
                break;
            }
            idx++;
            c = line.charAt(idx);
        }
        return tmp;
    }

    public static Integer getFullNumberFromPos(String line, Integer startIdx) {
        StringBuilder stringBuilder = new StringBuilder();
        Integer idx = startIdx;

        for (int i = startIdx; i >= 0; i--) {
            idx = i;
            if (i > line.length() - 1 || !Character.isDigit(line.charAt(i))) {
                idx = i + 1;
                break;
            }
        }

        char c = line.charAt(idx);
        while (Character.isDigit(c)) {
            stringBuilder.append(line.charAt(idx));
            if (idx + 1 > line.length() - 1 || !Character.isDigit(line.charAt(idx + 1))) {
                break;
            }
            idx++;
            c = line.charAt(idx);
        }
        return Integer.parseInt(stringBuilder.toString());
    }

    public static int solve2(List<String> lines) {

        long startTime = System.nanoTime();
        ArrayList<Integer> validNumbers = new ArrayList<>();
        int sum = 0;

        int row = -1;
        for (String line : lines) {
            row++;
            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);

                if (c == '*') {
                    validNumbers.clear();

                    putValidNumsIntoList(validNumbers, lines.get(row), i);

                    // top
                    boolean isSpaceOnTop = row > 0;

                    if (isSpaceOnTop) {

                        String rowOnTop = lines.get(row - 1);
                        putValidNumsIntoList(validNumbers, rowOnTop, i);
                    }

                    if (validNumbers.size() > 2) continue;

                    // bottom
                    boolean isSpaceBelow = row + 1 < lines.size();
                    if (isSpaceBelow) {

                        String rowBelow = lines.get(row + 1);
                        putValidNumsIntoList(validNumbers, rowBelow, i);

                    }
                    if (validNumbers.size() == 2) {
                        sum += validNumbers.stream()
                                           .reduce(1, (a, b) -> a * b);
                    }
                    validNumbers.clear();
                }
            }

        }

        System.out.printf("ms: %s\n",
                          TimeUnit.MILLISECONDS.convert(System.nanoTime() - startTime, TimeUnit.NANOSECONDS));

        return sum;

    }

    public static void putValidNumsIntoList(ArrayList<Integer> validNumbers, String row, Integer idx) {

        boolean isDigit = Character.isDigit(row.charAt(idx));

        if (isDigit) {
            validNumbers.add(getFullNumberFromPos(row, idx));
        } else {

            boolean isSpaceToLeft = idx > 0;
            boolean isSpaceToRight = idx < row.length() - 1;
            // left
            if (isSpaceToLeft && Character.isDigit(row.charAt(idx - 1))) {
                validNumbers.add(getFullNumberFromPos(row, idx - 1));
            }
            //right
            if (isSpaceToRight && Character.isDigit(row.charAt(idx + 1))) {
                validNumbers.add(getFullNumberFromPos(row, idx + 1));
            }
        }
    }

}
