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
        // Judul dan pengaturan frame
        setTitle("Login");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Posisikan di tengah layar
        setLayout(new BorderLayout());

        // Panel atas dengan judul
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Welcome to Pustaka Digital");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titlePanel.add(titleLabel);
        add(titlePanel, BorderLayout.NORTH);

        // Panel tengah untuk form login
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        formPanel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        formPanel.add(usernameField);

        formPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        formPanel.add(passwordField);

        // Tombol Login
        loginButton = new JButton("Login");
        loginButton.addActionListener(new LoginButtonListener());
        formPanel.add(loginButton);

        // Tombol "Belum punya akun?"
        noAccountButton = new JButton("Belum punya akun?");
        noAccountButton.addActionListener(e -> {
            new RegisterFrame().setVisible(true);
            dispose();
        });
        formPanel.add(noAccountButton);

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
