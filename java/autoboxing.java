import java.util.*;

public class SumCalculator {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter numbers: ");
        String input = sc.nextLine();
        String[] parts = input.split(",");
        ArrayList<Integer> numbers = new ArrayList<>();
        for (String part : parts) {
            numbers.add(Integer.parseInt(part.trim()));
        }
        int sum = 0;
        for (Integer num : numbers) {
            sum += num;
        }
        System.out.println("Sum of numbers = " + sum);
    }
}
