package com.dev.latygin.simplenotes.presentation.main.fragment.listOfNotes;

import android.support.v7.widget.CardView;

import com.arellomobile.mvp.MvpView;
import com.dev.latygin.simplenotes.data.room.Note;

import java.util.ArrayList;

public interface ListOfNotesView extends MvpView {
    void initAdapter(ArrayList<Note> notes);

    void checkSelectedNotes(CardView cardView, boolean checked);
}
