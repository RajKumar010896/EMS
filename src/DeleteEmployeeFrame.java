import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class DeleteEmployeeFrame extends JFrame {
    private JTextField empIdTextField;

    public DeleteEmployeeFrame(JFrame parentFrame, EmployeeDatabase employeeDatabase) {
        super("Delete Employee");

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
        JButton deleteButton = new JButton("Delete");
        deleteButton.setFont(new Font("Arial", Font.BOLD, 16));
        deleteButton.setBackground(new Color(50, 150, 200));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.addActionListener(e -> {
            String empId = empIdTextField.getText();

            try {
                // Call the method in the EmployeeDatabase class to delete the employee
                employeeDatabase.deleteEmployee(empId);

            JOptionPane.showMessageDialog(this, "Employee information deleted!");
            } catch (SQLException ex) {
                // Handle any SQLException (e.g., display an error message)
                ex.printStackTrace();
            }
        });
        panel.add(deleteButton, gbc);

        gbc.gridx++;
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 16));
        backButton.setBackground(new Color(50, 150, 200));
        backButton.setForeground(Color.WHITE);
        backButton.addActionListener(e -> {
            // Close the Delete Employee frame and go back to the main frame
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
        setVisible(true);
        setLocation(0,400);
    }
}
