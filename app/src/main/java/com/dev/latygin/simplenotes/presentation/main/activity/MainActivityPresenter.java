package com.dev.latygin.simplenotes.presentation.main.activity;

import android.support.design.widget.NavigationView;
import android.view.MenuItem;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.dev.latygin.simplenotes.R;
import com.dev.latygin.simplenotes.presentation.main.fragment.editnote.EditNoteFragment;
import com.dev.latygin.simplenotes.presentation.main.fragment.listOfNotes.ListOfNotesFragment;

@InjectViewState
public class MainActivityPresenter extends MvpPresenter<MainActivityView> {
    
    public void setupDrawerContent(NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(
                menuItem -> {
                    selectDrawerItem(menuItem);
                    return true;
                }
        );
    }

    private void selectDrawerItem(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.notes_item:
                getViewState().replaceFragment(EditNoteFragment.newInstance());
                break;
            case R.id.settings_item:
                getViewState().replaceFragment(ListOfNotesFragment.newInstance());
                break;
            default:
        }
        menuItem.setChecked(true);
        getViewState().checkDrawerItem(menuItem);
    }
}
