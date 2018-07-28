package com.dev.latygin.simplenotes.presentation.main;

import android.view.MenuItem;

import com.arellomobile.mvp.MvpView;

public interface MainActivityView extends MvpView {
    void checkDrawerItem(MenuItem menuItem);
}
