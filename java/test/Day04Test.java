import org.junit.jupiter.api.Test;
import util.FileReadHelper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day04Test {

    @Test
    void solve1() {
        List<String> lines = FileReadHelper.readFile("input/day04/test_input1.txt");

        int result = Day04.solve1(lines);

        assertEquals(13, result);
    }

    @Test
    void solve2() {
        assertEquals(1, 2);
    }
}