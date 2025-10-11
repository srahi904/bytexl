import java.util.*;
import java.util.stream.*;

class Student {
    String name;
    double marks;
    Student(String name, double marks) {
        this.name = name;
        this.marks = marks;
    }
}

public class StudentFilterSort {
    public static void main(String[] args) {
        List<Student> list = Arrays.asList(
            new Student("Ravi", 80),
            new Student("Asha", 60),
            new Student("Kiran", 90),
            new Student("Meena", 76)
        );

        List<String> result = list.stream()
            .filter(s -> s.marks > 75)
            .sorted((s1, s2) -> Double.compare(s2.marks, s1.marks))
            .map(s -> s.name)
            .collect(Collectors.toList());

        result.forEach(System.out::println);
    }
}
