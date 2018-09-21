package com.dev.latygin.simplenotes.presentation.main.fragment.editnote;

import com.arellomobile.mvp.MvpView;
import com.dev.latygin.simplenotes.data.room.Note;

public interface EditNoteView extends MvpView{
    void checkView(Note note);
}
