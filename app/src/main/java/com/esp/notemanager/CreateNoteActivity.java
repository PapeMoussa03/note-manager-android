package com.esp.notemanager;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CreateNoteActivity extends AppCompatActivity {

    private EditText editTextTitre, editTextContenu;
    private LinearLayout layoutNoteCard;
    private String couleurChoisie;
    private NoteDatabase noteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        editTextTitre = findViewById(R.id.editTextTitre);
        editTextContenu = findViewById(R.id.editTextContenu);
        layoutNoteCard = findViewById(R.id.layoutNoteCard);
        Button buttonCreer = findViewById(R.id.buttonCreer);

        noteDatabase = new NoteDatabase(this);

        // TEST CONNEXION — à supprimer après vérification
        try {
            List<Note> notes = noteDatabase.getAll();
            Toast.makeText(this,
                    "✅ DB connectée — " + notes.size() + " note(s)",
                    Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this,
                    "❌ Erreur DB : " + e.getMessage(),
                    Toast.LENGTH_LONG).show();
        }

        // Récupérer la couleur depuis MainActivity
        couleurChoisie = getIntent().getStringExtra("couleur");
        if (couleurChoisie == null || couleurChoisie.isEmpty()) {
            couleurChoisie = "#219653";
        }

        appliquerCouleur(couleurChoisie);

        buttonCreer.setOnClickListener(v -> enregistrerNote());
    }

    private void appliquerCouleur(String couleur) {
        GradientDrawable drawable = new GradientDrawable();
        drawable.setShape(GradientDrawable.RECTANGLE);
        drawable.setCornerRadius(16 * getResources().getDisplayMetrics().density);
        drawable.setColor(Color.parseColor(couleur));
        layoutNoteCard.setBackground(drawable);
    }

    private void enregistrerNote() {
        String titre = editTextTitre.getText().toString().trim();
        String contenu = editTextContenu.getText().toString().trim();

        if (TextUtils.isEmpty(titre)) {
            Toast.makeText(this, "Veuillez saisir un titre.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(contenu)) {
            Toast.makeText(this, "Veuillez saisir un contenu.", Toast.LENGTH_SHORT).show();
            return;
        }

        String date = new SimpleDateFormat("dd MMMM yyyy", Locale.FRENCH).format(new Date());

        Note note = new Note();
        note.setTitre(titre);
        note.setContenu(contenu);
        note.setCouleur(couleurChoisie);
        note.setDate(date);
        note.setFavori(false);

        noteDatabase.insert(note);

        Toast.makeText(this, "Note créée !", Toast.LENGTH_SHORT).show();

        setResult(RESULT_OK);
        finish();
    }
}