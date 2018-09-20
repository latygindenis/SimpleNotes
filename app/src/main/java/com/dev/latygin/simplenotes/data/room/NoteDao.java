package com.dev.latygin.simplenotes.data.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.dev.latygin.simplenotes.data.NoteRepository;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert
    void createNote(Note note);

    @Update
    void updateNote(Note note);

    @Delete
    void deleteNote(Note note);

    @Query("SELECT * FROM note WHERE id = :id")
    Note getNoteById(long id);

    @Query("SELECT * FROM note")
    List<Note> getListOfNotes();
}
