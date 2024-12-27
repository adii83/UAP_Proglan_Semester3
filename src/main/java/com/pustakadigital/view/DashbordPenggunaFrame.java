package com.pustakadigital.view;

import com.pustakadigital.databaseDAO.BukuDAO;
import com.pustakadigital.model.Buku;
import com.formdev.flatlaf.FlatDarkLaf;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.util.stream.Collectors;

public class DashbordPenggunaFrame extends JFrame {
    private JPanel bookPanel;
    private JTextField searchField;
    private JComboBox<String> genreFilterComboBox;
    private BukuDAO bukuDAO;

    private List<Buku> bukuListData;
    private int currentBookIndex = 0; // Untuk melacak buku yang sudah dimuat
    private static final int BOOKS_PER_PAGE = 10; // Jumlah buku per halaman

    public DashbordPenggunaFrame(String username) {
        setTitle("Pustaka Digital");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        bukuDAO = new BukuDAO();

        // Set theme
        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }

        // Panel Utama dengan GridBagLayout
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(50, 50, 50));
        add(mainPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Margin antar elemen
        gbc.fill = GridBagConstraints.BOTH;

        // Panel Selamat Datang
        JPanel welcomePanel = new JPanel(new BorderLayout());
        welcomePanel.setBackground(new Color(0, 0, 0));
        JLabel welcomeLabel = new JLabel("Selamat datang, " + username + "!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeLabel.setForeground(Color.WHITE);
        welcomePanel.add(welcomeLabel, BorderLayout.CENTER);

        // Tambahkan panel selamat datang ke layout utama
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1; // Mengisi penuh secara horizontal
        gbc.weighty = 0; // Tidak mengambil ruang tambahan vertikal
        mainPanel.add(welcomePanel, gbc);

        // Panel untuk filter dan pencarian
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.setBackground(new Color(50, 50, 50));
        filterPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Padding lebih kecil

        searchField = new JTextField(20);
        searchField.setFont(new Font("Arial", Font.PLAIN, 14));
        genreFilterComboBox = new JComboBox<>(new String[]{"Semua", "Fiksi", "Non-Fiksi", "Sejarah", "Sains"});
        genreFilterComboBox.setFont(new Font("Arial", Font.PLAIN, 14));

        filterPanel.add(new JLabel("Cari:"));
        filterPanel.add(searchField);
        filterPanel.add(new JLabel("Genre:"));
        filterPanel.add(genreFilterComboBox);

        // Tambahkan panel filter ke layout utama
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1; // Mengisi penuh secara horizontal
        gbc.weighty = 0; // Tidak mengambil ruang tambahan vertikal
        mainPanel.add(filterPanel, gbc);

        // Panel untuk buku lainnya
        JPanel outerBookPanel = new JPanel(new BorderLayout());
        outerBookPanel.setBackground(new Color(245, 245, 245));

        // Gunakan GridLayout dengan 4 kolom tetap
        bookPanel = new JPanel(new GridLayout(0, 4, 20, 20)); // 0 baris dan 4 kolom dengan jarak antar elemen
        bookPanel.setBackground(new Color(245, 245, 245));
        bookPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Margin antar panel buku

        JScrollPane scrollPane = new JScrollPane(bookPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // Selalu tampilkan scroll bar vertikal
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Jangan tampilkan scroll bar horizontal
        scrollPane.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Padding untuk scroll pane
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Smooth scrolling
        outerBookPanel.add(scrollPane, BorderLayout.CENTER);

        // Tambahkan panel buku ke layout utama
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1; // Mengisi penuh secara horizontal
        gbc.weighty = 1; // Mengambil semua sisa ruang vertikal
        mainPanel.add(outerBookPanel, gbc);

        // Event listener untuk pencarian dan filter
        searchField.addActionListener(e -> loadBooks());
        genreFilterComboBox.addActionListener(e -> loadBooks());

        // Load initial books
        loadBooks();
    }

    private void loadBooks() {
        // Mengambil daftar buku sesuai pencarian dan filter
        String searchQuery = searchField.getText().toLowerCase();
        String selectedGenre = (String) genreFilterComboBox.getSelectedItem();

        bukuListData = bukuDAO.getAllBuku().stream()
                .filter(buku -> buku.getJudul().toLowerCase().contains(searchQuery))
                .filter(buku -> selectedGenre.equals("Semua") || buku.getGenre().equalsIgnoreCase(selectedGenre))
                .collect(Collectors.toList());

        // Reset index dan panel
        currentBookIndex = 0;
        bookPanel.removeAll();
        loadMoreBooks();
    }

    private void loadMoreBooks() {
        // Ambil buku yang sesuai dengan halaman saat ini
        int endIndex = Math.min(currentBookIndex + BOOKS_PER_PAGE, bukuListData.size());
        List<Buku> booksToDisplay = bukuListData.subList(currentBookIndex, endIndex);

        for (Buku buku : booksToDisplay) {
            bookPanel.add(createBookPanel(buku)); // Tambahkan buku ke panel
        }

        // Tambahkan placeholder jika jumlah buku kurang dari 4
        int remainingSlots = 4 - (booksToDisplay.size() % 4);
        if (remainingSlots != 4) { // Hanya tambahkan placeholder jika diperlukan
            addPlaceholders(remainingSlots);
        }

        // Update currentBookIndex untuk halaman berikutnya
        currentBookIndex = endIndex;

        bookPanel.revalidate();
        bookPanel.repaint();
    }

    private void addPlaceholders(int placeholders) {
        for (int i = 0; i < placeholders; i++) {
            JPanel placeholder = new JPanel();
            placeholder.setBackground(new Color(245, 245, 245)); // Warna latar belakang placeholder sama dengan bookPanel
            bookPanel.add(placeholder);
        }
    }

    private JPanel createBookPanel(Buku buku) {
        JPanel bukuPanelItem = new JPanel();
        bukuPanelItem.setLayout(new BorderLayout());
        bukuPanelItem.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        bukuPanelItem.setBackground(new Color(255, 255, 255));
        bukuPanelItem.setPreferredSize(new Dimension(200, 300)); // Ukuran tetap untuk setiap buku

        // Gambar sampul buku
        ImageIcon imageIcon = new ImageIcon(buku.getGambarSampul());
        Image image = imageIcon.getImage().getScaledInstance(150, 200, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(image));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        bukuPanelItem.add(imageLabel, BorderLayout.CENTER);

        // Panel teks (judul dan pengarang)
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(new Color(255, 255, 255));
        textPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10)); // Padding untuk textPanel

        // Margin antara gambar dan teks (judul)
        textPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        // Judul buku
        JLabel titleLabel = new JLabel(buku.getJudul());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Pengarang buku
        JLabel authorLabel = new JLabel(buku.getPenulis());
        authorLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        authorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        authorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        authorLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0)); // Tambahkan padding atas

        textPanel.add(titleLabel);
        textPanel.add(Box.createRigidArea(new Dimension(0, 5))); // Tambahkan jarak antara judul dan pengarang
        textPanel.add(authorLabel);

        bukuPanelItem.add(textPanel, BorderLayout.SOUTH);

        return bukuPanelItem;
    }

    public void refreshBukuList() {
        loadBooks(); // Assuming loadBooks() refreshes the book list
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DashbordPenggunaFrame frame = new DashbordPenggunaFrame("User");
            frame.setVisible(true);
        });
    }
}
