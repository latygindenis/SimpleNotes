package com.dev.latygin.simplenotes.presentation.main.fragment.listOfNotes.recycler;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dev.latygin.simplenotes.R;
import com.dev.latygin.simplenotes.data.room.Note;

import java.util.ArrayList;

public class ListOfNotesAdapter extends RecyclerView.Adapter<ListOfNotesViewHolder> {


    private ArrayList<Note> notes;
    ArrayList<Boolean> isSelectedList;
    public interface ClickCallback {
        void click(int position);
    }

    public interface LongClickCallback {
        boolean longClick(CardView cardView, int position);
    }

    ClickCallback clickCallback;
    LongClickCallback longClickCallback;

    public void registerRecyclerClickCalback(ClickCallback clickCallback) {
        this.clickCallback = clickCallback;
    }

    public void registerRecyclerLongClickCalback(LongClickCallback longClickCallback) {
        this.longClickCallback = longClickCallback;
    }


    public ListOfNotesAdapter(ArrayList<Note> notes, FragmentActivity fragmentActivity, ArrayList<Boolean> isSelectedList) {
        this.notes = notes;
        this.isSelectedList = isSelectedList;
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

        if (isSelectedList.get(position)) {
            holder.cardNote.setCardBackgroundColor(Color.LTGRAY);
        } else {
            holder.cardNote.setCardBackgroundColor(Color.TRANSPARENT);
        }

        holder.cardNote.setOnClickListener(view -> clickCallback.click(position));
        holder.cardNote.setOnLongClickListener(v -> longClickCallback.longClick(holder.cardNote, position));
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }


}
