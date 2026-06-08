package com.esp.notemanager.adapter;

import android.graphics.Color;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.esp.notemanager.R;
import com.esp.notemanager.model.Note;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

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
        Note note = notes.get(position);
        holder.bind(note, clickListener, doubleClickListener);
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

        private final RelativeLayout container;
        private final TextView tvTitle;
        private final TextView tvDate;
        private final ImageView ivFavorite;

        private int clickCount = 0;
        private final Handler handler = new Handler();
        private Runnable singleClickAction;

        NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            container  = itemView.findViewById(R.id.noteItemContainer);
            tvTitle    = itemView.findViewById(R.id.tvNoteTitle);
            tvDate     = itemView.findViewById(R.id.tvNoteDate);
            ivFavorite = itemView.findViewById(R.id.ivFavorite);
        }

        void bind(Note note,
                  OnNoteClickListener clickListener,
                  OnNoteDoubleClickListener doubleClickListener) {

            tvTitle.setText(note.getTitre());
            tvDate.setText(formatDate(note.getDateCreation()));

            try {
                container.setBackgroundColor(Color.parseColor(note.getCouleur()));
            } catch (IllegalArgumentException e) {
                container.setBackgroundColor(Color.parseColor("#219653"));
            }

            if (note.isFavori()) {
                ivFavorite.setVisibility(View.VISIBLE);
            } else {
                ivFavorite.setVisibility(View.GONE);
            }

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
                    if (doubleClickListener != null) doubleClickListener.onNoteDoubleClick(note);
                }
            });
        }

        private String formatDate(long timestamp) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd MMM yyyy", Locale.FRENCH);
            return sdf.format(new Date(timestamp));
        }
    }
}