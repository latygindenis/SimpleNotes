package com.dev.latygin.simplenotes;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.dev.latygin.simplenotes.data.room.Note;
import com.dev.latygin.simplenotes.data.room.NoteDatabase;
import com.dev.latygin.simplenotes.presentation.main.utils.Screens;

import ru.terrakok.cicerone.Cicerone;
import ru.terrakok.cicerone.NavigatorHolder;
import ru.terrakok.cicerone.Router;

public class App extends Application {

    public static App instance;

    private NoteDatabase noteDatabase;
    private Cicerone<Router> cicerone;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        noteDatabase = Room.databaseBuilder(this,
                NoteDatabase.class, "database")
                .allowMainThreadQueries()
                .build();
        initCicerone();
        getRouter().newRootScreen(Screens.LIST_OF_NOTES.name());

    }

    private void initCicerone() {
        cicerone = Cicerone.create();
    }

    public NavigatorHolder getNavigatorHolder() {
        return cicerone.getNavigatorHolder();
    }

    public Router getRouter() {
        return cicerone.getRouter();
    }

    public static App getInstance() {
        return instance;
    }

    public NoteDatabase getNoteDatabase() {
        return noteDatabase;
    }
}
