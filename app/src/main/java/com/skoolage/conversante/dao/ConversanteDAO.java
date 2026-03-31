package com.skoolage.conversante.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.skoolage.conversante.bd.DBHelper;
import com.skoolage.conversante.models.Conversantes;

import java.util.ArrayList;
import java.util.List;

public class ConversanteDAO {

    private SQLiteDatabase db;
    private DBHelper helper;

    public ConversanteDAO(Context context) {
        helper = new DBHelper(context);
    }

    public void abrir() {
        db = helper.getWritableDatabase();
    }

    public void fechar() {
        if (db != null && db.isOpen()) {
            db.close();
        }
    }

    // INSERT
    public long inserir(Conversantes c) {
        abrir();

        ContentValues dados = new ContentValues();
        dados.put("Nome", c.getNome());
        dados.put("Celular", c.getCelular());
        dados.put("Email", c.getEmail());

        long id = db.insert("Conversantes", null, dados);

        fechar();
        return id;
    }

    // UPDATE
    public void alterar(Conversantes c) {
        if (c.getId() <= 0) return;

        abrir();

        ContentValues dados = new ContentValues();
        dados.put("Nome", c.getNome());
        dados.put("Celular", c.getCelular());
        dados.put("Email", c.getEmail());


        db.update("Conversantes", dados,
                "Id = ?", new String[]{String.valueOf(c.getId())});

        fechar();
    }

    // DELETE
    public void excluir(Conversantes c) {
        if (c.getId() <= 0) return;

        abrir();

        db.delete(
                "Conversantes",
                "Id = ?",
                new String[]{String.valueOf(c.getId())}
        );

        fechar();
    }

    // SELECT ALL
    public List<Conversantes> listarTudo() {
        abrir();

        List<Conversantes> lista = new ArrayList<>();
        String[] campos = {"Id", "Nome", "Celular", "Email"};

        Cursor dados = db.query(
                "Conversantes",
                campos,
                null,
                null,
                null,
                null,
                "Nome"
        );

        if (dados.moveToFirst()) {
            do {
                Conversantes c = new Conversantes(
                        dados.getInt(0),
                        dados.getString(1),
                        dados.getString(2),
                        dados.getString(3)
                );
                lista.add(c);
            } while (dados.moveToNext());
        }

        dados.close();
        fechar();
        return lista;
    }

    // SELECT POR ID (abrir registro específico)
    public Conversantes buscarPorId(int id) {
        abrir();

        String[] campos = {"Id", "Nome", "Celular", "Email"};
        String[] whereArgs = {String.valueOf(id)};

        Cursor dados = db.query(
                "Conversantes",
                campos,
                "Id = ?",
                whereArgs,
                null,
                null,
                null
        );

        Conversantes c = null;

        if (dados.moveToFirst()) {
            c = new Conversantes(
                    dados.getInt(0),
                    dados.getString(1),
                    dados.getString(2),
                    dados.getString(3)
            );
        }

        dados.close();
        fechar();
        return c;
    }
}