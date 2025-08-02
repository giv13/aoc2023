import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Day4 {
    private final String filePath;
    private final List<PairOfSets> pairs = new ArrayList<>();

    public Day4(String filePath) {
        this.filePath = filePath;
        readFile();
    }

    private void readFile() {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            while ((line = reader.readLine()) != null) {
                Pattern pattern = Pattern.compile("Card +\\d+: +([\\d ]+) \\| +([\\d ]+)");
                Matcher matcher = pattern.matcher(line);
                if (matcher.matches()) {
                    PairOfSets pair = new PairOfSets(
                            stringToSet(matcher.group(1)),
                            stringToSet(matcher.group(2))
                    );
                    pairs.add(pair);
                }

            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found!");
        } catch (IOException e) {
            System.out.println("I/O error!");
        }
    }

    public int getTotalPoints() {
        return pairs.stream().mapToInt(pair -> {
            Set<Integer> retained = new HashSet<>(pair.winningNumbers);
            retained.retainAll(pair.availableNumbers);
            return 1 << retained.size() - 1;
        }).sum();
    }

    public int getTotalCards() {
        return pairs.size() + getAdditionalCards(0, pairs.size(), new HashMap<>());
    }

    private int getAdditionalCards(int from, int to, Map<Integer, Integer> cache) {
        int additionalCards = 0;
        for (int i = from; i < to; i++) {
            if (cache.containsKey(i)) {
                additionalCards += cache.get(i);
                continue;
            }
            PairOfSets pair = pairs.get(i);
            Set<Integer> retained = new HashSet<>(pair.winningNumbers);
            retained.retainAll(pair.availableNumbers);
            int additionalCardsOfPair = retained.size() + getAdditionalCards(i + 1, i + 1 + retained.size(), cache);
            cache.put(i, additionalCardsOfPair);
            additionalCards += additionalCardsOfPair;
        }
        return additionalCards;
    }

    private static Set<Integer> stringToSet(String string) {
        return Arrays.stream(string.split(" +")).map(Integer::valueOf).collect(Collectors.toSet());
    }

    private record PairOfSets(Set<Integer> winningNumbers, Set<Integer> availableNumbers) {
    }
}
