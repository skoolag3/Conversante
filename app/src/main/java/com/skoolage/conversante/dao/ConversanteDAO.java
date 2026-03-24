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

    public void abrir(){
        db = helper.getWritableDatabase();
    }

    public void fechar(){
        helper.close();
    }

    public Long Inserir(Conversantes c){
        ContentValues dados = new ContentValues();
        dados.put("Nome", c.getNome());
        dados.put("Celular", c.getCelular());
        dados.put("Email", c.getEmail());

        return db.insert("Conversantes", null, dados);
    }

    public long Alterar(Conversantes c){
        ContentValues dados = new ContentValues();
        dados.put("Nome", c.getNome());
        dados.put("Celular", c.getCelular());
        dados.put("Email", c.getEmail());

        String[] whereArgs = {String.valueOf(c.getId())};
        return db.update("Conversantes", dados, "Id = ?", whereArgs);
    }

    public long Excluir(int id){
        String[] whereArgs = {String.valueOf(id)};
        return db.delete("Conversantes", "Id = ?", whereArgs);
    }

    public List<Conversantes> listarTudo(){
        List<Conversantes> lista = new ArrayList<>();
        String[] campos = new String[] {"Id", "Nome", "Celular", "Email"};

        Cursor dados = db.query("Conversantes", campos, null,
                null,null, null, "Nome");

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
        return lista;
    }
}