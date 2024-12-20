package com.pustakadigital;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;

import java.sql.*;
import java.util.List;

public class Statistik {

    public ChartPanel generateGenreChart() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String query = "SELECT genre, COUNT(*) FROM buku GROUP BY genre";
        try (Connection conn = Database.connect();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                dataset.addValue(rs.getInt(2), "Books", rs.getString(1));
            }
        } catch (SQLException e) {
            System.out.println("Error generating genre chart: " + e.getMessage());
        }
        JFreeChart chart = ChartFactory.createBarChart(
                "Jumlah Buku per Genre", "Genre", "Jumlah Buku", dataset);
        return new ChartPanel(chart);
    }
}
