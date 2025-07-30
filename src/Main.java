public class Main {
    public static void main(String[] args) {
        Day1 day1 = new Day1("files/day1.txt");
        System.out.println("Day 1/1: " + day1.getSum());
        System.out.println("Day 1/2: " + day1.getSum(true));

        Day2 day2 = new Day2("files/day2.txt");
        System.out.println("Day 2/1: " + day2.getSumOfIDs(12, 13, 14));
        System.out.println("Day 2/2: " + day2.getSumOfPowers());

        Day3 day3 = new Day3("files/day3.txt");
        System.out.println("Day 3/1: " + day3.getSumOfPartNumbers());
        System.out.println("Day 3/2: " + day3.getSumOfGearRatios());
    }
}