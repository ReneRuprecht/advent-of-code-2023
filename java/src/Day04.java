import util.FileReadHelper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.IntStream;

public class Day04 {
    public static void main(String[] args) {
        List<String> input1 = FileReadHelper.readFile("input/day04/input1.txt");
        int result1 = solve1(input1);
        System.out.printf("Result: %s%n", result1);

        List<String> input2 = FileReadHelper.readFile("input/day04/input2.txt");
        int result2 = solve2(input2);
        System.out.printf("Result: %s%n", result2);
    }

    public static int solve1(List<String> lines) {

        long startTime = System.nanoTime();

        int sum = 0;
        for (String line : lines) {
            HashSet<Integer> winningNums = new HashSet<>();
            HashSet<Integer> myNums = new HashSet<>();
            Arrays.stream(line.split(":")[1].split("\\|")[0].split(" "))
                  .map(String::trim)
                  .forEach(num -> {
                      if (!num.isEmpty()) {
                          winningNums.add(Integer.parseInt(num));
                      }
                  });

            Arrays.stream(line.split("\\|")[1].split(" "))
                  .map(String::trim)
                  .forEach(num -> {
                      if (!num.isEmpty()) {
                          myNums.add(Integer.parseInt(num));
                      }
                  });

            int counter = 0;
            for (Integer num : myNums) {
                if (!winningNums.contains(num)) continue;
                counter++;
            }
            if (counter == 0) continue;

            if (counter == 1) {
                sum += 1;
                continue;
            }

            if (counter > 1) {
                sum += IntStream.range(1, counter)
                                .reduce(1, (result, next) -> (result * 2));
            }
        }


        System.out.printf("ms: %s\n",
                          TimeUnit.MILLISECONDS.convert(System.nanoTime() - startTime, TimeUnit.NANOSECONDS));
        return sum;
    }

    public static int solve2(List<String> lines) {

        long startTime = System.nanoTime();
        int sum = 0;

        HashMap<Integer, Integer> finalCards = new HashMap<>();
        HashMap<Integer, Integer> cardHolder = new HashMap<>();

        int currentCard = 0;
        for (String line : lines) {
            HashSet<Integer> winningNums = new HashSet<>();
            HashSet<Integer> myNums = new HashSet<>();

            currentCard++;
            // winning nums
            Arrays.stream(line.split(":")[1].split("\\|")[0].split(" "))
                  .map(String::trim)
                  .forEach(num -> {
                      if (!num.isEmpty()) {
                          winningNums.add(Integer.parseInt(num));
                      }
                  });

            // my nums
            Arrays.stream(line.split("\\|")[1].split(" "))
                  .map(String::trim)
                  .forEach(num -> {
                      if (!num.isEmpty()) {
                          myNums.add(Integer.parseInt(num));
                      }
                  });

            int counter = 0;
            for (Integer num : myNums) {
                if (!winningNums.contains(num)) continue;
                counter++;
            }


            int cardFromHolder = 0;
            // check if card is in cardholder
            if (cardHolder.containsKey(currentCard)) {
                cardFromHolder = cardHolder.get(currentCard);
            }

            // add current card into final card
            finalCards.put(currentCard, 1 + cardFromHolder);

            // contains the amount to add for each match
            int valueToAdd = finalCards.get(currentCard);

            for (int i = currentCard + 1; i <= currentCard + counter; i++) {

                if (cardHolder.containsKey(i)) {
                    cardHolder.put(i, cardHolder.get(i) + valueToAdd);
                    continue;
                }
                cardHolder.put(i, valueToAdd);
            }

        }

        sum = finalCards.values()
                        .stream()
                        .reduce(0, Integer::sum);


        System.out.printf("ms: %s\n",
                          TimeUnit.MILLISECONDS.convert(System.nanoTime() - startTime, TimeUnit.NANOSECONDS));
        return sum;
    }


}
