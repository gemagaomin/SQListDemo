package com.gm.soft.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;

public class MySQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String name = "JZGK.db";
    private static final int version = 1;

    public MySQLiteOpenHelper(Context context, String path) {
        super(context, path + File.separator + name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String trainTypeSql = "CREATE TABLE  IF NOT EXISTS traintype(" +
                "traintypeid char(3) PRIMARY KEY," +
                "traintypename varchar(16) NOT NULL )";
        db.execSQL(trainTypeSql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
