package com.pustakadigital;

import javax.swing.*;
import java.awt.*;

public class StatistikFrame extends JFrame {
    private JPanel chartPanel;
// oke
    public StatistikFrame() {
        setTitle("Statistik Buku");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Generate chart
        Statistik statistik = new Statistik();
        chartPanel = statistik.generateGenreChart();

        add(chartPanel, BorderLayout.CENTER);
    }
}
