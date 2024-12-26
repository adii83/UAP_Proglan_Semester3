package com.pustakadigital.view;

import com.pustakadigital.databaseDAO.BukuDAO;
import com.pustakadigital.model.Buku;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AdminFrame extends JFrame {
    private BukuDAO bukuDAO;
    private JList<Buku> bukuList;
    private DefaultListModel<Buku> bukuListModel;
    private JComboBox<String> genreComboBox;

    public AdminFrame() {
        setTitle("Admin Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        bukuDAO = new BukuDAO();
        bukuListModel = new DefaultListModel<>();
        bukuList = new JList<>(bukuListModel);
        loadBukuList("Semua");

        JLabel welcomeLabel = new JLabel("Welcome, Admin!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(welcomeLabel, BorderLayout.NORTH);

        // Panel untuk tombol fitur
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton manageUsersButton = new JButton("Manage Users");
        JButton addBookButton = new JButton("Tambah Buku");
        JButton deleteBookButton = new JButton("Hapus Buku");
        JButton logoutButton = new JButton("Logout");

        buttonPanel.add(manageUsersButton);
        buttonPanel.add(addBookButton);
        buttonPanel.add(deleteBookButton);
        buttonPanel.add(logoutButton);

        add(buttonPanel, BorderLayout.SOUTH);
        add(new JScrollPane(bukuList), BorderLayout.CENTER);

        // Action listeners
        addBookButton.addActionListener(e -> new AddBookFrame().setVisible(true));
        deleteBookButton.addActionListener(e -> deleteSelectedBook());
        logoutButton.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });
    }

    private void loadBukuList(String genre) {
        List<Buku> bukuListData = bukuDAO.getAllBuku();
        bukuListModel.clear();
        for (Buku buku : bukuListData) {
            if (genre.equals("Semua") || buku.getGenre().equalsIgnoreCase(genre)) {
                bukuListModel.addElement(buku);
            }
        }
    }

    private void deleteSelectedBook() {
        Buku selectedBuku = bukuList.getSelectedValue();
        if (selectedBuku != null) {
            int confirm = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menghapus buku ini?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                bukuDAO.deleteBuku(selectedBuku.getId());
                loadBukuList((String) genreComboBox.getSelectedItem());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pilih buku yang ingin dihapus.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
