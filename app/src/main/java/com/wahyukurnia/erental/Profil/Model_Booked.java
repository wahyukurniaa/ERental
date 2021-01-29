package com.wahyukurnia.erental.Profil;

public class Model_Booked {
    int id_sewa_barang;
    String namaBarang,tanggal_pinjam, tanggal_kembali;


    public Model_Booked(int id_sewa_barang, String namaBarang, String tanggal_pinjam, String tanggal_kembali) {
        this.id_sewa_barang = id_sewa_barang;
        this.namaBarang = namaBarang;
        this.tanggal_pinjam = tanggal_pinjam;
        this.tanggal_kembali = tanggal_kembali;
    }

    public int getId_sewa_barang() {
        return id_sewa_barang;
    }

    public void setId_sewa_barang(int id_sewa_barang) {
        this.id_sewa_barang = id_sewa_barang;
    }

    public String getNamaBarang() {
        return namaBarang;
    }

    public void setNamaBarang(String namaBarang) {
        this.namaBarang = namaBarang;
    }

    public String getTanggal_pinjam() {
        return tanggal_pinjam;
    }

    public void setTanggal_pinjam(String tanggal_pinjam) {
        this.tanggal_pinjam = tanggal_pinjam;
    }

    public String getTanggal_kembali() {
        return tanggal_kembali;
    }

    public void setTanggal_kembali(String tanggal_kembali) {
        this.tanggal_kembali = tanggal_kembali;
    }
}
