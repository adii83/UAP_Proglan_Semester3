package com.pustakadigital.view;

import com.pustakadigital.api.OpenLibraryAPI;
import com.pustakadigital.databaseDAO.BukuDAO;
import com.pustakadigital.model.Buku;
import com.formdev.flatlaf.FlatDarkLaf;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.List;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import net.coobird.thumbnailator.Thumbnails;
import java.io.InputStream;

public class DashbordPenggunaFrame extends JFrame {
    private JPanel bookPanel;
    private JTextField searchField;
    private JComboBox<String> genreFilterComboBox;
    private BukuDAO bukuDAO;

    private List<Buku> bukuListData;
    private int currentBookIndex = 0; // Untuk melacak buku yang sudah dimuat
    private static final int BOOKS_PER_PAGE = 10; // Jumlah buku per halaman

    private JTextField isbnField; // Tambahkan field untuk ISBN

    public DashbordPenggunaFrame(String username) {
        setTitle("Pustaka Digital");
        setSize(1000, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        bukuDAO = new BukuDAO();


        try {
            UIManager.setLookAndFeel(new FlatDarkLaf());
        } catch (UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }


        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBackground(new Color(50, 50, 50));
        add(mainPanel);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5); // Margin antar elemen
        gbc.fill = GridBagConstraints.BOTH;


        JPanel welcomePanel = new JPanel(new BorderLayout());
        welcomePanel.setBackground(new Color(0, 0, 0));
        JLabel welcomeLabel = new JLabel("Selamat datang, " + username + "!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 20));
        welcomeLabel.setForeground(Color.WHITE);
        welcomePanel.add(welcomeLabel, BorderLayout.CENTER);


        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.weighty = 0;
        mainPanel.add(welcomePanel, gbc);


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


        isbnField = new JTextField(20);
        isbnField.setFont(new Font("Arial", Font.PLAIN, 14));

        filterPanel.add(new JLabel("ISBN:"));
        filterPanel.add(isbnField);


        isbnField.addActionListener(e -> loadBookByISBN());


        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 1; // Mengisi penuh secara horizontal
        gbc.weighty = 0; // Tidak mengambil ruang tambahan vertikal
        mainPanel.add(filterPanel, gbc);


        JPanel outerBookPanel = new JPanel(new BorderLayout());
        outerBookPanel.setBackground(new Color(245, 245, 245));


        bookPanel = new JPanel(new GridLayout(0, 4, 20, 20)); // 0 baris dan 4 kolom dengan jarak antar elemen
        bookPanel.setBackground(new Color(245, 245, 245));
        bookPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Margin antar panel buku

        JScrollPane scrollPane = new JScrollPane(bookPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); // Selalu tampilkan scroll bar vertikal
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Jangan tampilkan scroll bar horizontal
        scrollPane.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Padding untuk scroll pane
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Smooth scrolling
        outerBookPanel.add(scrollPane, BorderLayout.CENTER);


        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 1;
        gbc.weighty = 1;
        mainPanel.add(outerBookPanel, gbc);


        searchField.addActionListener(e -> loadBooks());
        genreFilterComboBox.addActionListener(e -> loadBooks());


        loadBooks();
    }

    private void loadBooks() {

        String searchQuery = searchField.getText().toLowerCase();
        String selectedGenre = (String) genreFilterComboBox.getSelectedItem();

        bukuListData = bukuDAO.getAllBuku().stream()
                .filter(buku -> buku.getJudul().toLowerCase().contains(searchQuery))
                .filter(buku -> selectedGenre.equals("Semua") || buku.getGenre().equalsIgnoreCase(selectedGenre))
                .collect(Collectors.toList());


        currentBookIndex = 0;
        bookPanel.removeAll();
        loadMoreBooks();
    }

    private void loadMoreBooks() {

        int endIndex = Math.min(currentBookIndex + BOOKS_PER_PAGE, bukuListData.size());
        List<Buku> booksToDisplay = bukuListData.subList(currentBookIndex, endIndex);

        for (Buku buku : booksToDisplay) {
            bookPanel.add(createBookPanel(buku));
        }


        int remainingSlots = 4 - (booksToDisplay.size() % 4);
        if (remainingSlots != 4) {
            addPlaceholders(remainingSlots);
        }


        currentBookIndex = endIndex;

        bookPanel.revalidate();
        bookPanel.repaint();
        System.out.println("Books loaded into panel.");
    }

    private void addPlaceholders(int placeholders) {
        for (int i = 0; i < placeholders; i++) {
            JPanel placeholder = new JPanel();
            placeholder.setBackground(new Color(245, 245, 245));
            bookPanel.add(placeholder);
        }
    }

    private JPanel createBookPanel(Buku buku) {
        JPanel bukuPanelItem = new JPanel();
        bukuPanelItem.setLayout(new BorderLayout());
        bukuPanelItem.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1));
        bukuPanelItem.setBackground(new Color(255, 255, 255));
        bukuPanelItem.setPreferredSize(new Dimension(200, 300));


