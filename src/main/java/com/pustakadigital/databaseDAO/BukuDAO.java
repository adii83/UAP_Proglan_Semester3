package com.pustakadigital.databaseDAO;

import com.pustakadigital.model.Buku;

import javax.swing.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BukuDAO {

    public void addBuku(Buku buku) {
        String query = "INSERT INTO " + Database.TABLE_BUKU + " (judul, penulis, genre, tahun, gambar_sampul) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, buku.getJudul());
            stmt.setString(2, buku.getPenulis());
            stmt.setString(3, buku.getGenre());
            stmt.setInt(4, buku.getTahun());
            stmt.setString(5, buku.getGambarSampul());
            stmt.executeUpdate();
            System.out.println("Buku berhasil ditambahkan: " + buku.getJudul());
        } catch (SQLException e) {
            System.out.println("Error adding book: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Terjadi kesalahan saat menambahkan buku: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
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
                        rs.getString("gambar_sampul")
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
