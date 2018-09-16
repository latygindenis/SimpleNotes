package com.dev.latygin.simplenotes.presentation.main.fragment.listOfNotes;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.dev.latygin.simplenotes.R;
import com.dev.latygin.simplenotes.data.room.Note;
import com.dev.latygin.simplenotes.presentation.main.fragment.listOfNotes.recycler.ListOfNotesAdapter;

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
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        presenter.setNotesForRecycler();

    }

    @Override
    public void initAdapter(ArrayList<Note> notes) {
        recyclerView.setAdapter(new ListOfNotesAdapter(notes, getActivity()));
    }
}
