package com.dev.latygin.simplenotes.presentation.main.fragment.listOfNotes;

import android.os.AsyncTask;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.dev.latygin.simplenotes.App;
import com.dev.latygin.simplenotes.data.room.Note;
import com.dev.latygin.simplenotes.data.room.NoteDatabase;

import java.util.ArrayList;

@InjectViewState
public class ListOfNotesPresenter extends MvpPresenter<ListOfNotesView> {

    public void setNotesForRecycler() {
        NoteDatabase database = App.getInstance().getNoteDatabase();
        ArrayList<Note> notesForRecycler = (ArrayList<Note>) database.noteDao().getListOfNotes();
        getViewState().initAdapter(notesForRecycler);
    }

}
