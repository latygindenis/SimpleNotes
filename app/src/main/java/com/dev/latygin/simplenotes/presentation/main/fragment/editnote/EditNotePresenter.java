package com.dev.latygin.simplenotes.presentation.main.fragment.editnote;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.dev.latygin.simplenotes.App;
import com.dev.latygin.simplenotes.data.room.Note;

import java.util.ArrayList;


@InjectViewState
public class EditNotePresenter extends MvpPresenter<EditNoteView> {


    public void updateNote(Note note) {
        App.getInstance().getNoteDatabase().noteDao().updateNote(note);
    }

    public void createNote(Note note) {
        App.getInstance().getNoteDatabase().noteDao().createNote(note);
        ArrayList<Note> notes = (ArrayList<Note>) App.getInstance().getNoteDatabase().noteDao().getListOfNotes();
        note.setId(notes.get(notes.size() - 1).getId());
    }

    public Note getNoteByKey(long key) {
        return App.getInstance().getNoteDatabase().noteDao().getNoteById(key);
    }

    public void updateState(Note note) {
        getViewState().checkView(note);
    }
}
