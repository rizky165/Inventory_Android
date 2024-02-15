package com.rizkyfachrieza.inventoryapps.model;

public class ModelKategori {
    public String id;
    public String nama_kategori;

    public ModelKategori(String id, String nama_kategori) {
        this.id = id;
        this.nama_kategori = nama_kategori;
    }

    public String getId() {
        return id;
    }

    public String getKategori() {
        return nama_kategori;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setKategori(String nama_kategori) {
        this.nama_kategori = nama_kategori;
    }


}
