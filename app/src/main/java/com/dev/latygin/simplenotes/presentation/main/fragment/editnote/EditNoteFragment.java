package com.dev.latygin.simplenotes.presentation.main.fragment.editnote;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.arellomobile.mvp.MvpAppCompatFragment;
import com.arellomobile.mvp.presenter.InjectPresenter;
import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.dev.latygin.simplenotes.R;
import com.dev.latygin.simplenotes.data.models.Note;

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

    public static EditNoteFragment newInstance() {
        return new EditNoteFragment();
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
        presenter.setupEditNote(titleEditText, contentEditText);
        setHasOptionsMenu(true);

    }

    @Override
    public void updateNote(Note note) {
        titleEditText.setText(note.getTitle());
        contentEditText.setText(note.getContent());
    }
}
