package com.dev.latygin.simplenotes.presentation.main.utils;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;

import com.dev.latygin.simplenotes.presentation.main.fragment.editnote.EditNoteFragment;
import com.dev.latygin.simplenotes.presentation.main.fragment.listOfNotes.ListOfNotesFragment;

import ru.terrakok.cicerone.android.SupportFragmentNavigator;

public class Navigator extends SupportFragmentNavigator {

    public Navigator(FragmentManager fragmentManager, int containerId) {
        super(fragmentManager, containerId);
    }

    @Override
    protected Fragment createFragment(String screenKey, Object data) {
        Screens screens = Enum.valueOf(Screens.class, screenKey);
        switch (screens) {
            case LIST_OF_NOTES:
                return ListOfNotesFragment.newInstance();
            case DETAIL_OF_NOTE:
                if (data == null) {
                    return EditNoteFragment.newInstance();
                } else {
                    return EditNoteFragment.newInstance((long) data);
                }
            case SETTINGS:
                return null;
            case ABOUT_US:
                return null;
            default:
                return ListOfNotesFragment.newInstance();
        }
    }

    @Override
    protected void showSystemMessage(String message) {

    }

    @Override
    protected void exit() {
        Log.i("exit", "yep");
    }

}
