package com.dev.latygin.simplenotes.presentation.main.fragment.listOfNotes.recycler;

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

    private ArrayList<Note> notes;

    public interface ClickCallback {
        void click(int position);
    }

    ClickCallback clickCallback;

    public void registerRecyclerCalback(ClickCallback clickCallback) {
        this.clickCallback = clickCallback;
    }

    public ListOfNotesAdapter(ArrayList<Note> notes, FragmentActivity fragmentActivity) {
        this.notes = notes;
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
        holder.titleNote.setText(note.title);
        holder.contentNote.setText(note.content);
        holder.cardNote.setOnClickListener(view -> clickCallback.click(position));
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }
}
