package com.wahyukurnia.erental.Rating;

public class ModelRating {
    private String id_ulasan;
    private String id_barang;
    private String id_user;
    private String review;
    private String bintang;
    private String date;
    private String nama_user;
    private String alamat_user;
    private String email_user;
    private String telp_user;
    private String username;
    private String password;

    public ModelRating(String id_ulasan, String id_barang, String id_user, String review, String bintang, String date, String nama_user, String alamat_user, String email_user, String telp_user, String username, String password) {
        this.id_ulasan = id_ulasan;
        this.id_barang = id_barang;
        this.id_user = id_user;
        this.review = review;
        this.bintang = bintang;
        this.date = date;
        this.nama_user = nama_user;
        this.alamat_user = alamat_user;
        this.email_user = email_user;
        this.telp_user = telp_user;
        this.username = username;
        this.password = password;
    }

    public String getId_ulasan() {
        return id_ulasan;
    }

    public void setId_ulasan(String id_ulasan) {
        this.id_ulasan = id_ulasan;
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

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public String getBintang() {
        return bintang;
    }

    public void setBintang(String bintang) {
        this.bintang = bintang;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
