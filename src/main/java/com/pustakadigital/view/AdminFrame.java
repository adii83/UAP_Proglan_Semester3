package com.pustakadigital.view;

import com.pustakadigital.databaseDAO.BukuDAO;
import com.pustakadigital.model.Buku;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class AdminFrame extends JFrame {
    private BukuDAO bukuDAO;
    private JList<Buku> bukuList;
    private DefaultListModel<Buku> bukuListModel;
    private JComboBox<String> genreComboBox;

    public AdminFrame() {
        setTitle("Admin Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        bukuDAO = new BukuDAO();
        bukuListModel = new DefaultListModel<>();
        bukuList = new JList<>(bukuListModel);
        bukuList.setCellRenderer(new BukuListCellRenderer());
        bukuList.setFixedCellHeight(200);
        bukuList.setFixedCellWidth(150);
        bukuList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        bukuList.setVisibleRowCount(-1);
        
        JScrollPane scrollPane = new JScrollPane(bukuList);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(scrollPane, BorderLayout.CENTER);

        loadBukuList("Semua");

        JLabel welcomeLabel = new JLabel("Welcome, Admin!", SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(welcomeLabel, BorderLayout.NORTH);

        // Panel untuk tombol fitur
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton manageUsersButton = new JButton("Manage Users");
        JButton addBookManualButton = new JButton("Tambah Buku Manual");
        JButton addBookISBNButton = new JButton("Tambah Buku dengan ISBN");
        JButton deleteBookButton = new JButton("Hapus Buku");
        JButton logoutButton = new JButton("Logout");

        buttonPanel.add(manageUsersButton);
        buttonPanel.add(addBookManualButton);
        buttonPanel.add(addBookISBNButton);
        buttonPanel.add(deleteBookButton);
        buttonPanel.add(logoutButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Initialize genreComboBox
        genreComboBox = new JComboBox<>(new String[]{"Semua", "Fiksi", "Non-Fiksi", "Sejarah", "Sains"});
        add(genreComboBox, BorderLayout.NORTH);

        // Tambahkan ActionListener ke genreComboBox
        genreComboBox.addActionListener(e -> {
            String selectedGenre = (String) genreComboBox.getSelectedItem();
            loadBukuList(selectedGenre);
        });

        // Action listeners
        addBookManualButton.addActionListener(e -> {
            AddBookManualFrame addBookManualFrame = new AddBookManualFrame(this, () -> loadBukuList("Semua"));
            addBookManualFrame.setVisible(true);
        });

        addBookISBNButton.addActionListener(e -> {
            AddBookISBNFrame addBookISBNFrame = new AddBookISBNFrame(this, () -> loadBukuList("Semua"));
            addBookISBNFrame.setVisible(true);
        });

        deleteBookButton.addActionListener(e -> deleteSelectedBook());
        logoutButton.addActionListener(e -> {
            new LoginFrame().setVisible(true);
            dispose();
        });
    }

    public void loadBukuList(String genre) {
        List<Buku> bukuListData = bukuDAO.getAllBuku();
        bukuListModel.clear();
        for (Buku buku : bukuListData) {
            if (genre.equals("Semua") || buku.getGenre().equalsIgnoreCase(genre)) {
                bukuListModel.addElement(buku);
            }
        }
    }

    private void deleteSelectedBook() {
        Buku selectedBuku = bukuList.getSelectedValue();
        if (selectedBuku != null) {
            int confirm = JOptionPane.showConfirmDialog(this, "Apakah Anda yakin ingin menghapus buku ini?", "Konfirmasi Hapus", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                bukuDAO.deleteBuku(selectedBuku.getId());
                loadBukuList((String) genreComboBox.getSelectedItem());
            }
        } else {
            JOptionPane.showMessageDialog(this, "Pilih buku yang ingin dihapus.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private class BukuListCellRenderer extends JPanel implements ListCellRenderer<Buku> {
        private JLabel imageLabel = new JLabel();
        private JLabel titleLabel = new JLabel();

        public BukuListCellRenderer() {
            setLayout(new BorderLayout(5, 5));
            setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
            
            imageLabel.setPreferredSize(new Dimension(140, 160));
            imageLabel.setHorizontalAlignment(JLabel.CENTER);
            titleLabel.setHorizontalAlignment(JLabel.CENTER);
            
            add(imageLabel, BorderLayout.CENTER);
            add(titleLabel, BorderLayout.SOUTH);
        }

        @Override
        public Component getListCellRendererComponent(JList<? extends Buku> list, 
                Buku buku, int index, boolean isSelected, boolean cellHasFocus) {
            
            // Load and scale the book cover image
            ImageIcon imageIcon = new ImageIcon(buku.getGambarSampul());
            Image image = imageIcon.getImage().getScaledInstance(140, 160, Image.SCALE_SMOOTH);
            imageLabel.setIcon(new ImageIcon(image));
            
            titleLabel.setText("<html><center>" + buku.getJudul() + "</center></html>");
            
            setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
            setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());
            
            return this;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            AdminFrame adminFrame = new AdminFrame();
            adminFrame.setVisible(true);
        });
    }
}
