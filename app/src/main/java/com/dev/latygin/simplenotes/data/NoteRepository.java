package com.dev.latygin.simplenotes.data;

import com.dev.latygin.simplenotes.data.room.Note;

import java.util.List;

public interface NoteRepository {
    void createNote(Note note);

    void updateNote(long key);

    void deleteNote(long key);

    List<Note> getNoteById(long key);

    List<Note> getListOfNotes();
}
