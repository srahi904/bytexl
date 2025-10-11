import java.sql.*;
import java.util.*;

class Student {
    private int studentId;
    private String name;
    private String department;
    private double marks;

    public Student() {}

    public Student(int studentId, String name, String department, double marks) {
        this.studentId = studentId;
        this.name = name;
        this.department = department;
        this.marks = marks;
    }

    public Student(String name, String department, double marks) {
        this.name = name;
        this.department = department;
        this.marks = marks;
    }

    public int getStudentId() { return studentId; }
    public String getName() { return name; }
    public String getDepartment() { return department; }
    public double getMarks() { return marks; }

    public void setStudentId(int studentId) { this.studentId = studentId; }
    public void setName(String name) { this.name = name; }
    public void setDepartment(String department) { this.department = department; }
    public void setMarks(double marks) { this.marks = marks; }

    @Override
    public String toString() {
        return "Student [ID=" + studentId + ", Name=" + name + ", Dept=" + department + ", Marks=" + marks + "]";
    }
}

class StudentController {
    private Connection con;

    public StudentController() {
        try {
            // Load MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to your local database
            con = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/studentdb",
                "root",
                "your_password" // <-- change this to your MySQL password
            );

        } catch (Exception e) {
            System.out.println("❌ Database connection failed!");
            e.printStackTrace();
        }
    }

    public void addStudent(Student s) {
        String query = "INSERT INTO students(name, department, marks) VALUES (?, ?, ?)";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, s.getName());
            ps.setString(2, s.getDepartment());
            ps.setDouble(3, s.getMarks());
            ps.executeUpdate();
            System.out.println("✅ Student added successfully!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Student> getAllStudents() {
        List<Student> list = new ArrayList<>();
        String query = "SELECT * FROM students";
        try (Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(query)) {
            while (rs.next()) {
                Student s = new Student(
                    rs.getInt("student_id"),
                    rs.getString("name"),
                    rs.getString("department"),
                    rs.getDouble("marks")
                );
                list.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public void updateStudent(int id, double newMarks) {
        String query = "UPDATE students SET marks = ? WHERE student_id = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setDouble(1, newMarks);
            ps.setInt(2, id);
            int rows = ps.executeUpdate();
            if (rows > 0) System.out.println("✅ Student updated successfully!");
            else System.out.println("⚠️ No student found with that ID.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteStudent(int id) {
        String query = "DELETE FROM students WHERE student_id = ?";
        try (PreparedStatement ps = con.prepareStatement(query)) {
            ps.setInt(1, id);
            int rows = ps.executeUpdate();
            if (rows > 0) System.out.println("🗑️ Student deleted successfully!");
            else System.out.println("⚠️ No student found with that ID.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

public class StudentManagementApp {
    private static Scanner sc = new Scanner(System.in);
    private static StudentController controller = new StudentController();

    public static void main(String[] args) {
        while (true) {
            System.out.println("\n=== Student Management System ===");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Update Student Marks");
            System.out.println("4. Delete Student");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1 -> addStudentUI();
                case 2 -> viewStudentsUI();
                case 3 -> updateStudentUI();
                case 4 -> deleteStudentUI();
                case 5 -> {
                    System.out.println("👋 Exiting... Goodbye!");
                    System.exit(0);
                }
                default -> System.out.println("⚠️ Invalid choice!");
            }
        }
    }

    private static void addStudentUI() {
        System.out.print("Enter name: ");
        String name = sc.nextLine();
        System.out.print("Enter department: ");
        String dept = sc.nextLine();
        System.out.print("Enter marks: ");
        double marks = sc.nextDouble();
        sc.nextLine();
        Student s = new Student(name, dept, marks);
        controller.addStudent(s);
    }

    private static void viewStudentsUI() {
        List<Student> list = controller.getAllStudents();
        if (list.isEmpty()) System.out.println("No students found.");
        else list.forEach(System.out::println);
    }

    private static void updateStudentUI() {
        System.out.print("Enter student ID to update: ");
        int id = sc.nextInt();
        System.out.print("Enter new marks: ");
        double marks = sc.nextDouble();
        sc.nextLine();
        controller.updateStudent(id, marks);
    }

    private static void deleteStudentUI() {
        System.out.print("Enter student ID to delete: ");
        int id = sc.nextInt();
        sc.nextLine();
        controller.deleteStudent(id);
    }
}

CREATE DATABASE studentdb;
USE studentdb;

CREATE TABLE students (
    student_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100),
    department VARCHAR(100),
    marks DOUBLE
);
