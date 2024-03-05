import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class AddEmployeeFrame extends JFrame {
    private JTextField empIdTextField;
    private JTextField empNameTextField;
    private JTextField salaryTextField;
    private JTextField departmentTextfield;

    public AddEmployeeFrame(JFrame parentFrame, EmployeeDatabase employeeDatabase) {
        super("Add Employee");

        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBackground(new Color(255, 215, 0));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel empIdLabel = new JLabel("Employee ID:");
        empIdLabel.setFont(new Font("Arial", Font.BOLD, 16));
        empIdLabel.setForeground(Color.BLACK);
        panel.add(empIdLabel, gbc);

        gbc.gridx++;
        empIdTextField = new JTextField(10);
        empIdTextField.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(empIdTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel empNameLabel = new JLabel("Employee Name:");
        empNameLabel.setFont(new Font("Arial", Font.BOLD, 16));
        empNameLabel.setForeground(Color.BLACK);
        panel.add(empNameLabel, gbc);

        gbc.gridx++;
        empNameTextField = new JTextField(10);
        empNameTextField.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(empNameTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel salaryLabel = new JLabel("Salary:");
        salaryLabel.setFont(new Font("Arial", Font.BOLD, 16));
        salaryLabel.setForeground(Color.BLACK);
        panel.add(salaryLabel, gbc);

        gbc.gridx++;
        salaryTextField = new JTextField(10);
        salaryTextField.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(salaryTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;

        JLabel departmentLabel = new JLabel("Department:");
        departmentLabel.setFont(new Font("Arial", Font.BOLD, 16));
        departmentLabel.setForeground(Color.BLACK);
        panel.add(departmentLabel, gbc);

        gbc.gridx++;
        departmentTextfield = new JTextField(10);
        departmentTextfield.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(departmentTextfield, gbc);

        gbc.gridx = 0;
        gbc.gridy++;


        JButton saveButton = new JButton("Save");
        saveButton.setFont(new Font("Arial", Font.BOLD, 16));
        saveButton.setBackground(new Color(50, 150, 200)); // Set button background color
        saveButton.setForeground(Color.WHITE);
        saveButton.addActionListener(e -> {
            String empId = empIdTextField.getText();
            String empName = empNameTextField.getText();
            String salary = salaryTextField.getText();
            String department = departmentTextfield.getText();

            // Perform validation checks
            if (empId.isEmpty() || empName.isEmpty() || salary.isEmpty() || department.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!empId.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "Employee ID must contain only numeric characters.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!empName.matches("^[a-zA-Z]+$")) {
                JOptionPane.showMessageDialog(this, "Employee name must contain only alphabetic characters.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {

                double salaryValue = Double.parseDouble(salary);
                if (salaryValue < 0) {
                    JOptionPane.showMessageDialog(this, "Salary must be a non-negative number.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                // If all validation passes, add the employee to the database
                employeeDatabase.addEmployee(empId, empName, salary, department);

                // Display a success message
                JOptionPane.showMessageDialog(this, "Employee information saved!");
            } catch (NumberFormatException ex) {
                // Handle the case where salary is not a valid number
                JOptionPane.showMessageDialog(this, "Please enter a valid number for the salary.", "Validation Error", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                // Handle database-related errors
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error adding employee. Please try again.", "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        });


        panel.add(saveButton, gbc);

        gbc.gridx++;
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setBackground(new Color(50, 150, 200)); // Set button background color
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e -> {
            // Close the Add Employee frame and go back to the main frame
            this.dispose();
            parentFrame.setVisible(true);
        });
        panel.add(backButton, gbc);

        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);

        // Set up the frame
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parentFrame);
        setLocation(0,0);
        setVisible(true);
    }
}
