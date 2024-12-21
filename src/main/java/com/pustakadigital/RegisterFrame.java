package com.pustakadigital;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class RegisterFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton registerButton, backButton;
    private UserDAO userDAO;

    public RegisterFrame() {
        setTitle("Register");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2));

        userDAO = new UserDAO();

        // Username Field
        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);

        // Password Field
        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        // Register Button
        registerButton = new JButton("Register");
        registerButton.addActionListener(new RegisterButtonListener());
        add(registerButton);

        // Back Button
        backButton = new JButton("Back to Login");
        backButton.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });
        add(backButton);
    }

    private class RegisterButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            // Validasi Input
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Username and password must not be empty!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!isValidPassword(password)) {
                JOptionPane.showMessageDialog(null, "Password must contain at least one uppercase letter, one digit, and one special character!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            userDAO.register(username, password, "user");
            JOptionPane.showMessageDialog(null, "Registration successful! You can now log in.");
            new LoginFrame().setVisible(true);
            dispose();
        }

        private boolean isValidPassword(String password) {
            // Validasi Password
            return password.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$");
        }
    }
}
