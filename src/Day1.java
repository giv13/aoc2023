import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day1 {
    private final String filePath;
    private final List<String> rows = new ArrayList<>();
    private final Map<String, Integer> numbers = new HashMap<>(Map.of("one", 1, "two", 2, "three", 3, "four", 4, "five", 5, "six", 6, "seven", 7, "eight", 8,"nine", 9));

    public Day1(String filePath) {
        this.filePath = filePath;
        readFile();
    }

    private void readFile() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                rows.add(line);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        } catch (IOException e) {
            System.out.println("I/O error!");
        }
    }

    public int getSum() {
        return getSum(false);
    }

    public int getSum(boolean withWords) {
        String regex = "\\d" + (withWords ? "|" + String.join("|", numbers.keySet()) : "");
        return rows.stream().mapToInt(row -> findNumber(row, regex)).sum();
    }

    private int findNumber(String row, String regex) {
        return Integer.parseInt(findNumber(row, regex, false) + findNumber(row, regex, true));
    }

    private String findNumber(String row, String regex, boolean backward) {
        int start = backward ? row.length() - 1 : 0;
        int end = backward ? row.length() : 1;
        while (!backward && end <= row.length() || backward && start >= 0) {
            String subrow = row.substring(start, end);
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(subrow);
            if (matcher.find()) {
                return numbers.containsKey(matcher.group()) ? String.valueOf(numbers.get(matcher.group())) : matcher.group();
            }
            if (backward) {
                start--;
            } else {
                end++;
            }
        }
        return "0";
    }
}
