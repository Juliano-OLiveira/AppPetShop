package com.juliano.pet_shopp.Util;

import java.io.Serializable;

public class Raca implements Serializable {
    private int id;
    private String raca;

    public Raca(int id, String raca) {
        this.id = id;
        this.raca = raca;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRaca() {
        return raca;
    }

    public void setRaca(String raca) {
        this.raca = raca;
    }

    @Override
    public String toString() {
        return this.getRaca();
    }
    //comparando os objetos == ou não
    @Override
    public boolean equals(Object obj) {
        return this.getId() == ((Raca) obj).getId();
    }
}
