import java.util.function.Supplier;

public class Main {
    private static void print(String day, Supplier<?> supplier) {
        long startTime = System.nanoTime();
        String result = String.valueOf(supplier.get());
        long endTime = System.nanoTime();
        System.out.println("Day " + day + ": " + result + "(" + (double) ((endTime - startTime) / 1000000) / 1000 + " мс)");
    }

    public static void main(String[] args) {
        Day1 day1 = new Day1("files/day1.txt");
        print("1/1", day1::getSum);
        print("1/2", () -> day1.getSum(true));

        Day2 day2 = new Day2("files/day2.txt");
        print("2/1", () -> day2.getSumOfIDs(12, 13, 14));
        print("2/2", day2::getSumOfPowers);

        Day3 day3 = new Day3("files/day3.txt");
        print("3/1", day3::getSumOfPartNumbers);
        print("3/2", day3::getSumOfGearRatios);

        Day4 day4 = new Day4("files/day4.txt");
        print("4/1", day4::getTotalPoints);
        print("4/2", day4::getTotalCards);
    }
}