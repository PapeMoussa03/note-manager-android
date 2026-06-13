package com.esp.notemanager.adapter;

import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.esp.notemanager.R;
import com.esp.notemanager.Note;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    public interface OnNoteClickListener {
        void onNoteClick(Note note);
    }

    public interface OnNoteDoubleClickListener {
        void onNoteDoubleClick(Note note);
    }

    private List<Note> notes;
    private final OnNoteClickListener clickListener;
    private final OnNoteDoubleClickListener doubleClickListener;
    private static final long DOUBLE_CLICK_DELAY = 300L;

    public NoteAdapter(List<Note> notes,
                       OnNoteClickListener clickListener,
                       OnNoteDoubleClickListener doubleClickListener) {
        this.notes = notes != null ? notes : new ArrayList<>();
        this.clickListener = clickListener;
        this.doubleClickListener = doubleClickListener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_note, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.bind(notes.get(position), clickListener, doubleClickListener);
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    public void setNotes(List<Note> newNotes) {
        this.notes = newNotes != null ? newNotes : new ArrayList<>();
        notifyDataSetChanged();
    }

    public List<Note> getNotes() {
        return notes;
    }

    static class NoteViewHolder extends RecyclerView.ViewHolder {

        private final CardView cardNote;
        private final TextView tvTitre;
        private final TextView tvDate;
        private final ImageView ivFavori;

        private int clickCount = 0;
        private final Handler handler = new Handler();
        private Runnable singleClickAction;

        NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            cardNote = itemView.findViewById(R.id.cardNote);
            tvTitre  = itemView.findViewById(R.id.tvTitre);
            tvDate   = itemView.findViewById(R.id.tvDate);
            ivFavori = itemView.findViewById(R.id.ivFavori);
        }

        void bind(Note note,
                  OnNoteClickListener clickListener,
                  OnNoteDoubleClickListener doubleClickListener) {

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
                clickCount++;
                if (clickCount == 1) {
                    singleClickAction = () -> {
                        clickCount = 0;
                        if (clickListener != null) clickListener.onNoteClick(note);
                    };
                    handler.postDelayed(singleClickAction, DOUBLE_CLICK_DELAY);
                } else if (clickCount == 2) {
                    handler.removeCallbacks(singleClickAction);
                    clickCount = 0;
                    if (doubleClickListener != null)
                        doubleClickListener.onNoteDoubleClick(note);
                }
            });
        }
    }
}