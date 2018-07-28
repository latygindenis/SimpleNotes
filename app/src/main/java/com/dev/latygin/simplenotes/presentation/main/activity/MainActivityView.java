package com.dev.latygin.simplenotes.presentation.main.activity;

import android.support.v4.app.Fragment;
import android.view.MenuItem;

import com.arellomobile.mvp.MvpView;

public interface MainActivityView extends MvpView {
    void checkDrawerItem(MenuItem menuItem);
    void replaceFragment(Fragment fragment);
}
