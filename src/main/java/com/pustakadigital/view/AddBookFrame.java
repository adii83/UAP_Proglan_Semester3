//Terbaru
package com.pustakadigital.view;

import com.pustakadigital.model.Buku;
import com.pustakadigital.databaseDAO.BukuDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddBookFrame extends JFrame {

    private JTextField judulField, penulisField, tahunField;
    private JComboBox<String> genreComboBox;
    private JButton saveButton, uploadButton;
    private JLabel imagePathLabel;
    private BukuDAO bukuDAO;

    public AddBookFrame() {
        setTitle("Tambah Buku");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        bukuDAO = new BukuDAO();

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

        // Gambar Sampul
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(new JLabel("Gambar Sampul:"), gbc);
        uploadButton = new JButton("Upload");
        uploadButton.addActionListener(new UploadButtonListener());
        gbc.gridx = 1;
        add(uploadButton, gbc);

        imagePathLabel = new JLabel();
        gbc.gridy = 5;
        add(imagePathLabel, gbc);

        // Save Button
        saveButton = new JButton("Simpan");
        saveButton.addActionListener(new SaveButtonListener());
        gbc.gridy = 6;
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
            dispose();
        }
    }
}
