package com.dev.latygin.simplenotes.presentation.main.fragment.listOfNotes;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.dev.latygin.simplenotes.App;
import com.dev.latygin.simplenotes.R;
import com.dev.latygin.simplenotes.data.room.Note;
import com.dev.latygin.simplenotes.presentation.main.fragment.listOfNotes.recycler.ListOfNotesAdapter;
import com.dev.latygin.simplenotes.presentation.main.utils.Screens;
import java.util.ArrayList;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListOfNotesFragment extends MvpAppCompatFragment implements ListOfNotesView {

    final int MENU_ID = 1;

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
        fab.setOnClickListener(v -> App.getInstance().getRouter().navigateTo(Screens.DETAIL_OF_NOTE.name()));
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
    }


    @Override
    public void initAdapter(ArrayList<Note> notes) {
        recyclerView.setAdapter(new ListOfNotesAdapter(notes, presenter.getListForDelete()));
        ListOfNotesAdapter listOfNotesAdapter = (ListOfNotesAdapter) recyclerView.getAdapter();

        listOfNotesAdapter.registerRecyclerClickCallback(position -> {
            Note note = notes.get(position);
            App.getInstance().getRouter().navigateTo(Screens.DETAIL_OF_NOTE.name(), note.getId());
        });


        listOfNotesAdapter.registerRecyclerLongClickCallback((cardView, position) -> {
            presenter.updateSelectedState(cardView, notes.get(position));
            return true;
        });
    }

    @Override
    public void checkSelectedNotes(CardView cardView, boolean checked) {
        if (checked) {
            cardView.setCardBackgroundColor(Color.LTGRAY);
            Objects.requireNonNull(getActivity()).invalidateOptionsMenu();
        } else {
            cardView.setCardBackgroundColor(Color.TRANSPARENT);
            Objects.requireNonNull(getActivity()).invalidateOptionsMenu();
        }
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        presenter.setNotesForRecycler();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.recycler_menu, menu);
        menu.setGroupVisible(R.id.groupVsbl, presenter.isListOfSelectedNotesNotEmpty());
        MenuItem menuItemSearchView = menu.findItem(R.id.app_bar_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItemSearchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Toast.makeText(getContext(), query, Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        if (presenter.isListOfSelectedNotesNotEmpty()) {
            menu.add(0, MENU_ID, 0, "Удалить")
                    .setIcon(android.R.drawable.ic_delete)
                    .setShowAsAction(
                            MenuItem.SHOW_AS_ACTION_ALWAYS
                                    | MenuItem.SHOW_AS_ACTION_WITH_TEXT);
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Выделено: " + presenter.getAmountOfSelected());
        } else {
            ((AppCompatActivity) getActivity()).getSupportActionBar().setTitle("Заметки");
            menu.removeItem(MENU_ID);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case MENU_ID:
                AlertDialog.Builder builder = new AlertDialog.Builder(Objects.requireNonNull(getActivity()));
                builder.setTitle("Подтвердите действие")
                        .setMessage("Уверены, что хотите удалить " + presenter.getAmountOfSelected() + " заметки?")
                        .setCancelable(false)
                        .setNegativeButton("Нет",
                                (dialog, which) -> {

                                })
                        .setPositiveButton("Да", (dialog, which) -> {
                            presenter.deleteSelectedNotes();
                            App.getInstance().getRouter().newRootScreen(Screens.LIST_OF_NOTES.name());
                            getActivity().invalidateOptionsMenu();
                        });
                AlertDialog alert = builder.create();
                alert.show();
        }
        return super.onOptionsItemSelected(item);
    }

}
