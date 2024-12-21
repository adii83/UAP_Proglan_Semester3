package com.pustakadigital;

import javax.swing.*;
import java.awt.*;

public class AdminFrame extends JFrame {
    public AdminFrame() {
        setTitle("Admin Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JLabel welcomeLabel = new JLabel("Welcome, Admin!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(welcomeLabel, BorderLayout.NORTH);

        // Panel untuk tombol fitur
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 2));

        JButton manageUsersButton = new JButton("Manage Users");
        JButton manageBooksButton = new JButton("Manage Books");
        JButton viewStatsButton = new JButton("View Statistics");
        JButton logoutButton = new JButton("Logout");

        buttonPanel.add(manageUsersButton);
        buttonPanel.add(manageBooksButton);
        buttonPanel.add(viewStatsButton);
        buttonPanel.add(logoutButton);

        add(buttonPanel, BorderLayout.CENTER);

        // Logout button action
        logoutButton.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });
    }
}
