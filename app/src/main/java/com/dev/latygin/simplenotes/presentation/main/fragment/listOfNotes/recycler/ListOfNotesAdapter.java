package com.dev.latygin.simplenotes.presentation.main.fragment.listOfNotes.recycler;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.latygin.simplenotes.R;
import com.dev.latygin.simplenotes.data.room.Note;

import java.util.ArrayList;

public class ListOfNotesAdapter extends RecyclerView.Adapter<ListOfNotesViewHolder> {


    private ArrayList<Boolean> selected;
    private ArrayList<Note> notes;

    public interface ClickCallback {
        void click(int position);
    }

    ClickCallback clickCallback;

    public void registerRecyclerCalback(ClickCallback clickCallback) {
        this.clickCallback = clickCallback;
    }

    public ListOfNotesAdapter(ArrayList<Note> notes, FragmentActivity fragmentActivity, ArrayList<Boolean> selected) {
        this.notes = notes;
        this.selected = selected;
    }

    @NonNull
    @Override
    public ListOfNotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.note_card, parent, false);
        return new ListOfNotesViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ListOfNotesViewHolder holder, int position) {
        Note note = notes.get(position);
        holder.titleNote.setText(note.getTitle());
        holder.contentNote.setText(note.getContent());

        holder.cardNote.setOnClickListener(view -> clickCallback.click(position));
        if (selected.get(position)) {
            holder.cardNote.setCardBackgroundColor(Color.LTGRAY);
        } else {
            holder.cardNote.setCardBackgroundColor(Color.TRANSPARENT);
        }

        holder.cardNote.setOnLongClickListener(v -> {
            if (!selected.get(position)) {
                holder.cardNote.setCardBackgroundColor(Color.LTGRAY);
                selected.set(position, true);
                return true;
            } else {
                holder.cardNote.setCardBackgroundColor(Color.TRANSPARENT);
                selected.set(position, false);
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }


}
