import org.junit.jupiter.api.Test;
import util.FileReadHelper;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Day02Test {

    @Test
    void solve1() {
        List<String> lines = FileReadHelper.readFile("input/day02/test_input1.txt");

        int result = Day02.solve1(lines);

        assertEquals(8, result);

    }

    @Test
    void solve2() {
        List<String> lines = FileReadHelper.readFile("input/day02/test_input2.txt");

        int result = Day02.solve2(lines);

        assertEquals(2286, result);
    }
}