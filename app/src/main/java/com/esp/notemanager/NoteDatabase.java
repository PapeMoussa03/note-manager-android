package com.esp.notemanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Helper pour la gestion de la base de données SQLite.
 */
public class NoteDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "notes_db";
    private static final int DATABASE_VERSION = 1;

    // Nom de la table et colonnes
    public static final String TABLE_NOTES = "notes";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TITRE = "titre";
    public static final String COLUMN_CONTENU = "contenu";
    public static final String COLUMN_COULEUR = "couleur";
    public static final String COLUMN_FAVORI = "favori";
    public static final String COLUMN_DATE = "date";

    // Script de création de la table
    private static final String CREATE_TABLE_NOTES = "CREATE TABLE " + TABLE_NOTES + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_TITRE + " TEXT, " +
            COLUMN_CONTENU + " TEXT, " +
            COLUMN_COULEUR + " TEXT, " +
            COLUMN_FAVORI + " INTEGER DEFAULT 0, " +
            COLUMN_DATE + " TEXT" +
            ");";

    public NoteDatabase(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Exécution du script de création
        db.execSQL(CREATE_TABLE_NOTES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Suppression de la table existante et re-création en cas de mise à jour
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES);
        onCreate(db);
    }
}
