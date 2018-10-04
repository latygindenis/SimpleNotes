package com.dev.latygin.simplenotes.presentation.main.fragment.listOfNotes.util;

import android.support.v7.util.DiffUtil;
import android.util.Log;

import com.dev.latygin.simplenotes.data.room.Note;

import java.util.List;

public class NotesDiffUtilCallback extends DiffUtil.Callback {

    private final List<Note> oldList;
    private final List<Note> newList;

    public NotesDiffUtilCallback(List<Note> oldList, List<Note> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        Note oldNote = oldList.get(oldItemPosition);
        Note newNote = newList.get(newItemPosition);
        return oldNote.getId() == newNote.getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        Log.i("lol", "areContentsTheSame: ");
        Note oldNote = oldList.get(oldItemPosition);
        Note newNote = newList.get(newItemPosition);
        return oldNote.getContent().equals(newNote.getContent()) &&
                oldNote.getTitle().equals(newNote.getTitle());
    }

}
