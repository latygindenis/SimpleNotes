package com.dev.latygin.simplenotes.presentation.main.fragment.listOfNotes;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.dev.latygin.simplenotes.App;
import com.dev.latygin.simplenotes.R;
import com.dev.latygin.simplenotes.data.room.Note;
import com.dev.latygin.simplenotes.presentation.main.fragment.listOfNotes.recycler.ListOfNotesAdapter;
import com.dev.latygin.simplenotes.presentation.main.utils.Screens;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListOfNotesFragment extends MvpAppCompatFragment implements ListOfNotesView {

    final int MENU_ID = 1;

    int amountOfSelected;
    boolean[] isSelectedList;

    @InjectPresenter
    ListOfNotesPresenter presenter;


    @ProvidePresenter
    public ListOfNotesPresenter presenter() {
        return new ListOfNotesPresenter();
    }

    @BindView(R.id.fab)
    FloatingActionButton fab;

    @BindView(R.id.noteRecycler)
    RecyclerView recyclerView;


    public static ListOfNotesFragment newInstance() {
        return new ListOfNotesFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.list_of_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        fab.setOnClickListener(v -> {
            App.getInstance().getRouter().navigateTo(Screens.DETAIL_OF_NOTE.name());
            amountOfSelected = 0;
        });
        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2, 1);
        recyclerView.setLayoutManager(layoutManager);

    }


    @Override
    public void initAdapter(ArrayList<Note> notes) {
        if (isSelectedList == null || isSelectedList.length < notes.size()) {
            isSelectedList = new boolean[notes.size()];
            for (int i = 0; i < isSelectedList.length; i++) {
                isSelectedList[i] = false;
            }
        }
        recyclerView.setAdapter(new ListOfNotesAdapter(notes, getActivity(), isSelectedList));
        ListOfNotesAdapter listOfNotesAdapter = (ListOfNotesAdapter) recyclerView.getAdapter();

        listOfNotesAdapter.registerRecyclerClickCalback(position -> {
            Note note = notes.get(position);
            App.getInstance().getRouter().navigateTo(Screens.DETAIL_OF_NOTE.name(), note.getId());
        });


        listOfNotesAdapter.registerRecyclerLongClickCalback((cardView, position) -> {

            if (!isSelectedList[position]) {
                isSelectedList[position] = true;
                cardView.setCardBackgroundColor(Color.LTGRAY);
                getActivity().invalidateOptionsMenu();
                amountOfSelected++;
                return true;
            } else {
                isSelectedList[position] = false;
                cardView.setCardBackgroundColor(Color.TRANSPARENT);
                getActivity().invalidateOptionsMenu();
                amountOfSelected--;
                return true;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.setNotesForRecycler();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (savedInstanceState != null) {
            isSelectedList = savedInstanceState.getBooleanArray("selectedNotes");
            amountOfSelected = savedInstanceState.getInt("amountOfSelected");
            Log.i("test", "onCreate: " + isSelectedList.length);
        } else {
            amountOfSelected = 0;
        }
        presenter.setNotesForRecycler();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.recycler_menu, menu);
        menu.setGroupVisible(R.id.groupVsbl, amountOfSelected > 0);
        if (amountOfSelected > 0) {
            menu.add(0, MENU_ID, 0, "Удалить")
                    .setIcon(android.R.drawable.ic_delete)
                    .setShowAsAction(
                            MenuItem.SHOW_AS_ACTION_ALWAYS
                                    | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Выделено: " + amountOfSelected);
        } else {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Заметки");
            menu.removeItem(MENU_ID);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_ID:
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Подтвердите действие")
                        .setMessage("Уверены, что хотите удалить " + amountOfSelected + " заметки?")
                        .setCancelable(false)
                        .setNegativeButton("Нет",
                                (dialog, which) -> {

                                })
                        .setPositiveButton("Да", (dialog, which) -> {
                            presenter.deleteSelectedNotes(isSelectedList);
                            App.getInstance().getRouter().newRootScreen(Screens.LIST_OF_NOTES.name());
                            getActivity().invalidateOptionsMenu();
                        });
                AlertDialog alert = builder.create();
                alert.show();
        }
        Log.i("item", String.valueOf(item.getItemId()));
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBooleanArray("selectedNotes", isSelectedList);
        outState.putInt("amountOfSelected", amountOfSelected);
    }
}
