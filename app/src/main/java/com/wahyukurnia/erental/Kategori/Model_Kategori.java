package com.wahyukurnia.erental.Kategori;

public class Model_Kategori {
int id_kategori;
String nama_kategori,gambar_;

    public Model_Kategori(int id_kategori, String nama_kategori, String gambar_) {
        this.id_kategori = id_kategori;
        this.nama_kategori = nama_kategori;
        this.gambar_ = gambar_;
    }

    public int getId_kategori() {
        return id_kategori;
    }

    public void setId_kategori(int id_kategori) {
        this.id_kategori = id_kategori;
    }

    public String getNama_kategori() {
        return nama_kategori;
    }

    public void setNama_kategori(String nama_kategori) {
        this.nama_kategori = nama_kategori;
    }

    public String getGambar_() {
        return gambar_;
    }

    public void setGambar_(String gambar_) {
        this.gambar_ = gambar_;
    }
}
