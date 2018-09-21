package com.dev.latygin.simplenotes.presentation.main.fragment.listOfNotes;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
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

import butterknife.BindView;
import butterknife.ButterKnife;

public class ListOfNotesFragment extends MvpAppCompatFragment implements ListOfNotesView {

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
            presenter.needUpdate = true;
        });

        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(2, 1);
        recyclerView.setLayoutManager(layoutManager);

    }

    @Override
    public void initAdapter(ArrayList<Note> notes) {
        recyclerView.setAdapter(new ListOfNotesAdapter(notes, getActivity(), presenter.isSelectedList));
        ListOfNotesAdapter listOfNotesAdapter = (ListOfNotesAdapter) recyclerView.getAdapter();

        listOfNotesAdapter.registerRecyclerCalback(position -> {
            Note note = notes.get(position);
            App.getInstance().getRouter().navigateTo(Screens.DETAIL_OF_NOTE.name(), note.getId());
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
        setRetainInstance(true);
        presenter.setNotesForRecycler();
    }

}
