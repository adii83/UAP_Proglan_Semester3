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
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Panel atas dengan judul
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Create an Account");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Panel tengah untuk form register
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        formPanel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        formPanel.add(usernameField);

        formPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        formPanel.add(passwordField);

        // Tombol Register
        registerButton = new JButton("Register");
        registerButton.addActionListener(new RegisterButtonListener());
        formPanel.add(registerButton);

        // Tombol Kembali ke Login
        backButton = new JButton("Back to Login");
        backButton.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });
        formPanel.add(backButton);

        add(formPanel, BorderLayout.CENTER);

        // Panel bawah untuk catatan
        JPanel footerPanel = new JPanel();
        JLabel footerLabel = new JLabel("© 2024 Pustaka Digital");
        footerLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        footerPanel.add(footerLabel);
        add(footerPanel, BorderLayout.SOUTH);

        // Inisialisasi DAO
        userDAO = new UserDAO();
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
