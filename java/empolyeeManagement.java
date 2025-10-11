import java.io.*;
import java.util.*;

class Employee {
    String name;
    int id;
    String designation;
    double salary;
    Employee(String name, int id, String designation, double salary) {
        this.name = name;
        this.id = id;
        this.designation = designation;
        this.salary = salary;
    }
    @Override
    public String toString() {
        return name + " | " + id + " | " + designation + " | " + salary;
    }
}

public class EmployeeManagement {
    static final String FILE_NAME = "employees.txt";

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("Menu:");
            System.out.println("1. Add Employee");
            System.out.println("2. Display All");
            System.out.println("3. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();
            if (choice == 1) {
                System.out.print("Name: ");
                String name = sc.nextLine();
                System.out.print("ID: ");
                int id = sc.nextInt();
                sc.nextLine();
                System.out.print("Designation: ");
                String designation = sc.nextLine();
                System.out.print("Salary: ");
                double salary = sc.nextDouble();
                sc.nextLine();
                Employee emp = new Employee(name, id, designation, salary);
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
                    bw.write(emp.toString());
                    bw.newLine();
                } catch (IOException e) {
                    System.out.println(e);
                }
                System.out.println("Employee added successfully!");
            } else if (choice == 2) {
                try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
                    String line;
                    System.out.println("Employee List:");
                    while ((line = br.readLine()) != null) {
                        System.out.println(line);
                    }
                } catch (IOException e) {
                    System.out.println(e);
                }
            } else if (choice == 3) {
                System.out.println("Exiting...");
                break;
            } else {
                System.out.println("Invalid choice");
            }
        }
    }
}
