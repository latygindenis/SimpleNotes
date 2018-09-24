package com.dev.latygin.simplenotes.presentation.main.fragment.listOfNotes;

import android.util.Log;

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
            Log.i("database", "yep");
            getViewState().initAdapter(notesForRecycler);
    }

    public void deleteSelectedNotes(boolean[] isSelectedList) {

        NoteDatabase database = App.getInstance().getNoteDatabase();
        ArrayList<Note> notesForRecycler = (ArrayList<Note>) database.noteDao().getListOfNotes();

        for (int i = 0; i < isSelectedList.length; i++) {
            if (isSelectedList[i]) {
                database.noteDao().deleteNote(notesForRecycler.get(i));
            }
        }
    }


}
