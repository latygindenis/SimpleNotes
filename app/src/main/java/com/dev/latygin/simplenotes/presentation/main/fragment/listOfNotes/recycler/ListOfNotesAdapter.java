package com.dev.latygin.simplenotes.presentation.main.fragment.listOfNotes.recycler;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.latygin.simplenotes.R;
import com.dev.latygin.simplenotes.data.room.Note;

import java.util.ArrayList;

public class ListOfNotesAdapter extends RecyclerView.Adapter<ListOfNotesViewHolder> {

    private ArrayList<Note> notes = new ArrayList<>();
    private Context context;

    public ListOfNotesAdapter(ArrayList<Note> notes, Context context) {
        this.notes = notes;
        this.context = context;
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
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }
}
