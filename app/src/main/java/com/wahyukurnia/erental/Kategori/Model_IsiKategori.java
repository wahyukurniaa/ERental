package com.wahyukurnia.erental.Kategori;

public class Model_IsiKategori {
    String nama_barang, tarif, deskripsi, gambar_barang;

    public Model_IsiKategori(String nama_barang, String tarif, String deskripsi, String gambar_barang) {
        this.nama_barang = nama_barang;
        this.tarif = tarif;
        this.deskripsi = deskripsi;
        this.gambar_barang = gambar_barang;
    }

    public String getNama_barang() {
        return nama_barang;
    }

    public void setNama_barang(String nama_barang) {
        this.nama_barang = nama_barang;
    }

    public String getTarif() {
        return tarif;
    }

    public void setTarif(String tarif) {
        this.tarif = tarif;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getGambar_barang() {
        return gambar_barang;
    }

    public void setGambar_barang(String gambar_barang) {
        this.gambar_barang = gambar_barang;
    }
}
