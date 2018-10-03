package com.dev.latygin.simplenotes.data.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.Single;

@Dao
public interface NoteDao {

    @Insert
    long createNote(Note note);

    @Update
    void updateNote(Note note);

    @Query("DELETE FROM note WHERE id = :id")
    void deleteNote(long id);

    @Delete
    void deleteListOfNote(List<Note> notes);

    @Query("SELECT * FROM note WHERE id = :id")
    Single<Note> getNoteById(long id);

    @Query("SELECT * FROM note")
    Flowable<List<Note>> getListOfNotes();
}
