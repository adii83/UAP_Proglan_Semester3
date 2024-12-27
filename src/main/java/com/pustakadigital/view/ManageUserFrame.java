package com.pustakadigital.view;

import com.pustakadigital.databaseDAO.UserDAO;
import com.pustakadigital.model.User;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ManageUserFrame extends JFrame {
    private JList<User> userList;
    private DefaultListModel<User> userListModel;
    private UserDAO userDAO;

    public ManageUserFrame() {
        setTitle("Manage Users");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        userDAO = new UserDAO();
        userListModel = new DefaultListModel<>();
        userList = new JList<>(userListModel);
        loadUserList();

        JButton deleteButton = new JButton("Hapus Pengguna");
        deleteButton.addActionListener(e -> deleteUser());

        setLayout(new BorderLayout());
        add(new JScrollPane(userList), BorderLayout.CENTER);
        add(deleteButton, BorderLayout.SOUTH);
    }

    private void loadUserList() {
        List<User> users = userDAO.getAllUsers();
        userListModel.clear();
        for (User user : users) {
            userListModel.addElement(user);
        }
    }

    private void deleteUser() {
        User selectedUser = userList.getSelectedValue();
        if (selectedUser != null) {
            int confirm = JOptionPane.showConfirmDialog(this, 
                "Apakah Anda yakin ingin menghapus pengguna ini?", 
                "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                userDAO.deleteUser(selectedUser.getId());
                loadUserList();
                JOptionPane.showMessageDialog(this, "Pengguna berhasil dihapus.");
            }
        } else {
            JOptionPane.showMessageDialog(this, "Silakan pilih pengguna untuk dihapus.");
        }
    }
} 