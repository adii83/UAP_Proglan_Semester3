package com.pustakadigital;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, registerButton;
    private UserDAO userDAO;

    public LoginFrame() {
        setTitle("Login");
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

        // Login Button
        loginButton = new JButton("Login");
        loginButton.addActionListener(new LoginButtonListener());
        add(loginButton);

        // Register Button
        registerButton = new JButton("Register");
        registerButton.addActionListener(new RegisterButtonListener());
        add(registerButton);
    }

    private class LoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (userDAO.login(username, password)) {
                JOptionPane.showMessageDialog(null, "Login successful!");
                // Open the main application frame here
                new MainFrame().setVisible(true);
                dispose();
            } else {
                JOptionPane.showMessageDialog(null, "Invalid username or password");
            }
        }
    }

    private class RegisterButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            User user = new User(0, username, password);
            userDAO.register(user);
            JOptionPane.showMessageDialog(null, "Registration successful!");
        }
    }
}
