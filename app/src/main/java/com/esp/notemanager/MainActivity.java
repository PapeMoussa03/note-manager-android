package com.esp.notemanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainActivity extends AppCompatActivity {

    private FloatingActionButton fabAdd;
    private LinearLayout colorPalette;
    private TextView tvAucuneNotes;
    private RecyclerView recyclerView;
    private EditText searchEditText;
    private Button btnFavoris;
    private boolean isPaletteOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fabAdd = findViewById(R.id.fabAdd);
        colorPalette = findViewById(R.id.colorPalette);
        tvAucuneNotes = findViewById(R.id.tvAucuneNotes);
        recyclerView = findViewById(R.id.recyclerView);
        searchEditText = findViewById(R.id.searchEditText);
        btnFavoris = findViewById(R.id.btnFavoris);

        // Couleurs des cercles
        findViewById(R.id.colorGreen).setBackgroundColor(getResources().getColor(R.color.green, null));
        findViewById(R.id.colorRed).setBackgroundColor(getResources().getColor(R.color.red, null));
        findViewById(R.id.colorBlue).setBackgroundColor(getResources().getColor(R.color.blue, null));
        findViewById(R.id.colorYellow).setBackgroundColor(getResources().getColor(R.color.yellow, null));
        findViewById(R.id.colorOrange).setBackgroundColor(getResources().getColor(R.color.orange, null));
        findViewById(R.id.colorGray).setBackgroundColor(getResources().getColor(R.color.gray, null));

        // Rendre les cercles ronds
        setCircleShape(R.id.colorGreen);
        setCircleShape(R.id.colorRed);
        setCircleShape(R.id.colorBlue);
        setCircleShape(R.id.colorYellow);
        setCircleShape(R.id.colorOrange);
        setCircleShape(R.id.colorGray);

        // FAB click - ouvrir/fermer palette
        fabAdd.setOnClickListener(v -> {
            if (isPaletteOpen) {
                colorPalette.setVisibility(View.GONE);
                isPaletteOpen = false;
            } else {
                colorPalette.setVisibility(View.VISIBLE);
                isPaletteOpen = true;
            }
        });

        // Clic sur chaque couleur -> ouvrir CreateNoteActivity
        findViewById(R.id.colorGreen).setOnClickListener(v -> openCreateNote("#219653"));
        findViewById(R.id.colorRed).setOnClickListener(v -> openCreateNote("#EB5757"));
        findViewById(R.id.colorBlue).setOnClickListener(v -> openCreateNote("#2F80ED"));
        findViewById(R.id.colorYellow).setOnClickListener(v -> openCreateNote("#F2C94C"));
        findViewById(R.id.colorOrange).setOnClickListener(v -> openCreateNote("#F2994A"));
        findViewById(R.id.colorGray).setOnClickListener(v -> openCreateNote("#828282"));

        // Afficher "Aucune notes" si liste vide (sera géré par MND plus tard)
        tvAucuneNotes.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    private void setCircleShape(int viewId) {
        View view = findViewById(viewId);
        view.post(() -> {
            android.graphics.drawable.GradientDrawable drawable =
                    new android.graphics.drawable.GradientDrawable();
            drawable.setShape(android.graphics.drawable.GradientDrawable.OVAL);
            drawable.setColor(((android.graphics.drawable.ColorDrawable)
                    view.getBackground()).getColor());
            view.setBackground(drawable);
        });
    }

    private void openCreateNote(String color) {
        colorPalette.setVisibility(View.GONE);
        isPaletteOpen = false;
        Intent intent = new Intent(this, CreateNoteActivity.class);
        intent.putExtra("color", color);
        startActivity(intent);
    }
}