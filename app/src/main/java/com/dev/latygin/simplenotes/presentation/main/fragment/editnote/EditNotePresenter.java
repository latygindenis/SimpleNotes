package com.dev.latygin.simplenotes.presentation.main.fragment.editnote;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.dev.latygin.simplenotes.App;
import com.dev.latygin.simplenotes.data.room.Note;


@InjectViewState
public class EditNotePresenter extends MvpPresenter<EditNoteView> {

    public void updateNoteTitle(Note note, CharSequence charSequence) {
        note.setTitle(String.valueOf(charSequence));
    }

    public void updateNoteContent(Note note, CharSequence charSequence) {
        note.setContent(String.valueOf(charSequence));
    }

    public void updateNote(Note note) {
        App.getInstance().getNoteDatabase().noteDao().updateNote(note);
    }

    public void createNote(Note note) {
        App.getInstance().getNoteDatabase().noteDao().createNote(note);
    }

    public Note getNoteByKey(long key) {
        return App.getInstance().getNoteDatabase().noteDao().getNoteById(key);
    }
}
