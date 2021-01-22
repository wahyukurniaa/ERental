package com.wahyukurnia.erental.Kategori;

public class Model_IsiKategori {
    int id_barang;
    String nama_barang, tarif, gambar_barang;

    public Model_IsiKategori(int id_barang, String nama_barang, String tarif, String gambar_barang) {
        this.id_barang = id_barang;
        this.nama_barang = nama_barang;
        this.tarif = tarif;
        this.gambar_barang = gambar_barang;
    }

    public int getId_barang() {
        return id_barang;
    }

    public void setId_barang(int id_barang) {
        this.id_barang = id_barang;
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

    public String getGambar_barang() {
        return gambar_barang;
    }

    public void setGambar_barang(String gambar_barang) {
        this.gambar_barang = gambar_barang;
    }
}
