package com.dev.latygin.simplenotes.presentation.main.fragment.editnote;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toolbar;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.dev.latygin.simplenotes.App;
import com.dev.latygin.simplenotes.R;
import com.dev.latygin.simplenotes.data.room.Note;
import com.dev.latygin.simplenotes.presentation.main.activity.MainActivity;
import com.dev.latygin.simplenotes.presentation.main.fragment.listOfNotes.ListOfNotesFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditNoteFragment extends MvpAppCompatFragment implements EditNoteView {

    @InjectPresenter
    EditNotePresenter presenter;

    @ProvidePresenter
    public EditNotePresenter presenter() {
        return new EditNotePresenter();
    }

    @BindView(R.id.title_edit_text)
    EditText titleEditText;

    @BindView(R.id.content_edit_text)
    EditText contentEditText;


    Note note;

    public static EditNoteFragment newInstance(long key) {
        EditNoteFragment editNoteFragment = new EditNoteFragment();
        Bundle bundle = new Bundle();
        bundle.putLong("key", key);
        editNoteFragment.setArguments(bundle);
        return editNoteFragment;
    }

    public static EditNoteFragment newInstance() {
        return new EditNoteFragment();
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        if (getArguments() != null) {
            note = presenter.getNoteByKey(getArguments().getLong("key"));
        } else {
            note = new Note();
            presenter.createNote(note);
        }
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.full_notes, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        presenter.updateState(note);
    }


    @Override
    public void onDestroy() {
        note.setTitle(String.valueOf(titleEditText.getText()));
        note.setContent(String.valueOf(contentEditText.getText()));
        if (note.getId() == 0) {
            presenter.createNote(note);
        } else {
            presenter.updateNote(note);
        }
        super.onDestroy();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.note_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getFragmentManager().beginTransaction()
                        .replace(R.id.main_container, ListOfNotesFragment.newInstance()).commit();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void checkView(Note note) {
        titleEditText.setText(note.getTitle());
        contentEditText.setText(note.getContent());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong("key", note.getId());
    }

}
