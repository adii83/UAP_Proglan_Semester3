package com.pustakadigital.databaseDAO;

import com.pustakadigital.model.Buku;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;

public class BukuDAO {
    private static final Logger logger = Logger.getLogger(BukuDAO.class);
    private Connection connection;

    public BukuDAO() {
        connection = Database.connect();
        logger.info("Database connection established.");
    }

    public void addBuku(Buku buku) {
        String query = "INSERT INTO buku (judul, penulis, genre, tahun, gambarSampul) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Database.connect();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, buku.getJudul());
            stmt.setString(2, buku.getPenulis());
            stmt.setString(3, buku.getGenre());
            stmt.setInt(4, buku.getTahun());
            stmt.setString(5, buku.getGambarSampul());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error adding book: " + e.getMessage());
        }
    }

    public boolean isIsbnExists(String isbn) {
        String query = "SELECT COUNT(*) FROM buku WHERE isbn = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, isbn);
            ResultSet rs = stmt.executeQuery();
            rs.next();
            return rs.getInt(1) > 0;
        } catch (SQLException e) {
            logger.error("Error checking ISBN existence", e);
            return false;
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
                        rs.getString("gambarSampul"),
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
        String query = "UPDATE buku SET judul = ?, penulis = ?, genre = ?, tahun = ?, gambarSampul = ? WHERE id = ?";
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

    public Buku getBukuByISBN(String isbn) {
        Buku buku = null;
        String sql = "SELECT * FROM buku WHERE isbn = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, isbn);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                buku = new Buku(
                    resultSet.getString("judul"),
                    resultSet.getString("penulis"),
                    resultSet.getString("genre"),
                    resultSet.getString("gambarSampul"),
                    resultSet.getString("isbn")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return buku;
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
