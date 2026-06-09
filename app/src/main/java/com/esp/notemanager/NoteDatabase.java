package com.esp.notemanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class NoteDatabase extends SQLiteOpenHelper {

    private static final String DB_NAME = "notes.db";
    private static final int DB_VERSION = 1;
    private static final String TABLE = "notes";

    public NoteDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE + " (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "titre TEXT," +
                "contenu TEXT," +
                "couleur TEXT," +
                "favori INTEGER," +
                "date TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db);
    }

    public void insert(Note note) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("titre", note.getTitre());
        values.put("contenu", note.getContenu());
        values.put("couleur", note.getCouleur());
        values.put("favori", note.isFavori() ? 1 : 0);
        values.put("date", note.getDate());
        db.insert(TABLE, null, values);
        db.close();
    }

    public List<Note> getAll() {
        List<Note> notes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE, null);
        if (cursor.moveToFirst()) {
            do {
                Note n = new Note();
                n.setId(cursor.getInt(0));
                n.setTitre(cursor.getString(1));
                n.setContenu(cursor.getString(2));
                n.setCouleur(cursor.getString(3));
                n.setFavori(cursor.getInt(4) == 1);
                n.setDate(cursor.getString(5));
                notes.add(n);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return notes;
    }
}