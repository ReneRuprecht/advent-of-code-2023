package util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileReadHelper {

    public static List<String> readFile(String filePath) {

        BufferedReader reader;
        ArrayList<String> lines = new ArrayList<>();

        try {
            reader = new BufferedReader(new FileReader(filePath));
            String line = reader.readLine();

            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }

            reader.close();
            return lines;
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
        return lines;

    }
}
