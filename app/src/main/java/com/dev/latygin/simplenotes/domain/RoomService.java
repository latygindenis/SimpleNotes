package com.dev.latygin.simplenotes.domain;

import android.util.Log;

import com.dev.latygin.simplenotes.App;
import com.dev.latygin.simplenotes.data.room.Note;
import com.dev.latygin.simplenotes.data.room.NoteDao;

import java.util.List;

import io.reactivex.Completable;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableCompletableObserver;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

public class RoomService {


    private static RoomService instance;
    private NoteDao noteDao;

    private RoomService() {
        noteDao = App.getInstance().getNoteDatabase().noteDao();
    }

    public static RoomService getInstance() {
        if (instance == null) {
            instance = new RoomService();
        }
        return instance;
    }

    public void createNote(IdOfNewNoteCallback idOfNewNoteCallback) {
        Single.fromCallable(() -> noteDao.createNote(new Note()))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(id -> idOfNewNoteCallback.onSuccess(id));
    }

    public void updateNote(Note note) {
        Completable.fromAction(() -> noteDao.updateNote(note))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        Log.i("note", "updateNoteLol: " + note.getId());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.i("note", "Error");
                    }
                });
    }

    public void deleteNote(long key) {
        Completable.fromAction(() -> noteDao.deleteNote(key))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableCompletableObserver() {
                    @Override
                    public void onComplete() {
                        Log.i("note", "del");
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    public void deleteListOfNotes(List<Long> notes) {
        for (int i = 0; i < notes.size(); i++) {
            deleteNote(notes.get(i));
        }
    }

    public void getNoteById(long key, GetNoteByIdCallback getNoteByIdCallback) {
        noteDao.getNoteById(key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableSingleObserver<Note>() {
                    @Override
                    public void onSuccess(Note note) {
                        getNoteByIdCallback.onSuccess(note);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getNoteByIdCallback.onError(e);
                    }
                });
    }

    public void getListOfNotes(ListOfNoteCallback listOfNoteCallback) {
        noteDao.getListOfNotes()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(notes -> listOfNoteCallback.onAccept(notes));
    }

    public interface ListOfNoteCallback {
        void onAccept(List<Note> notes);
    }

    public interface GetNoteByIdCallback {
        void onSuccess(Note note);

        void onError(Throwable e);
    }

    public interface IdOfNewNoteCallback {
        void onSuccess(long key);
    }
}
