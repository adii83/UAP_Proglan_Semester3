package com.pustakadigital.databaseDAO;

import com.pustakadigital.model.Buku;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class BukuDAO {
    private static final Logger logger = Logger.getLogger(BukuDAO.class);

    public void addBuku(Buku buku) {
        if (isIsbnExists(buku.getIsbn())) {
            JOptionPane.showMessageDialog(null, "ISBN sudah ada!", "Error", JOptionPane.ERROR_MESSAGE);
            logger.warn("Attempt to add a book with existing ISBN: " + buku.getIsbn());
            return;
        }

        String query = "INSERT INTO buku (judul, penulis, genre, tahun, gambar_sampul, isbn) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, buku.getJudul());
            stmt.setString(2, buku.getPenulis());
            stmt.setString(3, buku.getGenre());
            stmt.setInt(4, buku.getTahun());
            stmt.setString(5, buku.getGambarSampul());
            stmt.setString(6, buku.getIsbn());
            stmt.executeUpdate();
            logger.info("Buku berhasil ditambahkan: " + buku.getJudul());
        } catch (SQLException e) {
            logger.error("Error adding book: " + e.getMessage(), e);
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat menambahkan buku: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean isIsbnExists(String isbn) {
        String query = "SELECT COUNT(*) FROM buku WHERE isbn = ?";
        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, isbn);
            ResultSet rs = stmt.executeQuery();
            if (rs.next() && rs.getInt(1) > 0) {
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Error checking ISBN: " + e.getMessage());
        }
        return false;
    }

    public List<Buku> getAllBuku() {
        List<Buku> bukuList = new ArrayList<>();
        String query = "SELECT * FROM buku";
        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Buku buku = new Buku(
                        rs.getInt("id"),
                        rs.getString("judul"),
                        rs.getString("penulis"),
                        rs.getString("genre"),
                        rs.getInt("tahun"),
                        rs.getString("gambar_sampul"),
                        rs.getString("isbn")
                );
                bukuList.add(buku);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching books: " + e.getMessage());
        }
        return bukuList;
    }

    public void updateBuku(Buku buku) {
        String query = "UPDATE buku SET judul = ?, penulis = ?, genre = ?, tahun = ?, gambar_sampul = ? WHERE id = ?";
        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, buku.getJudul());
            stmt.setString(2, buku.getPenulis());
            stmt.setString(3, buku.getGenre());
            stmt.setInt(4, buku.getTahun());
            stmt.setString(5, buku.getGambarSampul());
            stmt.setInt(6, buku.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error updating book: " + e.getMessage());
        }
    }

    public void deleteBuku(int id) {
        String query = "DELETE FROM buku WHERE id = ?";
        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error deleting book: " + e.getMessage());
        }
    }
}
