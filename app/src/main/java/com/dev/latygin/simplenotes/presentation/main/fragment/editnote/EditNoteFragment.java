package com.dev.latygin.simplenotes.presentation.main.fragment.editnote;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.dev.latygin.simplenotes.App;
import com.dev.latygin.simplenotes.R;
import com.dev.latygin.simplenotes.data.room.Note;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EditNoteFragment extends MvpAppCompatFragment implements EditNoteView{

    @InjectPresenter
    EditNotePresenter presenter;

    @ProvidePresenter
    public EditNotePresenter presenter(){
        return new EditNotePresenter();
    }

    @BindView(R.id.title_edit_text)
    EditText titleEditText;

    @BindView(R.id.content_edit_text)
    EditText contentEditText;

    Note note = new Note();

    public static EditNoteFragment newInstance() {
        return new EditNoteFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            note.id = (long) getArguments().get("key");
            note.setContent((String) getArguments().get("content"));
            note.setTitle((String) getArguments().get("title"));
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
        titleEditText.setText(note.title);
        contentEditText.setText(note.content);

        titleEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                presenter.updateNoteTitle(note, charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        contentEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                presenter.updateNoteContent(note, charSequence);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        setHasOptionsMenu(true);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

    @Override
    public void onStop() {
        super.onStop();
        if (note.id == 0) {
            presenter.createNote(note);
        } else {
            presenter.updateNote(note);
        }

    }
}
