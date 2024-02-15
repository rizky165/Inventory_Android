package com.rizkyfachrieza.inventoryapps.model;

public class ModelSatuan {
    public String id;
    public String nama_satuan;

    public ModelSatuan(String id, String nama_satuan) {
        this.id = id;
        this.nama_satuan = nama_satuan;
    }

    public String getId() {
        return id;
    }

    public String getSatuan() {
        return nama_satuan;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSatuan(String nama_satuan) {
        this.nama_satuan = nama_satuan;
    }


}
