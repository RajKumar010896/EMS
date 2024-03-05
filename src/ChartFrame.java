import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Vector;

public class ChartFrame extends JFrame {
    private EmployeeDatabase employeeDatabase;

    public ChartFrame(JFrame parentFrame, EmployeeDatabase employeeDatabase) {
        super("Top 5 Highest Salaried Employees");
        this.employeeDatabase = employeeDatabase;
        initializeUI();
    }

    private void initializeUI() {
        setAppearance();
        try {
            // Fetch the data for the chart (employee details as a String)
            String employeeDetails = employeeDatabase.viewEmployees();

            // Parse the employee details string
            Vector<Vector<String>> data = parseEmployeeDetails(employeeDetails);

            // Sort the data by salary in descending order using a custom comparator
            data.sort((row1, row2) -> {
                double salary1 = extractNumericSalary(row1.get(2));
                double salary2 = extractNumericSalary(row2.get(2));
                return Double.compare(salary2, salary1);
            });

            // Select the top 5 records
            Vector<Vector<String>> top5Data = new Vector<>(data.subList(0, Math.min(5, data.size())));

            // Create column names
            Vector<String> columnNames = new Vector<>();
            columnNames.add("Employee ID");
            columnNames.add("Employee Name");
            columnNames.add("Salary");
            columnNames.add("Department");

            // Create JTable with data and column names
            JTable jTable = new JTable(top5Data, columnNames);
            jTable.setFont(new Font("Arial", Font.PLAIN, 14));
            jTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));

            // Add JTable to a JScrollPane for scrollable display
            JScrollPane scrollPane = new JScrollPane(jTable);

            // Add JScrollPane to the frame
            add(scrollPane);

            JButton backButton = new JButton("Back");
            backButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // Close the current frame and show the parent frame
                    dispose();
                    if (getParent() != null) {
                        getParent().setVisible(true);
                    }
                }
            });

            // Add the back button to the frame
            add(backButton, BorderLayout.SOUTH);

            // Set up the frame
            setSize(800,400);
            setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            setLocationRelativeTo(null);
            setVisible(true);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching data from the database: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

        }
    }

    // Method to extract numeric part of salary
    private double extractNumericSalary(String salary) {
        try {
            return Double.parseDouble(salary.replaceAll("[^\\d.]", ""));
        } catch (NumberFormatException e) {
            return 0; // Handle non-numeric or invalid salary values
        }
    }

    private void setAppearance() {
        // Customize the appearance of the frame, e.g., set background color
        getContentPane().setBackground(new Color(255, 204, 0)); // Replace with your preferred color

        setLayout(new BorderLayout());
    }

    private Vector<Vector<String>> parseEmployeeDetails(String employeeDetails) {
        Vector<Vector<String>> data = new Vector<>();

        // Split the employee details string by newline character
        String[] lines = employeeDetails.split("\n");

        for (String line : lines) {
            // Split each line by comma to get employee ID, name, and salary
            String[] parts = line.split(",");
            if (parts.length == 4) {
                Vector<String> row = new Vector<>();
                row.add(parts[0].trim());  // Employee ID
                row.add(parts[1].trim());  // Employee Name
                //row.add(parts[2].trim());  // Salary
                String salary = parts[2].trim().replaceAll("[^\\d.]", "");  // Remove non-numeric characters
                row.add(salary);
                row.add(parts[3].trim());
                data.add(row);
            }
        }

        return data;
    }
}
