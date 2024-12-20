package com.pustakadigital;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    private JTable bukuTable;
    private JButton btnAdd, btnEdit, btnDelete, btnStatistik;

    public MainFrame() {
        setTitle("Pustaka Digital");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Tabel Buku
        bukuTable = new JTable(); // Data akan dimuat ke dalam tabel

        // Button untuk operasi CRUD
        btnAdd = new JButton("Tambah Buku");
        btnEdit = new JButton("Edit Buku");
        btnDelete = new JButton("Hapus Buku");
        btnStatistik = new JButton("Tampilkan Statistik");

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(1, 4));
        panel.add(btnAdd);
        panel.add(btnEdit);
        panel.add(btnDelete);
        panel.add(btnStatistik);

        // Menambahkan panel dan tabel ke frame
        add(new JScrollPane(bukuTable), BorderLayout.CENTER);
        add(panel, BorderLayout.SOUTH);
    }
}
