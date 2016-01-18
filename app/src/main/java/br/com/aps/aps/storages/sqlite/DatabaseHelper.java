package br.com.aps.aps.storages.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import br.com.aps.aps.storages.sqlite.location.LocationContract;
import br.com.aps.aps.storages.sqlite.user.UserContract;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String BANCO_DADOS = "APS_DB";
    private static int VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DatabaseHelper.BANCO_DADOS, null, DatabaseHelper.VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(UserContract.createTable());
        db.execSQL(LocationContract.createTable());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) { }

}
