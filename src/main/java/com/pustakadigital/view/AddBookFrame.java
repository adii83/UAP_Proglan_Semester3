//Terbaru
package com.pustakadigital.view;

import com.pustakadigital.model.Buku;
import com.pustakadigital.databaseDAO.BukuDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddBookFrame extends JFrame {

    private JTextField judulField, penulisField, genreField, tahunField;
    private JButton saveButton;
    private JFileChooser fileChooser;
    private BukuDAO bukuDAO;

    public AddBookFrame() {
        setTitle("Tambah Buku");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(6, 2));

        bukuDAO = new BukuDAO();

        // Field input
        add(new JLabel("Judul:"));
        judulField = new JTextField();
        add(judulField);

        add(new JLabel("Penulis:"));
        penulisField = new JTextField();
        add(penulisField);

        add(new JLabel("Genre:"));
        genreField = new JTextField();
        add(genreField);

        add(new JLabel("Tahun:"));
        tahunField = new JTextField();
        add(tahunField);

        // Gambar Sampul
        add(new JLabel("Gambar Sampul:"));
        fileChooser = new JFileChooser();
        add(fileChooser);

        // Save Button
        saveButton = new JButton("Simpan");
        saveButton.addActionListener(new SaveButtonListener());
        add(saveButton);
    }

    private class SaveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String judul = judulField.getText();
            String penulis = penulisField.getText();
            String genre = genreField.getText();
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

            String gambarSampul = fileChooser.getSelectedFile() != null ? fileChooser.getSelectedFile().getAbsolutePath() : "";

            if (gambarSampul.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Silakan pilih gambar sampul!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Buku buku = new Buku(0, judul, penulis, genre, tahun, gambarSampul);
            bukuDAO.addBuku(buku);
            JOptionPane.showMessageDialog(null, "Buku berhasil disimpan!");
        }
    }
}
