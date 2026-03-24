package com.skoolage.conversante.models;

import java.io.Serializable;

public class Conversantes implements Serializable {

    private int Id;
    private String Nome;
    private String Celular;
    private String Email;

    public Conversantes(int id, String nome, String celular, String email) {
        Id = id;
        Nome = nome;
        Celular = celular;
        Email = email;
    }

    public Conversantes(String nome, String celular, String email) {
        Nome = nome;
        Celular = celular;
        Email = email;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getCelular() {
        return Celular;
    }

    public void setCellular(String celular) {
        Celular = celular;
    }
}
