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

public class Day3 {
    private final String filePath;
    private final List<String> schematic = new ArrayList<>();
    private int rowLength;

    public Day3(String filePath) {
        this.filePath = filePath;
        readFile();
    }

    private void readFile() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                schematic.add(line);
                if (rowLength == 0) rowLength = line.length();
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        } catch (IOException e) {
            System.out.println("I/O error!");
        }
    }

    public int getSumOfPartNumbers() {
        int sum = 0;
        Pattern patternNumber = Pattern.compile("\\d+");
        Pattern patternSymbol = Pattern.compile("[^\\d.]+");
        for (int i = 0; i < schematic.size(); i++) {
            String row = schematic.get(i);
            Matcher matcherNumber = patternNumber.matcher(row);
            while (matcherNumber.find()) {
                StringBuilder around = new StringBuilder();
                int start = matcherNumber.start() == 0 ? 0 : matcherNumber.start() - 1;
                int end = matcherNumber.end() == rowLength ? rowLength: matcherNumber.end() + 1;
                if (i > 0) {
                    around.append(schematic.get(i - 1), start, end);
                }
                around.append(schematic.get(i), start, end);
                if (i < schematic.size() - 1) {
                    around.append(schematic.get(i + 1), start, end);
                }
                Matcher matcherSymbol = patternSymbol.matcher(around);
                if (matcherSymbol.find()) {
                    sum += Integer.parseInt(matcherNumber.group());
                }
            }
        }
        return sum;
    }

    public int getSumOfGearRatios() {
        Map<String, List<Integer>> gears = new HashMap<>();
        Pattern patternNumber = Pattern.compile("\\d+");
        Pattern patternSymbol = Pattern.compile("\\*");
        for (int i = 0; i < schematic.size(); i++) {
            Matcher matcherNumber = patternNumber.matcher(schematic.get(i));
            while (matcherNumber.find()) {
                Map<Integer, String> around = new HashMap<>();
                int start = matcherNumber.start() == 0 ? 0 : matcherNumber.start() - 1;
                int end = matcherNumber.end() == rowLength ? rowLength: matcherNumber.end() + 1;
                if (i > 0) {
                    around.put(i - 1, schematic.get(i - 1).substring(start, end));
                }
                around.put(i, schematic.get(i).substring(start, end));
                if (i < schematic.size() - 1) {
                    around.put(i + 1, schematic.get(i + 1).substring(start, end));
                }
                for (Map.Entry<Integer, String> a : around.entrySet()) {
                    Matcher matcherSymbol = patternSymbol.matcher(a.getValue());
                    while (matcherSymbol.find()) {
                        List<Integer> gear = gears.computeIfAbsent(a.getKey() + ":" + (start + matcherSymbol.start()), g -> new ArrayList<>());
                        gear.add(Integer.parseInt(matcherNumber.group()));
                    }
                }
            }
        }
        return gears.values().stream().filter(numbers -> numbers.size() == 2).mapToInt(numbers -> numbers.getFirst() * numbers.getLast()).sum();
    }
}
