package com.rizkyfachrieza.inventoryapps.model;

public class ModelBarangMasuk {

    public String id;
    public String kode_barang;
    public String nama_barang;
    public String stok_barang;
    public String satuan;
    public String supplier;
    public String tgl_masuk;

    public ModelBarangMasuk(String id, String kode_barang, String nama_barang,String tgl_masuk, String supplier, String stok_barang, String satuan) {
        this.id = id;
        this.kode_barang = kode_barang;
        this.nama_barang = nama_barang;
        this.stok_barang = stok_barang;
        this.satuan = satuan;
        this.supplier = supplier;
        this.tgl_masuk = tgl_masuk;
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

    public String getStokBarang() {
        return stok_barang;
    }
    public String getSatuan() {
        return satuan;
    }
    public String getSupplier() {
        return supplier;
    }
    public String getTglMasuk() {
        return tgl_masuk;
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

    public void setStokBarang(String stok_barang) {
        this.stok_barang = stok_barang;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public void setSupplier(String supplier) {
        this.satuan = supplier;
    }

    public void setTglMasuk(String tgl_masuk) {
        this.satuan = tgl_masuk;
    }

}
