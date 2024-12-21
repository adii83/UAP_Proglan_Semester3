package com.pustakadigital;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public boolean login(String username, String password) {
        String query = "SELECT * FROM users WHERE username = ? AND password = ?";
        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            return rs.next();
        } catch (SQLException e) {
            System.out.println("Error logging in: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Error logging in: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
        return false;
    }

    public void register(User user) {
        String checkQuery = "SELECT COUNT(*) FROM users WHERE username = ?";
        String insertQuery = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";

        try (Connection conn = Database.connect();
             PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
             PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {

            // Periksa apakah username sudah ada
            checkStmt.setString(1, user.getUsername());
            ResultSet rs = checkStmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                JOptionPane.showMessageDialog(null, "Username already exists!", "Registration Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Tambahkan pengguna baru
            insertStmt.setString(1, user.getUsername());
            insertStmt.setString(2, user.getPassword());
//            insertStmt.setString(3, user.getEmail());
            insertStmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "User registered successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            System.out.println("Error registering user: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Error registering user: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
