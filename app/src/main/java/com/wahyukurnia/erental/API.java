package com.wahyukurnia.erental;

public class API {

        private String HOST = "http://192.168.100.36/rental/";


    public String URL_LOGIN = HOST + "Login.php";
    public String URL_REGISTER = HOST + "register.php";
    public String URL_Kategori = HOST + "select_kategori.php";
    public String URL_Isi_Kategori = HOST + "select_isi_kategori.php?id_kategori=";
    public String URL_Store = HOST + "insert_store.php";
    public String URL_GAMBAR = HOST + "gambar_kategori/";
    public String URL_GAMBAR_U = HOST + "gambar/";
  
    public String URL_USER = HOST + "select_user.php?id_user=";
    public String URL_UPDATE = HOST + "update_profil.php";
    public String URL_DESKRIPSI =  HOST + "select_deskripsi.php?id_barang=";

    public String URL_BARANG =  HOST + "input_barang.php";
    public String URL_SEWA_BARANG =  HOST + "input_sewa_barang.php";
    public String URL_SLIDER = HOST + "slider/";

    public String URL_BOOKED = HOST + "select_sewa_barang.php?id_user=";
    public String URL_NOTIF = HOST + "select_notif.php?id_user=";
    public String URL_KONFIRMASI = HOST + "update_konfirmasi.php";
    public String URL_TOLAK = HOST + "update_ditolak.php";
    public String URL_RATING = HOST + "input_ulasan.php";
    public String URL_SELECT_RATING = HOST + "select_ulasan.php";
    public String URL_SELECT_RATING_BRG = HOST + "select_ulasan_brg.php?id_barang=";
    public String URL_SEARCH  = HOST + "select_search.php?key=";



}
