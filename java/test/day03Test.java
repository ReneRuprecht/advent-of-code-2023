import org.junit.jupiter.api.Test;
import util.FileReadHelper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day03Test {

    @Test
    void solve1() {
        List<String> input1 = FileReadHelper.readFile("input/day03/test_input1.txt");
        int result = Day03.solve1(input1);

        assertEquals(4361, result);
    }

    @Test
    void solve2() {
        List<String> input2 = FileReadHelper.readFile("input/day03/test_input2.txt");
        int result = Day03.solve1(input2);

        assertEquals(467835, result);
    }
}