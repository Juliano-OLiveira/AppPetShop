package com.juliano.pet_shopp.Util;

import java.io.Serializable;

public class Proprietario implements Serializable {
    private int id;
    private String proprietario;

    public Proprietario(int id, String proprietario) {
        this.id = id;
        this.proprietario = proprietario;
    }

    public Proprietario(int idRaca) {
    }

    public Proprietario() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProprietario() {
        return proprietario;
    }

    public void setProprietario(String proprietario) {
        this.proprietario = proprietario;
    }

    @Override
    public String toString() {
        return this.getProprietario();
    }

    //comparando os objetos == ou não
    @Override
    public boolean equals(Object obj) {
        return this.getId() == ((Proprietario) obj).getId();
    }
}
