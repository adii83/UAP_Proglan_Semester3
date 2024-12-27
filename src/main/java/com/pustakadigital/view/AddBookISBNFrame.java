package com.pustakadigital.view;

import com.pustakadigital.model.Buku;
import com.pustakadigital.databaseDAO.BukuDAO;
import com.pustakadigital.api.OpenLibraryAPI;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddBookISBNFrame extends JFrame {
    private JTextField isbnField;
    private JButton fetchButton, saveButton;
    private JLabel judulLabel, penulisLabel, genreLabel, coverLabel;
    private BukuDAO bukuDAO;
    private JFrame parentFrame;
    private Runnable refreshCallback;
    private JComboBox<String> genreComboBox;

    public AddBookISBNFrame(JFrame parentFrame, Runnable refreshCallback) {
        setTitle("Tambah Buku dengan ISBN");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        this.refreshCallback = refreshCallback;
        this.bukuDAO = new BukuDAO();
        this.parentFrame = parentFrame;

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // ISBN
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("ISBN:"), gbc);
        isbnField = new JTextField();
        isbnField.setPreferredSize(new Dimension(200, 25));
        gbc.gridx = 1;
        add(isbnField, gbc);

        // Tombol Ambil Data
        fetchButton = new JButton("Ambil Data");
        fetchButton.addActionListener(new FetchButtonListener());
        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        add(fetchButton, gbc);

        // Labels for displaying fetched data
        judulLabel = new JLabel("Judul: ");
        penulisLabel = new JLabel("Penulis: ");
        genreLabel = new JLabel("Genre: ");
        coverLabel = new JLabel("Gambar Sampul: ");

        gbc.gridy = 2;
        gbc.gridwidth = 1;
        add(judulLabel, gbc);
        gbc.gridy = 3;
        add(penulisLabel, gbc);
        gbc.gridy = 4;
        add(new JLabel("Genre:"), gbc);
        genreComboBox = new JComboBox<>(new String[]{"Fiksi", "Non-Fiksi", "Sejarah", "Sains", "Lainnya"});
        gbc.gridx = 1;
        add(genreComboBox, gbc);
        gbc.gridy = 5;
        add(coverLabel, gbc);

        // Save Button
        saveButton = new JButton("Simpan");
        saveButton.addActionListener(new SaveButtonListener());
        gbc.gridy = 6;
        gbc.gridx = 0;
        gbc.gridwidth = 2;
        add(saveButton, gbc);
    }

    private class FetchButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String isbn = isbnField.getText();
            if (isbn.isEmpty()) {
                JOptionPane.showMessageDialog(null, "ISBN harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            fetchDataFromAPI(isbn);
        }
    }

    private void fetchDataFromAPI(String isbn) {
        OpenLibraryAPI openLibraryAPI = new OpenLibraryAPI();
        JSONObject bookInfo = openLibraryAPI.getBookInfoByISBN(isbn);

        if (bookInfo != null) {
            String title = bookInfo.optString("title", "Judul tidak tersedia");
            JSONArray authorsArray = bookInfo.optJSONArray("authors");
            String authors = formatAuthors(authorsArray);
            String coverUrl = bookInfo.optJSONObject("cover").optString("large", "");
            
            // Cek genre dari API
            String genre = bookInfo.optJSONArray("subjects") != null ? bookInfo.optJSONArray("subjects").toString() : (String) genreComboBox.getSelectedItem();

            judulLabel.setText("Judul: " + title);
            penulisLabel.setText("Penulis: " + authors);
            genreLabel.setText("Genre: " + genre);
            coverLabel.setText("Gambar Sampul: " + coverUrl);
        } else {
            JOptionPane.showMessageDialog(null, "Tidak ada data yang ditemukan untuk ISBN ini.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private String formatAuthors(JSONArray authorsArray) {
        if (authorsArray == null) return "Penulis tidak tersedia";
        StringBuilder authors = new StringBuilder();
        for (int i = 0; i < authorsArray.length(); i++) {
            JSONObject author = authorsArray.getJSONObject(i);
            if (i > 0) {
                authors.append(", ");
            }
            authors.append(author.getString("name"));
        }
        return authors.toString();
    }

    private class SaveButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String isbn = isbnField.getText();
            if (isbn.isEmpty()) {
                JOptionPane.showMessageDialog(null, "ISBN harus diisi!", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Assuming the data is already fetched and displayed
            String judul = judulLabel.getText().replace("Judul: ", "");
            String penulis = penulisLabel.getText().replace("Penulis: ", "");
            String genre = genreLabel.getText().replace("Genre: ", "");
            String gambarSampul = coverLabel.getText().replace("Gambar Sampul: ", "");

            Buku buku = new Buku(0, judul, penulis, genre, 0, gambarSampul, isbn);
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