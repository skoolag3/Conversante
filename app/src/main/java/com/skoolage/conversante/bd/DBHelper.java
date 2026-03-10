package com.skoolage.conversante.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DBVersion = 1;

    public DBHelper(Context context) {
        super(context, "Uatizap2", null, DBVersion);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE Conversantes (Id integer primary key not null," +
                   " Nome text not null," +
                   " Celular text not null, Email text not null)");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("Atualizacao de bd", "V.Antiga: " + oldVersion +
                " - N.Versao: " + newVersion);
        db.execSQL("drop table if exists Conversantes");
        onCreate(db);
    }

}
