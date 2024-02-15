package com.rizkyfachrieza.inventoryapps.model;

public class ModelBarang {

    public String id;
    public String kode_barang;
    public String nama_barang;
    public String kategori;
    public String harga;
    public String stok_barang;
    public String satuan;

    public ModelBarang(String id, String kode_barang, String nama_barang, String kategori, String harga, String stok_barang, String satuan) {
        this.id = id;
        this.kode_barang = kode_barang;
        this.nama_barang = nama_barang;
        this.kategori = kategori;
        this.harga = harga;
        this.stok_barang = stok_barang;
        this.satuan = satuan;
    }

    public String getId() {
        return id;
    }

    public String getKodeBarang() {
        return kode_barang;
    }

    public String getNamaBarang() {
        return nama_barang;
    }

    public String getKategori() {
        return kategori;
    }

    public String getHarga() {
        return harga;
    }

    public String getStokBarang() {
        return stok_barang;
    }

    public String getSatuan() {
        return satuan;
    }


    public void setId(String id) {
        this.id = id;
    }

    public void setKodeBarang(String kode_barang) {
        this.kode_barang = kode_barang;
    }

    public void setNamaBarang(String nama_barang) {
        this.nama_barang = nama_barang;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public void setHarga(String harga) {
        this.harga = harga;
    }

    public void setStokBarang(String stok_barang) {
        this.stok_barang = stok_barang;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }
}
