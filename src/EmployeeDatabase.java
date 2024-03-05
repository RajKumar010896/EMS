import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EmployeeDatabase {

    private Connection connection;

    // Constructor that takes a database connection
    public EmployeeDatabase(Connection connection) {
        this.connection = connection;
    }

    // Method to add an employee to the database
    public void addEmployee(String empId, String empName, String salary, String department) throws SQLException {
        // The SQL query to insert an employee
        String sql = "INSERT INTO employee (emp_id, emp_name, salary, department) VALUES (?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            // Set values for the parameters in the query
            preparedStatement.setString(1, empId);
            preparedStatement.setString(2, empName);
            preparedStatement.setString(3, salary);
            preparedStatement.setString(4, department);

            // Execute the query
            preparedStatement.executeUpdate();
        }
    }

    public String viewEmployees() throws SQLException {
        StringBuilder result = new StringBuilder();
        String sql = "SELECT * FROM employee";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql);
             var resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                String empId = resultSet.getString("emp_id");
                String empName = resultSet.getString("emp_name");
                String salary = resultSet.getString("salary");
                String department = resultSet.getString("Department");

                result.append("Emp ID: ").append(empId)
                        .append(", Emp Name: ").append(empName)
                        .append(", Salary: ").append(salary)
                        .append(", Department: ").append(department)
                        .append("\n");
            }
        }
        return result.toString();
    }

    // Method to update an employee's information in the database
    public void updateEmployee(String empId, String empName, String newSalary,String department) throws SQLException {
        String sql = "UPDATE employee SET emp_name=?, salary=?, department=? WHERE emp_id=?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, empName);
            preparedStatement.setString(2, newSalary);
            preparedStatement.setString(3, department);
            preparedStatement.setString(4, empId);

            preparedStatement.executeUpdate();
        }
    }

    // Method to delete an employee from the database
    public void deleteEmployee(String empId) throws SQLException {
        String sql = "DELETE FROM employee WHERE emp_id = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, empId);

            preparedStatement.executeUpdate();
        }
    }


}


