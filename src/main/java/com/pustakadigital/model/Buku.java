package com.pustakadigital.model;

public class Buku {
    private int id;
    private String judul;
    private String penulis;
    private String genre;
    private int tahun;
    private String gambarSampul;
    private String isbn;


    public Buku(int id, String judul, String penulis, String genre, int tahun, String gambarSampul) {
        this(id, judul, penulis, genre, tahun, gambarSampul, null);
    }


    public Buku(int id, String judul, String penulis, String genre, int tahun, String gambarSampul, String isbn) {
        this.id = id;
        this.judul = judul;
        this.penulis = penulis;
        this.genre = genre;
        this.tahun = tahun;
        this.gambarSampul = gambarSampul;
        this.isbn = isbn;
    }


    public Buku(String judul, String penulis, String genre, String gambarSampul, String isbn) {
        this.judul = judul;
        this.penulis = penulis;
        this.genre = genre;
        this.gambarSampul = gambarSampul;
        this.isbn = isbn;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }


    public String getPenulis() {
        return penulis;
    }

    public void setPenulis(String penulis) {
        this.penulis = penulis;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }


    public int getTahun() {
        return tahun;
    }

    public void setTahun(int tahun) {
        this.tahun = tahun;
    }


    public String getGambarSampul() {
        return gambarSampul;
    }

    public void setGambarSampul(String gambarSampul) {
        this.gambarSampul = gambarSampul;
    }

    public String getIsbn() {
        return isbn;
    }

    @Override
    public String toString() {
        return "Buku{" +
                "id=" + id +
                ", judul='" + judul + '\'' +
                ", penulis='" + penulis + '\'' +
                ", genre='" + genre + '\'' +
                ", tahun=" + tahun +
                ", gambarSampul='" + gambarSampul + '\'' +
                ", isbn='" + isbn + '\'' +
                '}';
    }
}
