package com.esp.notemanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Data Access Object (DAO) pour gérer les opérations CRUD sur les notes.
 */
public class NoteDAO {

    private SQLiteDatabase db;
    private NoteDatabase dbHelper;

    public NoteDAO(Context context) {
        dbHelper = new NoteDatabase(context);
    }

    public void open() {
        db = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    /**
     * Insère une nouvelle note en base.
     */
    public long insertNote(Note note) {
        open();
        ContentValues values = new ContentValues();
        values.put(NoteDatabase.COLUMN_TITRE, note.getTitre());
        values.put(NoteDatabase.COLUMN_CONTENU, note.getContenu());
        values.put(NoteDatabase.COLUMN_COULEUR, note.getCouleur());
        values.put(NoteDatabase.COLUMN_FAVORI, note.isFavori() ? 1 : 0);
        values.put(NoteDatabase.COLUMN_DATE, note.getDate());

        long id = db.insert(NoteDatabase.TABLE_NOTES, null, values);
        close();
        return id;
    }

    /**
     * Met à jour une note existante.
     */
    public int updateNote(Note note) {
        open();
        ContentValues values = new ContentValues();
        values.put(NoteDatabase.COLUMN_TITRE, note.getTitre());
        values.put(NoteDatabase.COLUMN_CONTENU, note.getContenu());
        values.put(NoteDatabase.COLUMN_COULEUR, note.getCouleur());
        values.put(NoteDatabase.COLUMN_FAVORI, note.isFavori() ? 1 : 0);
        values.put(NoteDatabase.COLUMN_DATE, note.getDate());

        int rows = db.update(NoteDatabase.TABLE_NOTES, values,
                NoteDatabase.COLUMN_ID + " = ?", new String[]{String.valueOf(note.getId())});
        close();
        return rows;
    }

    /**
     * Supprime une note par son ID.
     */
    public void deleteNote(int id) {
        open();
        db.delete(NoteDatabase.TABLE_NOTES,
                NoteDatabase.COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        close();
    }

    /**
     * Récupère toutes les notes triées par date décroissante.
     */
    public List<Note> getAllNotes() {
        List<Note> notes = new ArrayList<>();
        open();
        Cursor cursor = db.query(NoteDatabase.TABLE_NOTES, null, null, null, null, null,
                NoteDatabase.COLUMN_DATE + " DESC");

        if (cursor.moveToFirst()) {
            do {
                notes.add(cursorToNote(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return notes;
    }

    /**
     * Récupère uniquement les notes marquées comme favorites.
     */
    public List<Note> getFavoris() {
        List<Note> notes = new ArrayList<>();
        open();
        Cursor cursor = db.query(NoteDatabase.TABLE_NOTES, null,
                NoteDatabase.COLUMN_FAVORI + " = 1", null, null, null,
                NoteDatabase.COLUMN_DATE + " DESC");

        if (cursor.moveToFirst()) {
            do {
                notes.add(cursorToNote(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return notes;
    }

    /**
     * Recherche des notes par titre.
     */
    public List<Note> searchNotes(String query) {
        List<Note> notes = new ArrayList<>();
        open();
        Cursor cursor = db.query(NoteDatabase.TABLE_NOTES, null,
                NoteDatabase.COLUMN_TITRE + " LIKE ?", new String[]{"%" + query + "%"},
                null, null, NoteDatabase.COLUMN_DATE + " DESC");

        if (cursor.moveToFirst()) {
            do {
                notes.add(cursorToNote(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        close();
        return notes;
    }

    /**
     * Convertit un Cursor en objet Note.
     */
    private Note cursorToNote(Cursor cursor) {
        Note note = new Note();
        note.setId(cursor.getInt(cursor.getColumnIndexOrThrow(NoteDatabase.COLUMN_ID)));
        note.setTitre(cursor.getString(cursor.getColumnIndexOrThrow(NoteDatabase.COLUMN_TITRE)));
        note.setContenu(cursor.getString(cursor.getColumnIndexOrThrow(NoteDatabase.COLUMN_CONTENU)));
        note.setCouleur(cursor.getString(cursor.getColumnIndexOrThrow(NoteDatabase.COLUMN_COULEUR)));
        note.setFavori(cursor.getInt(cursor.getColumnIndexOrThrow(NoteDatabase.COLUMN_FAVORI)) == 1);
        note.setDate(cursor.getString(cursor.getColumnIndexOrThrow(NoteDatabase.COLUMN_DATE)));
        return note;
    }
}
