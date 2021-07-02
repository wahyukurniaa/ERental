package com.wahyukurnia.erental.Barang;



public class Model_Barang {
        private String id_barang;
        private String id_user;
        private String id_kategori;
        private String id_store;
        private String nama_barang;
        private String tarif_barang;
        private String deskripsi;
        private String stok;
        private String gambar_barang;
        private String nama_user;
        private String alamat_user;
        private String email_user;
        private String telp_user;
        private String username;
        private String password;

    public Model_Barang(String id_barang, String id_user, String id_kategori, String id_store, String nama_barang, String tarif_barang, String deskripsi, String stok, String gambar_barang, String nama_user, String alamat_user, String email_user, String telp_user, String username, String password) {
        this.id_barang = id_barang;
        this.id_user = id_user;
        this.id_kategori = id_kategori;
        this.id_store = id_store;
        this.nama_barang = nama_barang;
        this.tarif_barang = tarif_barang;
        this.deskripsi = deskripsi;
        this.stok = stok;
        this.gambar_barang = gambar_barang;
        this.nama_user = nama_user;
        this.alamat_user = alamat_user;
        this.email_user = email_user;
        this.telp_user = telp_user;
        this.username = username;
        this.password = password;
    }

    public String getId_barang() {
        return id_barang;
    }

    public void setId_barang(String id_barang) {
        this.id_barang = id_barang;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getId_kategori() {
        return id_kategori;
    }

    public void setId_kategori(String id_kategori) {
        this.id_kategori = id_kategori;
    }

    public String getId_store() {
        return id_store;
    }

    public void setId_store(String id_store) {
        this.id_store = id_store;
    }

    public String getNama_barang() {
        return nama_barang;
    }

    public void setNama_barang(String nama_barang) {
        this.nama_barang = nama_barang;
    }

    public String getTarif_barang() {
        return tarif_barang;
    }

    public void setTarif_barang(String tarif_barang) {
        this.tarif_barang = tarif_barang;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getStok() {
        return stok;
    }

    public void setStok(String stok) {
        this.stok = stok;
    }

    public String getGambar_barang() {
        return gambar_barang;
    }

    public void setGambar_barang(String gambar_barang) {
        this.gambar_barang = gambar_barang;
    }

    public String getNama_user() {
        return nama_user;
    }

    public void setNama_user(String nama_user) {
        this.nama_user = nama_user;
    }

    public String getAlamat_user() {
        return alamat_user;
    }

    public void setAlamat_user(String alamat_user) {
        this.alamat_user = alamat_user;
    }

    public String getEmail_user() {
        return email_user;
    }

    public void setEmail_user(String email_user) {
        this.email_user = email_user;
    }

    public String getTelp_user() {
        return telp_user;
    }

    public void setTelp_user(String telp_user) {
        this.telp_user = telp_user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

