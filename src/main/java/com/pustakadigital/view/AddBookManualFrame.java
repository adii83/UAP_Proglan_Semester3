package com.pustakadigital.view;

import com.pustakadigital.model.Buku;
import com.pustakadigital.databaseDAO.BukuDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddBookManualFrame extends JFrame {
    private JTextField judulField, penulisField, tahunField;
    private JComboBox<String> genreComboBox;
    private JButton saveButton, uploadButton;
    private JLabel imagePathLabel;
    private BukuDAO bukuDAO;
    private JFrame parentFrame;
    private Runnable refreshCallback;

    public AddBookManualFrame(JFrame parentFrame, Runnable refreshCallback) {
        setTitle("Tambah Buku Manual");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        this.refreshCallback = refreshCallback;
        this.bukuDAO = new BukuDAO();
        this.parentFrame = parentFrame;

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Field input
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Judul:"), gbc);
        judulField = new JTextField();
        gbc.gridx = 1;
        add(judulField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Penulis:"), gbc);
        penulisField = new JTextField();
        gbc.gridx = 1;
        add(penulisField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Genre:"), gbc);
        genreComboBox = new JComboBox<>(new String[]{"Fiksi", "Non-Fiksi", "Sejarah", "Sains"});
        gbc.gridx = 1;
        add(genreComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Tahun:"), gbc);
        tahunField = new JTextField();
        gbc.gridx = 1;
        add(tahunField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        add(new JLabel("Gambar Sampul:"), gbc);
        uploadButton = new JButton("Upload");
        uploadButton.addActionListener(new UploadButtonListener());
        gbc.gridx = 1;
        add(uploadButton, gbc);

        imagePathLabel = new JLabel();
        gbc.gridy = 6;
        gbc.gridx = 1;
        add(imagePathLabel, gbc);

        // Save Button
        saveButton = new JButton("Simpan");
        saveButton.addActionListener(new SaveButtonListener());
        gbc.gridy = 7;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        add(saveButton, gbc);
    }

    private class UploadButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser fileChooser = new JFileChooser();
            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                imagePathLabel.setText(filePath);
            }
        }
    }

    private class SaveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String judul = judulField.getText();
            String penulis = penulisField.getText();
            String genre = (String) genreComboBox.getSelectedItem();
            String tahunText = tahunField.getText();

            // Validasi input
            if (judul.isEmpty() || penulis.isEmpty() || genre.isEmpty() || tahunText.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Semua field harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            int tahun;
            try {
                tahun = Integer.parseInt(tahunText);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(null, "Tahun harus berupa angka!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String gambarSampul = imagePathLabel.getText();
            if (gambarSampul.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Silakan pilih gambar sampul!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Buku buku = new Buku(0, judul, penulis, genre, tahun, gambarSampul);
            bukuDAO.addBuku(buku);
            JOptionPane.showMessageDialog(null, "Buku berhasil disimpan!");

            // Refresh buku list in AdminFrame
            if (parentFrame instanceof AdminFrame) {
                ((AdminFrame) parentFrame).loadBukuList("Semua");
            }

            // Panggil refreshCallback jika tidak null
            if (refreshCallback != null) {
                refreshCallback.run();
            }

            dispose();
        }
    }
} 