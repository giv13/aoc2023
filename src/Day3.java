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

    private Map<String, List<Integer>> findAdjacentNumbers(String regexNumber, String regexSymbol) {
        Map<String, List<Integer>> symbols = new HashMap<>();
        Pattern patternNumber = Pattern.compile(regexNumber);
        Pattern patternSymbol = Pattern.compile(regexSymbol);
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
                        List<Integer> symbol = symbols.computeIfAbsent(a.getKey() + ":" + (start + matcherSymbol.start()), g -> new ArrayList<>());
                        symbol.add(Integer.parseInt(matcherNumber.group()));
                    }
                }
            }
        }
        return symbols;
    }


    public int getSumOfPartNumbers() {
        return findAdjacentNumbers("\\d+", "[^\\d.]+").values().stream().mapToInt(numbers -> numbers.stream().mapToInt(Integer::intValue).sum()).sum();
    }

    public int getSumOfGearRatios() {
        return findAdjacentNumbers("\\d+", "\\*").values().stream().filter(numbers -> numbers.size() == 2).mapToInt(numbers -> numbers.getFirst() * numbers.getLast()).sum();
    }
}
