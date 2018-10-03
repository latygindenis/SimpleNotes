package com.dev.latygin.simplenotes.presentation.main.activity;

import android.support.design.widget.NavigationView;
import android.view.MenuItem;

import com.arellomobile.mvp.InjectViewState;
import com.arellomobile.mvp.MvpPresenter;
import com.dev.latygin.simplenotes.App;
import com.dev.latygin.simplenotes.R;
import com.dev.latygin.simplenotes.presentation.main.utils.Screens;

@InjectViewState
public class MainActivityPresenter extends MvpPresenter<MainActivityView> {

    void setupDrawerContent(NavigationView navigationView) {
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
                App.getInstance().getRouter().newRootScreen(Screens.LIST_OF_NOTES.name());
                break;
            case R.id.settings_item:
                App.getInstance().getRouter().navigateTo(Screens.DETAIL_OF_NOTE.name(), (long) 1);
                break;
            default:
        }
        menuItem.setChecked(true);
        getViewState().checkDrawerItem(menuItem);
    }
}
