import util.FileReadHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

public class Day03 {

    public static void main(String[] args) {
        List<String> input1 = FileReadHelper.readFile("input/day03/input1.txt");
        int result1 = solve1(input1);
        System.out.printf("Result: %s%n", result1);

    }

    public static int solve1(List<String> lines) {

        long startTime = System.nanoTime();

        // row, checkedIndexes
        HashMap<Integer, HashMap<Integer, Integer>> numMap = new HashMap<>();

        int row = -1;
        for (String line : lines) {
            row++;
            HashMap<Integer, Integer> validNums = new HashMap<>();
            for (int i = 0; i < line.length(); i++) {
                char c = line.charAt(i);

                if (!Character.isDigit(c) && c != '.') {

                    boolean isSpaceToLeft = i > 0;

                    boolean isSpaceToRight = i < line.length() - 1;

                    // left
                    if (isSpaceToLeft && Character.isDigit(line.charAt(i - 1))) {
                        validNums.putAll(getMapFromPos(line, i - 1));
                    }

                    //right
                    if (isSpaceToRight && Character.isDigit(line.charAt(i + 1))) {
                        validNums.putAll(getMapFromPos(line, i + 1));
                    }

                    // top
                    boolean isSpaceToTop = row > 0;

                    if (isSpaceToTop) {

                        HashMap<Integer, Integer> tmp = new HashMap<>();
                        String rowOnTop = lines.get(row - 1);
                        // left
                        if (isSpaceToLeft && Character.isDigit(rowOnTop.charAt(i - 1))) {
                            tmp.putAll(getMapFromPos(rowOnTop, i - 1));
                        }

                        //right
                        if (isSpaceToRight && Character.isDigit(rowOnTop.charAt(i + 1))) {
                            tmp.putAll(getMapFromPos(rowOnTop, i + 1));
                        }

                        if (Character.isDigit(rowOnTop.charAt(i))) {
                            tmp.putAll(getMapFromPos(rowOnTop, i));
                        }
                        tmp.putAll(numMap.get(row - 1));
                        numMap.put(row - 1, tmp);
                    }


                    boolean isSpaceToBottom = row + 1 < lines.size();

                    if (isSpaceToBottom) {

                        HashMap<Integer, Integer> tmp = new HashMap<>();
                        String rowOnBottom = lines.get(row + 1);
                        // left
                        if (isSpaceToLeft && Character.isDigit(rowOnBottom.charAt(i - 1))) {
                            tmp.putAll(getMapFromPos(rowOnBottom, i - 1));
                        }

                        //right
                        if (isSpaceToRight && Character.isDigit(rowOnBottom.charAt(i + 1))) {
                            tmp.putAll(getMapFromPos(rowOnBottom, i + 1));
                        }

                        if (Character.isDigit(rowOnBottom.charAt(i))) {
                            tmp.putAll(getMapFromPos(rowOnBottom, i));
                        }


                        if (numMap.containsKey(row + 1)) {
                            tmp.putAll(numMap.get(row + 1));
                        }
                        numMap.put(row + 1, tmp);
                    }
                }
            }
            if (numMap.containsKey(row)) {
                HashMap<Integer, Integer> tmp = new HashMap<>();
                tmp.putAll(validNums);
                tmp.putAll(numMap.get(row));
                numMap.put(row, tmp);
            } else {
                numMap.put(row, validNums);
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


    public static int solve2(List<String> lines) {
        return 0;
    }
}
