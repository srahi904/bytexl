import java.sql.*;

public class FetchEmployeeData {
    public static void main(String[] args) {
        // Database credentials
        String url = "jdbc:mysql://localhost:3306/testdb"; // replace testdb with your DB name
        String user = "root"; // replace with your MySQL username
        String password = "yourpassword"; // replace with your MySQL password

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            // 1️⃣ Load and Register JDBC Driver (optional for newer versions)
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2️⃣ Establish Connection
            conn = DriverManager.getConnection(url, user, password);
            System.out.println("✅ Database Connected Successfully!");

            // 3️⃣ Create Statement
            stmt = conn.createStatement();

            // 4️⃣ Execute Query
            String query = "SELECT EmpID, Name, Salary FROM Employee";
            rs = stmt.executeQuery(query);

            // 5️⃣ Display Results
            System.out.println("\n--- Employee Records ---");
            while (rs.next()) {
                int id = rs.getInt("EmpID");
                String name = rs.getString("Name");
                double salary = rs.getDouble("Salary");

                System.out.printf("ID: %d | Name: %s | Salary: %.2f%n", id, name, salary);
            }

        } catch (ClassNotFoundException e) {
            System.out.println("❌ JDBC Driver not found!");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("❌ Database error!");
            e.printStackTrace();
        } finally {
            // 6️⃣ Close resources
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

CREATE DATABASE testdb;
USE testdb;

CREATE TABLE Employee (
    EmpID INT PRIMARY KEY,
    Name VARCHAR(100),
    Salary DOUBLE
);

INSERT INTO Employee VALUES
(1, 'Rahi', 50000),
(2, 'Tamanna', 60000),
(3, 'Aman', 55000);


