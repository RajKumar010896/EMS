import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class UpdateEmployeeFrame extends JFrame {
    private JTextField empIdTextField;
    private JTextField empNameTextField;
    private JTextField newsalaryTextField;
    private JTextField departmentTextField;
    private EmployeeDatabase employeeDatabase;

    public UpdateEmployeeFrame(JFrame parentFrame,EmployeeDatabase employeeDatabase) {
        super("Update Employee");
        this.employeeDatabase = employeeDatabase;

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
        newsalaryTextField = new JTextField(10);
        newsalaryTextField.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(newsalaryTextField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        JLabel departmentLabel = new JLabel("Department:");
        departmentLabel.setFont(new Font("Arial", Font.BOLD, 16));
        departmentLabel.setForeground(Color.BLACK);
        panel.add(departmentLabel, gbc);

        gbc.gridx++;
        departmentTextField = new JTextField(10);
        departmentTextField.setFont(new Font("Arial", Font.PLAIN, 16));
        panel.add(departmentTextField, gbc);


        gbc.gridx = 0;
        gbc.gridy++;
        JButton saveButton = new JButton("Update");
        saveButton.setFont(new Font("Arial", Font.BOLD, 16));
        saveButton.setBackground(new Color(50, 150, 200));
        saveButton.setForeground(Color.WHITE);
        saveButton.addActionListener(e -> {
            String empId = empIdTextField.getText();
            String empName = empNameTextField.getText();
            String newSalary = newsalaryTextField.getText();
            String department = departmentTextField.getText();

                // Validate empId: Allow only numeric characters
                if (!empId.matches("\\d+")) {
                    JOptionPane.showMessageDialog(this, "Employee ID must contain only numeric characters.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validate empName: Allow only alphabetic characters
                if (!empName.matches("^[a-zA-Z]+$")) {
                    JOptionPane.showMessageDialog(this, "Employee name must contain only alphabetic characters.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validate newSalary: Allow only numeric characters
                try {
                    double salaryValue = Double.parseDouble(newSalary);
                    if (salaryValue < 0) {
                        JOptionPane.showMessageDialog(this, "Salary must be a non-negative number.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Please enter a valid number for the salary.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                // Validate department: Allow alphabetic and numeric characters
                if (!department.matches("^[a-zA-Z0-9]+$")) {
                    JOptionPane.showMessageDialog(this, "Department must contain alphabetic and numeric characters only.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                try {
                    // If all validation passes, update the employee in the database
                    employeeDatabase.updateEmployee(empId, empName, newSalary, department);

                    // Display a success message
                    JOptionPane.showMessageDialog(this, "Employee information updated!");
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error updating employee. Please try again.", "Database Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        panel.add(saveButton, gbc);

        gbc.gridx++;
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setBackground(new Color(50, 150, 200));
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e -> {
            // Close the Update Employee frame and go back to the main frame
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
        setLocation(1100,400);
        setVisible(true);
    }
}

