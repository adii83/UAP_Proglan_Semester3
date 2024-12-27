package com.pustakadigital.model;

public class Buku {
    private int id;
    private String judul;
    private String penulis;
    private String genre;
    private int tahun;
    private String gambarSampul;
    private String isbn;

    // Constructor for AddBookManualFrame
    public Buku(int id, String judul, String penulis, String genre, int tahun, String gambarSampul) {
        this(id, judul, penulis, genre, tahun, gambarSampul, null);
    }

    // Constructor for AddBookISBNFrame
    public Buku(int id, String judul, String penulis, String genre, int tahun, String gambarSampul, String isbn) {
        this.id = id;
        this.judul = judul;
        this.penulis = penulis;
        this.genre = genre;
        this.tahun = tahun;
        this.gambarSampul = gambarSampul;
        this.isbn = isbn;
    }

    // Getter dan Setter untuk id
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    // Getter dan Setter untuk judul
    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    // Getter dan Setter untuk penulis
    public String getPenulis() {
        return penulis;
    }

    public void setPenulis(String penulis) {
        this.penulis = penulis;
    }

    // Getter dan Setter untuk genre
    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    // Getter dan Setter untuk tahun
    public int getTahun() {
        return tahun;
    }

    public void setTahun(int tahun) {
        this.tahun = tahun;
    }

    // Getter dan Setter untuk gambarSampul
    public String getGambarSampul() {
        return gambarSampul;
    }

    public void setGambarSampul(String gambarSampul) {
        this.gambarSampul = gambarSampul;
    }

    public String getIsbn() {
        return isbn;
    }
}
