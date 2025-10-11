import java.util.*;

class Employee {
    String name;
    int age;
    double salary;
    Employee(String name, int age, double salary) {
        this.name = name;
        this.age = age;
        this.salary = salary;
    }
    public String toString() {
        return name + " - " + age + " - " + salary;
    }
}

public class EmployeeSorting {
    public static void main(String[] args) {
        List<Employee> list = new ArrayList<>();
        list.add(new Employee("John", 25, 50000));
        list.add(new Employee("Alice", 30, 70000));
        list.add(new Employee("Bob", 22, 45000));

        System.out.println("Sort by Name:");
        list.sort((e1, e2) -> e1.name.compareTo(e2.name));
        list.forEach(System.out::println);

        System.out.println("\nSort by Age:");
        list.sort((e1, e2) -> Integer.compare(e1.age, e2.age));
        list.forEach(System.out::println);

        System.out.println("\nSort by Salary (Descending):");
        list.sort((e1, e2) -> Double.compare(e2.salary, e1.salary));
        list.forEach(System.out::println);
    }
}
