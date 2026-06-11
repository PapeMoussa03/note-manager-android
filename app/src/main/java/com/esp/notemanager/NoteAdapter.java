package com.esp.notemanager;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    private List<Note> listeComplete;
    private List<Note> listeAffichee;
    private final Context context;
    private OnNoteClickListener listener;

    private String filtreRecherche = "";
    private boolean filtreFavorisActif = false;

    public interface OnNoteClickListener {
        void onNoteClick(Note note);
        void onNoteDoubleClick(Note note);
    }

    public NoteAdapter(Context context, List<Note> notes) {
        this.context = context;
        this.listeComplete = new ArrayList<>(notes);
        this.listeAffichee = new ArrayList<>(notes);
    }

    public void setOnNoteClickListener(OnNoteClickListener listener) {
        this.listener = listener;
    }

    public void setNotes(List<Note> notes) {
        this.listeComplete = new ArrayList<>(notes);
        appliquerFiltres();
    }

    private void appliquerFiltres() {
        List<Note> resultat = new ArrayList<>(listeComplete);

        if (filtreFavorisActif) {
            List<Note> favoris = new ArrayList<>();
            for (Note note : resultat) {
                if (note.isFavori()) favoris.add(note);
            }
            resultat = favoris;
        }

        if (filtreRecherche != null && !filtreRecherche.trim().isEmpty()) {
            String query = filtreRecherche.trim().toLowerCase();
            List<Note> correspondances = new ArrayList<>();
            for (Note note : resultat) {
                if (note.getTitre() != null &&
                        note.getTitre().toLowerCase().contains(query)) {
                    correspondances.add(note);
                }
            }
            resultat = correspondances;
        }

        listeAffichee = resultat;
        notifyDataSetChanged();
    }

    public void filtrerParRecherche(String query) {
        this.filtreRecherche = (query == null) ? "" : query;
        appliquerFiltres();
    }

    public void setFiltreFavoris(boolean actif) {
        this.filtreFavorisActif = actif;
        appliquerFiltres();
    }

    public boolean isFiltreFavorisActif() {
        return filtreFavorisActif;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context)
                .inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.bind(listeAffichee.get(position));
    }

    @Override
    public int getItemCount() {
        return listeAffichee.size();
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {

        private final CardView cardNote;
        private final TextView tvTitre;
        private final TextView tvDate;
        private final ImageView ivFavori;

        private static final long DOUBLE_CLIC_DELAI_MS = 300;
        private long dernierClic = 0;

        NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            cardNote = itemView.findViewById(R.id.cardNote);
            tvTitre  = itemView.findViewById(R.id.tvTitre);
            tvDate   = itemView.findViewById(R.id.tvDate);
            ivFavori = itemView.findViewById(R.id.ivFavori);
        }

        void bind(Note note) {
            tvTitre.setText(note.getTitre());
            tvDate.setText(note.getDateFormatee());

            try {
                cardNote.setCardBackgroundColor(
                        Color.parseColor(note.getCouleur()));
            } catch (IllegalArgumentException e) {
                cardNote.setCardBackgroundColor(
                        Color.parseColor("#219653"));
            }

            ivFavori.setVisibility(
                    note.isFavori() ? View.VISIBLE : View.GONE);

            itemView.setOnClickListener(v -> {
                long maintenant = System.currentTimeMillis();
                boolean estDoubleClic =
                        (maintenant - dernierClic) < DOUBLE_CLIC_DELAI_MS;
                dernierClic = maintenant;

                if (estDoubleClic) {
                    if (listener != null) listener.onNoteDoubleClick(note);
                } else {
                    if (listener != null) listener.onNoteClick(note);
                }
            });
        }
    } // ferme NoteViewHolder
} // ferme NoteAdapter