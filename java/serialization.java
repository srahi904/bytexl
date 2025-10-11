import java.io.*;
import java.util.*;

class Student implements Serializable {
    int id;
    String name;
    double gpa;
    Student(int id, String name, double gpa) {
        this.id = id;
        this.name = name;
        this.gpa = gpa;
    }
}

public class StudentSerialization {
    public static void main(String[] args) {
        Student s = new Student(101, "Alice", 9.1);
        try {
            FileOutputStream fos = new FileOutputStream("student.ser");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(s);
            oos.close();
            fos.close();
            System.out.println("Student serialized successfully!");
        } catch (IOException e) {
            System.out.println(e);
        }
        try {
            FileInputStream fis = new FileInputStream("student.ser");
            ObjectInputStream ois = new ObjectInputStream(fis);
            Student obj = (Student) ois.readObject();
            ois.close();
            fis.close();
            System.out.println("Student deserialized:");
            System.out.println("ID: " + obj.id);
            System.out.println("Name: " + obj.name);
            System.out.println("GPA: " + obj.gpa);
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }
    }
}
