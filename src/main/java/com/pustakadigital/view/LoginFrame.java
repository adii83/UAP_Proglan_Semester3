package com.pustakadigital.view;

import com.pustakadigital.databaseDAO.UserDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton, noAccountButton;
    private UserDAO userDAO;

    public LoginFrame() {
        setTitle("Login");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(1, 2));

        // Panel untuk Sign In
        JPanel signInPanel = createSignInPanel();
        signInPanel.setBackground(new Color(0, 128, 0));

        // Panel untuk Sign Up
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

        JLabel message = new JLabel("To keep connected with us please login with your personal info");
        message.setFont(new Font("Arial", Font.PLAIN, 14));
        message.setForeground(Color.WHITE);
        gbc.gridy = 1;
        panel.add(message, gbc);

        usernameField = new JTextField(20);
        usernameField.setBorder(BorderFactory.createTitledBorder("Username"));
        gbc.gridy = 2;
        panel.add(usernameField, gbc);

        passwordField = new JPasswordField(20);
        passwordField.setBorder(BorderFactory.createTitledBorder("Password"));
        gbc.gridy = 3;
        panel.add(passwordField, gbc);

        loginButton = new JButton("SIGN IN");
        loginButton.setBackground(Color.WHITE);
        loginButton.setForeground(new Color(0, 128, 0));
        loginButton.setFocusPainted(false);
        loginButton.addActionListener(new LoginButtonListener());
        gbc.gridy = 4;
        panel.add(loginButton, gbc);

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

        noAccountButton = new JButton("SIGN UP");
        noAccountButton.setBackground(new Color(0, 128, 0));
        noAccountButton.setForeground(Color.WHITE);
        noAccountButton.setFocusPainted(false);
        noAccountButton.addActionListener(e -> {
            new RegisterFrame().setVisible(true);
            dispose();
        });
        gbc.gridy = 1;
        panel.add(noAccountButton, gbc);

        return panel;
    }

    private class LoginButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Username dan Password harus diisi!", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                String role = userDAO.login(username, password);
                if (role != null) {
                    JOptionPane.showMessageDialog(null, "Login successful!");
                    if ("admin".equals(role)) {
                        new AdminFrame().setVisible(true);
                    } else {
                        new MainFrame().setVisible(true);
                    }
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Invalid username or password.", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "An error occurred during login: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
