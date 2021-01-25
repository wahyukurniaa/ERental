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
    public String URL_SLIDER = HOST + "slider/";

}
