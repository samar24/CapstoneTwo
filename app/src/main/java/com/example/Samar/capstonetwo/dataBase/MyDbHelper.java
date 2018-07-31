package com.example.Samar.capstonetwo.dataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;




public class MyDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "sub.db";
    private static final int DATABASE_VERSION = 10;

    public MyDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        final String SQL_CREATE_SUBRED_TABLE = "CREATE TABLE " + MyContract.sunRedditEntry.TABLE_NAME + " (" +
                MyContract.sunRedditEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                MyContract.sunRedditEntry.COLUMN_SUBRED_AUTHOR+ " TEXT , " +
                MyContract.sunRedditEntry.COLUMN_SUBRED_IMAGE+ " TEXT , " +
                MyContract.sunRedditEntry.COLUMN_SUBRED_ID+ " TEXT UNIQUE , " +
                MyContract.sunRedditEntry.COLUMN_SUBRED_TITLE+ " TEXT )";

        sqLiteDatabase.execSQL(SQL_CREATE_SUBRED_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + MyContract.sunRedditEntry.TABLE_NAME);
        onCreate(db);
    }
}


