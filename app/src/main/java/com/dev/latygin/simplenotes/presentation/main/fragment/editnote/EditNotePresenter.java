package com.dev.latygin.simplenotes.presentation.main.fragment.editnote;


import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.dev.latygin.simplenotes.data.room.Note;
import com.dev.latygin.simplenotes.domain.RoomService;



@InjectViewState
public class EditNotePresenter extends MvpPresenter<EditNoteView> {

    boolean noteIsCreate = false;
    private Note curNote;

    void createNote() {
        RoomService.getInstance().createNote(key -> {
            curNote = new Note();
            curNote.setId(key);
            noteIsCreate = true;
        });
    }

    void getNoteByKey(long key) {
        RoomService.getInstance().getNoteById(key, new RoomService.GetNoteByIdCallback() {
            @Override
            public void onSuccess(Note note) {
                curNote = note;
                noteIsCreate = true;
                updateState(curNote);
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    void updateNote(String title, String content) {
        if (title == null) {
            title = "";
        }
        if (content == null) {
            content = "";
        }
        curNote.setTitle(title);
        curNote.setContent(content);
        RoomService.getInstance().updateNote(curNote);
    }

    private void updateState(Note note) {
        getViewState().checkView(note);
    }
}
