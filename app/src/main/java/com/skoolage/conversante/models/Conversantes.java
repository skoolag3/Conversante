package com.skoolage.conversante.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Conversantes implements Serializable, Parcelable {

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

    public Conversantes() {}

    public Conversantes(String nome, String celular, String email) {
        Nome = nome;
        Celular = celular;
        Email = email;
    }

    // ===== PARCELABLE =====
    protected Conversantes(Parcel in) {
        Id = in.readInt();
        Nome = in.readString();
        Celular = in.readString();
        Email = in.readString();
    }

    public static final Creator<Conversantes> CREATOR = new Creator<Conversantes>() {
        @Override
        public Conversantes createFromParcel(Parcel in) {
            return new Conversantes(in);
        }

        @Override
        public Conversantes[] newArray(int size) {
            return new Conversantes[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(Id);
        dest.writeString(Nome);
        dest.writeString(Celular);
        dest.writeString(Email);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    // ===== GETTERS / SETTERS =====
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