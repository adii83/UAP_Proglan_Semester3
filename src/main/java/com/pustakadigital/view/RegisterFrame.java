package com.pustakadigital.view;

import com.pustakadigital.databaseDAO.UserDAO;

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
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(1, 2));


        JPanel signInPanel = createSignInPanel();
        signInPanel.setBackground(new Color(0, 128, 0));


        JPanel signUpPanel = createSignUpPanel();
        signUpPanel.setBackground(Color.WHITE);

        add(signInPanel);
        add(signUpPanel);

        userDAO = new UserDAO();
    }

    private JPanel createSignInPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Welcome Back!");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(title, gbc);

        backButton = new JButton("SIGN IN");
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(new Color(0, 128, 0));
        backButton.setFocusPainted(false);
        backButton.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });
        gbc.gridy = 1;
        panel.add(backButton, gbc);

        return panel;
    }

    private JPanel createSignUpPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel title = new JLabel("Create Account");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(new Color(0, 128, 0));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        panel.add(title, gbc);

        usernameField = new JTextField(20);
        usernameField.setBorder(BorderFactory.createTitledBorder("Username"));
        gbc.gridy = 1;
        panel.add(usernameField, gbc);

        passwordField = new JPasswordField(20);
        passwordField.setBorder(BorderFactory.createTitledBorder("Password"));
        gbc.gridy = 2;
        panel.add(passwordField, gbc);

        registerButton = new JButton("SIGN UP");
        registerButton.setBackground(new Color(0, 128, 0));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFocusPainted(false);
        registerButton.addActionListener(new RegisterButtonListener());
        gbc.gridy = 3;
        panel.add(registerButton, gbc);

        return panel;
    }

    private class RegisterButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

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
            return password.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$");
        }
    }
}

