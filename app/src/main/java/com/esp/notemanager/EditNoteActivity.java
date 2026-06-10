package com.esp.notemanager;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditNoteActivity extends AppCompatActivity {

    public static final String EXTRA_NOTE_ID      = "note_id";
    public static final String EXTRA_NOTE_TITRE   = "note_titre";
    public static final String EXTRA_NOTE_CONTENU = "note_contenu";
    public static final String EXTRA_NOTE_COULEUR = "note_couleur";
    public static final String EXTRA_NOTE_FAVORI  = "note_favori";
    public static final String EXTRA_NOTE_DATE    = "note_date";

    private EditText editTitre;
    private EditText editContenu;
    private Button   btnModifier;
    private View     rootLayout;

    private NoteDAO  noteDAO;

    private int     noteId;
    private boolean noteFavori;
    private String  noteDate;
    private String  noteCouleur;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        editTitre   = findViewById(R.id.editTitre);
        editContenu = findViewById(R.id.editContenu);
        btnModifier = findViewById(R.id.btnModifier);
        rootLayout  = findViewById(R.id.rootLayout);

        noteDAO = new NoteDAO(this);

        recupererDonneeIntent();

        btnModifier.setOnClickListener(v -> sauvegarderModification());
    }

    private void recupererDonneeIntent() {
        Intent intent = getIntent();

        if (intent == null) {
            Toast.makeText(this, "Erreur : aucune note reçue", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        noteId      = intent.getIntExtra(EXTRA_NOTE_ID, -1);
        noteFavori  = intent.getBooleanExtra(EXTRA_NOTE_FAVORI, false);
        noteDate    = intent.getStringExtra(EXTRA_NOTE_DATE);
        noteCouleur = intent.getStringExtra(EXTRA_NOTE_COULEUR);

        String titreCourant   = intent.getStringExtra(EXTRA_NOTE_TITRE);
        String contenuCourant = intent.getStringExtra(EXTRA_NOTE_CONTENU);

        if (noteId == -1) {
            Toast.makeText(this, "Erreur : note introuvable", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        editTitre.setText(titreCourant);
        editContenu.setText(contenuCourant);

        appliquerCouleur(noteCouleur);
    }

    private void sauvegarderModification() {
        String nouveauTitre   = editTitre.getText().toString().trim();
        String nouveauContenu = editContenu.getText().toString().trim();

        if (TextUtils.isEmpty(nouveauTitre)) {
            editTitre.setError("Le titre ne peut pas être vide");
            editTitre.requestFocus();
            return;
        }

        if (TextUtils.isEmpty(nouveauContenu)) {
            editContenu.setError("Le contenu ne peut pas être vide");
            editContenu.requestFocus();
            return;
        }

        Note noteModifiee = new Note(
                noteId,
                nouveauTitre,
                nouveauContenu,
                noteCouleur,
                noteFavori,
                noteDate
        );

        noteDAO.update(noteModifiee);

        Toast.makeText(this, "Note modifiée avec succès", Toast.LENGTH_SHORT).show();

        setResult(RESULT_OK);
        finish();
    }

    private void appliquerCouleur(String couleurHex) {
        if (couleurHex == null || couleurHex.isEmpty()) return;

        try {
            String hex = couleurHex.startsWith("#") ? couleurHex : "#" + couleurHex;
            rootLayout.setBackgroundColor(Color.parseColor(hex));
        } catch (IllegalArgumentException e) {
            // Couleur invalide
        }
    }
}