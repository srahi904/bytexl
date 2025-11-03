package com.example.controller;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import java.util.List;
import java.util.Scanner;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class Main {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        StudentService studentService = context.getBean(StudentService.class);
        PaymentService paymentService = context.getBean(PaymentService.class);

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.println("\n--- Student Management Menu ---");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Delete Student");
            System.out.println("4. Pay Fees");
            System.out.println("5. Refund Fees");
            System.out.println("6. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1:
                    Student s = new Student();
                    System.out.print("Enter Name: "); s.setName(sc.next());
                    System.out.print("Enter Email: "); s.setEmail(sc.next());
                    System.out.print("Enter Course: "); s.setCourse(sc.next());
                    System.out.print("Enter Fees: "); s.setFees(sc.nextDouble());
                    studentService.addOrUpdate(s);
                    System.out.println("✅ Student added successfully!");
                    break;

                case 2:
                    List<Student> list = studentService.getAll();
                    list.forEach(System.out::println);
                    break;

                case 3:
                    System.out.print("Enter ID to delete: ");
                    studentService.delete(sc.nextInt());
                    System.out.println("🗑️ Student deleted.");
                    break;

                case 4:
                    System.out.print("Enter Student ID: ");
                    int sid = sc.nextInt();
                    System.out.print("Enter Amount: ");
                    double amt = sc.nextDouble();
                    paymentService.payFees(sid, amt);
                    System.out.println("💸 Payment successful.");
                    break;

                case 5:
                    System.out.print("Enter Student ID: ");
                    sid = sc.nextInt();
                    System.out.print("Enter Refund Amount: ");
                    amt = sc.nextDouble();
                    try {
                        paymentService.refundFees(sid, amt);
                        System.out.println("💰 Refund processed.");
                    } catch (Exception e) {
                        System.out.println("⚠️ Transaction rolled back: " + e.getMessage());
                    }
                    break;

                case 6:
                    System.out.println("👋 Exiting...");
                    context.close();
                    sc.close();
                    System.exit(0);
            }
        }
    }
}
