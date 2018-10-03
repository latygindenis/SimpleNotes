package com.dev.latygin.simplenotes.presentation.main.fragment.listOfNotes;

import android.support.v7.widget.CardView;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.dev.latygin.simplenotes.data.room.Note;
import com.dev.latygin.simplenotes.domain.RoomService;

import java.util.ArrayList;
import java.util.List;

@InjectViewState
public class ListOfNotesPresenter extends MvpPresenter<ListOfNotesView> {

    private List<Long> idNotesForDelete = new ArrayList<>();

    void setNotesForRecycler() {
        RoomService.getInstance().getListOfNotes(notes -> getViewState().initAdapter((ArrayList<Note>) notes));
    }

    void deleteSelectedNotes() {
        RoomService.getInstance().deleteListOfNotes(idNotesForDelete);
        idNotesForDelete = new ArrayList<>();
    }

    void updateSelectedState(CardView cardView, Note note) {
        if (idNotesForDelete.contains(note.getId())) {
            removeSelectedNote(note);
            getViewState().checkSelectedNotes(cardView, false);
        } else {
            addSelectedNote(note);
            getViewState().checkSelectedNotes(cardView, true);
        }
    }

    List<Long> getListForDelete() {
        return idNotesForDelete;
    }

    boolean isListOfSelectedNotesNotEmpty() {
        return idNotesForDelete.size() != 0;
    }

    int getAmountOfSelected() {
        return idNotesForDelete.size();
    }


    private void addSelectedNote(Note note) {
        idNotesForDelete.add(note.getId());
    }

    private void removeSelectedNote(Note note) {
        idNotesForDelete.remove(note.getId());
    }






}
