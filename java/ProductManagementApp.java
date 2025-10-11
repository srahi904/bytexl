import java.sql.*;
import java.util.*;

public class ProductManagementApp {

    // Database Connection Object
    private static Connection con;

    // Initialize connection
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/productdb",
                    "root",
                    "your_password" // <-- replace with your MySQL password
            );
        } catch (Exception e) {
            System.out.println("❌ Database connection failed!");
            e.printStackTrace();
        }
    }

    // Main Program
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== PRODUCT MANAGEMENT SYSTEM ===");
            System.out.println("1. Add Product");
            System.out.println("2. View All Products");
            System.out.println("3. Update Product");
            System.out.println("4. Delete Product");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine(); // consume newline

            switch (choice) {
                case 1 -> addProduct(sc);
                case 2 -> viewAllProducts();
                case 3 -> updateProduct(sc);
                case 4 -> deleteProduct(sc);
                case 5 -> {
                    System.out.println("👋 Exiting... Goodbye!");
                    System.exit(0);
                }
                default -> System.out.println("⚠️ Invalid choice, try again!");
            }
        }
    }

    // 1️⃣ CREATE Operation
    private static void addProduct(Scanner sc) {
        try {
            System.out.print("Enter Product Name: ");
            String name = sc.nextLine();
            System.out.print("Enter Price: ");
            double price = sc.nextDouble();
            System.out.print("Enter Quantity: ");
            int quantity = sc.nextInt();

            String query = "INSERT INTO product (ProductName, Price, Quantity) VALUES (?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, name);
            ps.setDouble(2, price);
            ps.setInt(3, quantity);

            int rows = ps.executeUpdate();
            if (rows > 0)
                System.out.println("✅ Product added successfully!");
            else
                System.out.println("❌ Failed to add product.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 2️⃣ READ Operation
    private static void viewAllProducts() {
        try {
            String query = "SELECT * FROM product";
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery(query);

            System.out.println("\n🛒 Product List:");
            System.out.println("------------------------------------------------------");
            System.out.printf("%-10s %-25s %-10s %-10s%n", "ID", "Name", "Price", "Qty");
            System.out.println("------------------------------------------------------");

            while (rs.next()) {
                int id = rs.getInt("ProductID");
                String name = rs.getString("ProductName");
                double price = rs.getDouble("Price");
                int qty = rs.getInt("Quantity");
                System.out.printf("%-10d %-25s %-10.2f %-10d%n", id, name, price, qty);
            }

            System.out.println("------------------------------------------------------");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // 3️⃣ UPDATE Operation with Transaction
    private static void updateProduct(Scanner sc) {
        try {
            con.setAutoCommit(false); // start transaction

            System.out.print("Enter Product ID to update: ");
            int id = sc.nextInt();
            sc.nextLine();

            System.out.print("Enter new Product Name: ");
            String name = sc.nextLine();
            System.out.print("Enter new Price: ");
            double price = sc.nextDouble();
            System.out.print("Enter new Quantity: ");
            int qty = sc.nextInt();

            String query = "UPDATE product SET ProductName=?, Price=?, Quantity=? WHERE ProductID=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, name);
            ps.setDouble(2, price);
            ps.setInt(3, qty);
            ps.setInt(4, id);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                con.commit();
                System.out.println("✅ Product updated successfully!");
            } else {
                con.rollback();
                System.out.println("⚠️ No product found with that ID.");
            }

            con.setAutoCommit(true); // restore auto-commit mode

        } catch (Exception e) {
            try {
                con.rollback();
                System.out.println("❌ Update failed, rolled back!");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    // 4️⃣ DELETE Operation with Transaction
    private static void deleteProduct(Scanner sc) {
        try {
            con.setAutoCommit(false); // start transaction

            System.out.print("Enter Product ID to delete: ");
            int id = sc.nextInt();

            String query = "DELETE FROM product WHERE ProductID=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                con.commit();
                System.out.println("🗑️ Product deleted successfully!");
            } else {
                con.rollback();
                System.out.println("⚠️ No product found with that ID.");
            }

            con.setAutoCommit(true);

        } catch (Exception e) {
            try {
                con.rollback();
                System.out.println("❌ Delete failed, rolled back!");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }
}


CREATE DATABASE productdb;

USE productdb;

CREATE TABLE product (
    ProductID INT PRIMARY KEY AUTO_INCREMENT,
    ProductName VARCHAR(100),
    Price DOUBLE,
    Quantity INT
);
