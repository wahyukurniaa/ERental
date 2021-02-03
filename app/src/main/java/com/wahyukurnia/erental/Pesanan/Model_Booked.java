package com.wahyukurnia.erental.Pesanan;

public class Model_Booked {

        private String id_sewa_barang;
        private String id_user;
        private String id_barang;
        private String id_status;
        private String banyak_sewa;
        private String tanggal_awal;
        private String tanggal_akhir;
        private String alamat_penyewa;
        private String jaminan;
        private String jenis_transaksi;
        private String jenis_pengiriman;
        private String total_harga;
        private String id_kategori;
        private String id_store;
        private String nama_barang;
        private String tarif_barang;
        private String deskripsi;
        private String stok;
        private String gambar_barang;
        private String status;
        private String nama_user;
        private String alamat_user;
        private String email_user;
        private String telp_user;
        private String username;
        private String password;

    public Model_Booked(String id_sewa_barang, String id_user, String id_barang, String id_status, String banyak_sewa, String tanggal_awal, String tanggal_akhir, String alamat_penyewa, String jaminan, String jenis_transaksi, String jenis_pengiriman, String total_harga, String id_kategori, String id_store, String nama_barang, String tarif_barang, String deskripsi, String stok, String gambar_barang, String status, String nama_user, String alamat_user, String email_user, String telp_user, String username, String password) {
        this.id_sewa_barang = id_sewa_barang;
        this.id_user = id_user;
        this.id_barang = id_barang;
        this.id_status = id_status;
        this.banyak_sewa = banyak_sewa;
        this.tanggal_awal = tanggal_awal;
        this.tanggal_akhir = tanggal_akhir;
        this.alamat_penyewa = alamat_penyewa;
        this.jaminan = jaminan;
        this.jenis_transaksi = jenis_transaksi;
        this.jenis_pengiriman = jenis_pengiriman;
        this.total_harga = total_harga;
        this.id_kategori = id_kategori;
        this.id_store = id_store;
        this.nama_barang = nama_barang;
        this.tarif_barang = tarif_barang;
        this.deskripsi = deskripsi;
        this.stok = stok;
        this.gambar_barang = gambar_barang;
        this.status = status;
        this.nama_user = nama_user;
        this.alamat_user = alamat_user;
        this.email_user = email_user;
        this.telp_user = telp_user;
        this.username = username;
        this.password = password;
    }

    public String getId_sewa_barang() {
        return id_sewa_barang;
    }

    public void setId_sewa_barang(String id_sewa_barang) {
        this.id_sewa_barang = id_sewa_barang;
    }

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getId_barang() {
        return id_barang;
    }

    public void setId_barang(String id_barang) {
        this.id_barang = id_barang;
    }

    public String getId_status() {
        return id_status;
    }

    public void setId_status(String id_status) {
        this.id_status = id_status;
    }

    public String getBanyak_sewa() {
        return banyak_sewa;
    }

    public void setBanyak_sewa(String banyak_sewa) {
        this.banyak_sewa = banyak_sewa;
    }

    public String getTanggal_awal() {
        return tanggal_awal;
    }

    public void setTanggal_awal(String tanggal_awal) {
        this.tanggal_awal = tanggal_awal;
    }

    public String getTanggal_akhir() {
        return tanggal_akhir;
    }

    public void setTanggal_akhir(String tanggal_akhir) {
        this.tanggal_akhir = tanggal_akhir;
    }

    public String getAlamat_penyewa() {
        return alamat_penyewa;
    }

    public void setAlamat_penyewa(String alamat_penyewa) {
        this.alamat_penyewa = alamat_penyewa;
    }

    public String getJaminan() {
        return jaminan;
    }

    public void setJaminan(String jaminan) {
        this.jaminan = jaminan;
    }

    public String getJenis_transaksi() {
        return jenis_transaksi;
    }

    public void setJenis_transaksi(String jenis_transaksi) {
        this.jenis_transaksi = jenis_transaksi;
    }

    public String getJenis_pengiriman() {
        return jenis_pengiriman;
    }

    public void setJenis_pengiriman(String jenis_pengiriman) {
        this.jenis_pengiriman = jenis_pengiriman;
    }

    public String getTotal_harga() {
        return total_harga;
    }

    public void setTotal_harga(String total_harga) {
        this.total_harga = total_harga;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

