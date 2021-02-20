package com.zikozee.architectureexample;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

public class AddNoteViewModel extends AndroidViewModel {
    private NoteRepository repository;

    public AddNoteViewModel(@NonNull Application application) {
        super(application);
        repository = new NoteRepository(application);
    }

    public void insert(Note note){
        repository.insert(note);
    }
}
