import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ViewEmployeeFrame extends JFrame {
    public ViewEmployeeFrame(JFrame parentFrame, EmployeeDatabase employeeDatabase) {
        super("View Employee");

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.YELLOW);

        JTextArea employeeDetailsTextArea = new JTextArea();
        employeeDetailsTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(employeeDetailsTextArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        JButton viewButton = new JButton("View Employees");
        viewButton.setFont(new Font("Arial", Font.BOLD, 16));
        viewButton.setBackground(new Color(50, 150, 200));
        viewButton.setForeground(Color.WHITE);
        viewButton.addActionListener(e -> {
            try {
                // Retrieve employee details from the database and display them
                String employeeDetails = employeeDatabase.viewEmployees();
                employeeDetailsTextArea.setText(employeeDetails);
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error viewing employees. Please try again.");
            }
        });
        panel.add(viewButton, BorderLayout.NORTH);

        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setBackground(new Color(50, 150, 200));
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e -> {
            // Close the View Employee frame and go back to the main frame
            this.dispose();
            parentFrame.setVisible(true);
        });
        panel.add(backButton, BorderLayout.SOUTH);

        setLayout(new BorderLayout());
        add(panel, BorderLayout.CENTER);

        // Set up the frame
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(parentFrame);
        setVisible(true);
        setLocation(1100,0);
    }
}


