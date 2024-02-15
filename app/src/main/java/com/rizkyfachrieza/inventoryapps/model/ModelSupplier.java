package com.rizkyfachrieza.inventoryapps.model;

public class ModelSupplier {
    public String id;
    public String supplier;

    public ModelSupplier(String id, String supplier) {
        this.id = id;
        this.supplier = supplier;
    }

    public String getId() {
        return id;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }


}
