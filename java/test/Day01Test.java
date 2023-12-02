import org.junit.jupiter.api.Test;
import util.FileReadHelper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day01Test {

    @Test
    void solve1() {
        List<String> lines = FileReadHelper.readFile("input/day01/test_input1.txt");

        int result = Day01.solve1(lines);

        assertEquals(142, result);
    }

    @Test
    void solve2() {
        List<String> lines = FileReadHelper.readFile("input/day01/test_input2.txt");

        int result = Day01.solve2(lines);

        assertEquals(281, result);
    }
}