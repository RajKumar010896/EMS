import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.sql.SQLException;

public class EmployeeManagementSystemApp extends JFrame {
    private JTextField locationTextField;
    private JTextField temperatureTextField;

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/employee_management";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "abc123";
    private EmployeeDatabase employeeDatabase;

    public EmployeeManagementSystemApp(EmployeeDatabase employeeDatabase) {
        super("Employee Management System");
        this.employeeDatabase = employeeDatabase;
        initializeUI();
    }

    private void initializeUI () {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");

                // Create buttons centered horizontally
                JPanel buttonsPanel = new JPanel(new GridBagLayout());
                GridBagConstraints gbc = new GridBagConstraints();
                gbc.gridx = 0;
                gbc.gridy = 0;
                gbc.gridwidth = 1;
                gbc.fill = GridBagConstraints.HORIZONTAL;
                gbc.insets = new Insets(5, 5, 5, 5);

                buttonsPanel.setBackground(new Color(255, 204, 0));


                buttonsPanel.add(createButton("ADD", employeeDatabase), gbc);
                gbc.gridy++;
                buttonsPanel.add(createButton("VIEW", employeeDatabase), gbc);
                gbc.gridy++;
                buttonsPanel.add(createButton("UPDATE", employeeDatabase), gbc);
                gbc.gridy++;
                buttonsPanel.add(createButton("DELETE", employeeDatabase), gbc);
                gbc.gridy++;
                buttonsPanel.add(createButton("CHARTS", employeeDatabase), gbc);

                // Customize button appearance
                for (Component component : buttonsPanel.getComponents()) {
                    if (component instanceof JButton) {
                        JButton button = (JButton) component;
                        button.setPreferredSize(new Dimension(150, 40));
                        button.setFont(new Font("Arial", Font.BOLD, 14));
                        button.setBackground(new Color(50, 150, 200));
                        button.setForeground(Color.WHITE);
                    }
                }


                // Create location and temperature display
                JPanel weatherPanel = new JPanel(new GridLayout(0, 1, 5, 5));
                weatherPanel.setBackground(new Color(255, 204, 0));

                JLabel locationLabel = new JLabel("Current Location:");
                locationLabel.setBackground(new Color(255, 204, 0));
                locationLabel.setOpaque(true);
                weatherPanel.add(locationLabel);

                locationTextField = new JTextField();
                locationTextField.setEditable(false);
                locationTextField.setBackground(new Color(255, 204, 0));
                weatherPanel.add(locationTextField);

                JLabel temperatureLabel = new JLabel("Current Temperature:");
                temperatureLabel.setBackground(new Color(255, 204, 0));
                temperatureLabel.setOpaque(true);
                weatherPanel.add(temperatureLabel);

                temperatureTextField = new JTextField();
                temperatureTextField.setEditable(false);
                temperatureTextField.setBackground(new Color(255, 204, 0));
                weatherPanel.add(temperatureTextField);
                // Add components to the main frame
                setLayout(new BorderLayout());
                add(buttonsPanel, BorderLayout.CENTER);  // Centered horizontally
                add(weatherPanel, BorderLayout.SOUTH);   // Positioned below buttons

                // Set up the frame
                setSize(400, 400);
                setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                setLocationRelativeTo(null);

                // Display static location and temperature
                locationTextField.setText("Mumbai");
                temperatureTextField.setText("30.0°C");
                // Set background color for the main frame
                getContentPane().setBackground(new Color(51, 204, 255));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

        private JButton createButton (String buttonText, EmployeeDatabase employeeDatabase){
            JButton button = new JButton(buttonText);
            button.addActionListener(new ButtonClickListener(employeeDatabase));
            return button;
        }

        private void handleDatabaseConnectionError(Exception e) {
        e.printStackTrace();
        }

        private class ButtonClickListener implements ActionListener {

            private final EmployeeDatabase employeeDatabase;

            public ButtonClickListener(EmployeeDatabase employeeDatabase) {
                this.employeeDatabase = employeeDatabase;
            }
            public void actionPerformed(ActionEvent e) {
                String command = e.getActionCommand();
                if (command.equals("ADD")) {
                    SwingUtilities.invokeLater(() -> new AddEmployeeFrame(EmployeeManagementSystemApp.this, employeeDatabase));

                } else if (command.equals("VIEW")) {
                    SwingUtilities.invokeLater(() -> new ViewEmployeeFrame(EmployeeManagementSystemApp.this, employeeDatabase));

                } else if (command.equals("UPDATE")) {
                    SwingUtilities.invokeLater(() -> new UpdateEmployeeFrame(EmployeeManagementSystemApp.this, employeeDatabase));

                } else if (command.equals("DELETE")) {
                    SwingUtilities.invokeLater(() -> new DeleteEmployeeFrame(EmployeeManagementSystemApp.this,employeeDatabase));

                } else if(command.equals("CHARTS")) {
                    SwingUtilities.invokeLater(() -> new ChartFrame(EmployeeManagementSystemApp.this, employeeDatabase));
                }else{
                    JOptionPane.showMessageDialog(EmployeeManagementSystemApp.this, command + " Button Clicked");
                }
            }
        }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/employee_management", "root", "abc123");
                EmployeeDatabase employeeDatabase = new EmployeeDatabase(connection);

                new EmployeeManagementSystemApp(employeeDatabase).setVisible(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        });
    }
}



