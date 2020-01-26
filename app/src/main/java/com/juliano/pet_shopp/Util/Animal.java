package com.juliano.pet_shopp.Util;

import java.io.Serializable;

public class Animal implements Serializable {

          private int id;
          private  String nome ;
          private Raca raca;
          private String porte;
          private Proprietario  proprietario;
          private String observacao;
          private Double peso;
          private int idade;
          private String sexo;
          private int foto;
          private String urlFoto;

    public Animal(int id, String nome, Raca raca, String porte, Proprietario proprietario,String observacao,double peso ,int idade,int foto, String urlFoto, String sexo) {
        this.id = id;
        this.nome= nome;
        this.raca = raca;
        this.porte= porte;
        this.observacao = observacao ;
        this.proprietario = proprietario;
        this.peso= peso;
        this.idade= idade;
        this.foto = foto;
        this.urlFoto = urlFoto;
        this.sexo = sexo;
    }

    public Animal() {

    }

    @Override
    public String toString() {
        return String.format("%s %s/%s %s/%s %s/%s", nome, porte, proprietario, observacao,raca,peso,idade);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Raca getRaca() {
        return raca;
    }


    public void setRaca(Raca raca) {
        this.raca = raca;
    }

    public String getPorte() {
        return porte;
    }

    public void setPorte(String porte) {
        this.porte = porte;
    }

    public Proprietario getProprietario() {
        return proprietario;
    }

    public void setProprietario(Proprietario proprietario) {
        this.proprietario = proprietario;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Double getPeso() {
        return peso;
    }

    public void setPeso(Double peso) {
        this.peso = peso;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }


    public int getFoto() {
        return foto;
    }

    public void setFoto(int foto) {
        this.foto = foto;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }


}
