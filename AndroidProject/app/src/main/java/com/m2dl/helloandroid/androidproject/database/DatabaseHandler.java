package com.m2dl.helloandroid.androidproject.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ctalon on 15/01/15.
 */
public class DatabaseHandler extends SQLiteOpenHelper {

    public static final String KEY = "id";
    public static final String INTITULE = "entity_name";
    public static final String COMMENTAIRE = "commentaire";
    public static final String LOCALISATION = "localisation";
    public static final String SPECIFICATION = "specification";
    public static final String PHOTO = "photo";

    public static final String TABLE_NAME = "LivingEntity";
    public static final String TABLE_CREATE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    KEY + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    INTITULE + " TEXT, " +
                    COMMENTAIRE + " TEXT, " +
                    LOCALISATION + " TEXT, " +
                    PHOTO + " BLOB, " +
                    SPECIFICATION + " TEXT);";
    public static final String TABLE_DROP = "DROP TABLE IF EXISTS " + TABLE_NAME + ";";


    public DatabaseHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(TABLE_CREATE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(TABLE_DROP);
        onCreate(db);
    }
}
