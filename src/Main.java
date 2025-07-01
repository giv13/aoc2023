public class Main {
    public static void main(String[] args) {
        Day1 day1 = new Day1("files/day1.txt");
        System.out.println("Day 1/1: " + day1.getSum());
        System.out.println("Day 1/2: " + day1.getSum(true));
    }
}