        String coverUrl = buku.getGambarSampul();
        System.out.println("Fetching image from URL: " + coverUrl);

        BufferedImage image = null;
        try {
            if (coverUrl != null && !coverUrl.isEmpty()) {
                if (coverUrl.startsWith("http://") || coverUrl.startsWith("https://")) {
                    URL url = new URL(coverUrl);
                    image = ImageIO.read(url);
                } else {
                    File file = new File(coverUrl);
                    if (file.exists()) {
                        image = ImageIO.read(file);
                    } else {
                        System.out.println("File tidak ditemukan: " + coverUrl);
                    }
                }
            }

            if (image == null) {

                InputStream defaultImageStream = getClass().getResourceAsStream("/images/default.png");
                if (defaultImageStream != null) {
                    image = ImageIO.read(defaultImageStream);
                } else {
                    System.out.println("Default image not found.");
                }
            }

            if (image != null) {
                Image scaledImage = image.getScaledInstance(140, 160, Image.SCALE_SMOOTH);
                JLabel imageLabel = new JLabel(new ImageIcon(scaledImage));
                imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
                bukuPanelItem.add(imageLabel, BorderLayout.CENTER);
            } else {
                JLabel imageLabel = new JLabel("Image not available");
                imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
                bukuPanelItem.add(imageLabel, BorderLayout.CENTER);
            }
        } catch (Exception e) {
            e.printStackTrace();
            JLabel imageLabel = new JLabel("Error loading image");
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            bukuPanelItem.add(imageLabel, BorderLayout.CENTER);
        }


        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.setBackground(new Color(255, 255, 255));
        textPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        textPanel.add(Box.createRigidArea(new Dimension(0, 10)));

        JLabel titleLabel = new JLabel(buku.getJudul());
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel authorLabel = new JLabel(buku.getPenulis());
        authorLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        authorLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        authorLabel.setHorizontalAlignment(SwingConstants.CENTER);
        authorLabel.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        textPanel.add(titleLabel);
        textPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        textPanel.add(authorLabel);

        bukuPanelItem.add(textPanel, BorderLayout.SOUTH);

        bukuPanelItem.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                try {
                    openBookDetailPage(buku);
                } catch (MalformedURLException | URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        });

        return bukuPanelItem;
    }

    private boolean isFromISBN(Buku buku) {

        return buku.getIsbn() != null && !buku.getIsbn().isEmpty();
    }

    private void openBookDetailPage(Buku buku) throws MalformedURLException, URISyntaxException {
        OpenLibraryAPI api = new OpenLibraryAPI();
        String bookISBN = buku.getIsbn();
        JSONObject bookDetails = api.getBookInfoByISBN(bookISBN);
        
        try {
            String openLibraryUrl = bookDetails.getString("url");
            Desktop.getDesktop().browse(new URI(openLibraryUrl));
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error opening URL.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void loadBookByISBN() {
        String isbn = isbnField.getText();
        Buku buku = bukuDAO.getBukuByISBN(isbn);

        if (buku != null) {
            // Format authors if needed
            JSONArray authorsArray = new JSONArray(buku.getPenulis());
            String authors = formatAuthors(authorsArray);
            buku.setPenulis(authors);

            bukuListData.add(buku);
            currentBookIndex = 0;
            bookPanel.removeAll();
            loadMoreBooks();
        } else {
            JOptionPane.showMessageDialog(this, "Buku tidak ditemukan!", "Peringatan", JOptionPane.WARNING_MESSAGE);
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            DashbordPenggunaFrame frame = new DashbordPenggunaFrame("User");
            frame.setVisible(true);
        });
    }
}
