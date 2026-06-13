package com.esp.notemanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class NoteDAO {

    private final NoteDatabase noteDatabase;

    public NoteDAO(Context context) {
        noteDatabase = new NoteDatabase(context);
    }

    public void insert(Note note) {
        noteDatabase.insert(note);
    }

    public void update(Note note) {
        SQLiteDatabase db = noteDatabase.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("titre", note.getTitre());
        values.put("contenu", note.getContenu());
        values.put("couleur", note.getCouleur());
        values.put("favori", note.isFavori() ? 1 : 0);
        values.put("date", note.getDate());
        db.update("notes", values, "id=?",
                new String[]{String.valueOf(note.getId())});
        db.close();
    }

    public void delete(int id) {
        SQLiteDatabase db = noteDatabase.getWritableDatabase();
        db.delete("notes", "id=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public List<Note> getAll() {
        return noteDatabase.getAll();
    }

    public List<Note> getFavoris() {
        List<Note> notes = new ArrayList<>();
        SQLiteDatabase db = noteDatabase.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM notes WHERE favori=1", null);
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

    public List<Note> search(String query) {
        List<Note> notes = new ArrayList<>();
        SQLiteDatabase db = noteDatabase.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM notes WHERE titre LIKE ?",
                new String[]{"%" + query + "%"});
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