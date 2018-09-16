package com.dev.latygin.simplenotes;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.dev.latygin.simplenotes.data.room.Note;
import com.dev.latygin.simplenotes.data.room.NoteDatabase;

public class App extends Application {

    public static App instance;

    private NoteDatabase noteDatabase;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        noteDatabase = Room.databaseBuilder(this,
                NoteDatabase.class, "database")
                .allowMainThreadQueries()
                .build();
        // noteDatabase.noteDao().createNote(new Note());

    }

    public static App getInstance() {
        return instance;
    }

    public NoteDatabase getNoteDatabase() {
        return noteDatabase;
    }
}
