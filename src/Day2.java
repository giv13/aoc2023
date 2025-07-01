import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day2 {
    private final String filePath;
    private final Map<Integer, List<int[]>> games = new HashMap<>();

    public Day2(String filePath) {
        this.filePath = filePath;
        readFile();
    }

    private void readFile() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                List<int[]> sets = new ArrayList<>();
                String[] split = line.split(":\\s?");
                for (String set : split[1].split(";\\s?")) {
                    int[] cubes = new int[3];
                    int i = 0;
                    for (String color : new String[]{"red", "green", "blue"}) {
                        Pattern pattern = Pattern.compile("(\\d*) " + color);
                        Matcher matcher = pattern.matcher(set);
                        cubes[i++] = matcher.find() ? Integer.parseInt(matcher.group(1)) : 0;
                    }
                    sets.add(cubes);
                }
                Pattern pattern = Pattern.compile("Game (\\d*)");
                Matcher matcher = pattern.matcher(split[0]);
                if (matcher.find()) {
                    games.put(Integer.parseInt(matcher.group(1)), sets);
                }
            }
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        } catch (IOException e) {
            System.out.println("I/O error!");
        }
    }

    public int getSumOfIDs(int redCubes, int greenCubes, int blueCubes) {
        return games.entrySet().stream().filter(game -> game.getValue().stream().noneMatch(set -> set[0] > redCubes || set[1] > greenCubes || set[2] > blueCubes)).mapToInt(Map.Entry::getKey).sum();
    }

    public int getSumOfPowers() {
        return games.values().stream().mapToInt(game -> game.stream().mapToInt(set -> set[0]).max().orElse(0) * game.stream().mapToInt(set -> set[1]).max().orElse(0) * game.stream().mapToInt(set -> set[2]).max().orElse(0)).sum();
    }
}
