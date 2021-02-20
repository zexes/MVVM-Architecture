package com.zikozee.architectureexample;

import android.app.Application;

import androidx.lifecycle.LiveData;

import java.util.List;

//TODO: info==>>> STEP 4
public class NoteRepository {

    private NoteDao noteDao;
    private LiveData<List<Note>> allNotes;

    //Application is a subclass of Context so we can use it as a Context ==>>>> checkout "What is Context in Android" in exam book mark
    public NoteRepository(Application application){
        NoteDatabase database = NoteDatabase.getInstance(application);
        noteDao = database.noteDao();
        allNotes = noteDao.getAllNotes(); // we need initialize else noteDao already contains it
    }

    // now we create methods all our db operations
    // ***and these are the apis that are exposed to the outside****
    public void insert(final Note note){
        AppExecutors.getInstance().diskIO().execute(() -> noteDao.insert(note));
    }

    public void update(Note note){
        AppExecutors.getInstance().diskIO().execute(() -> noteDao.update(note));
    }

    public void delete(Note note){
        AppExecutors.getInstance().diskIO().execute(() -> noteDao.delete(note));
    }

    public void deleteAllNotes(){
        AppExecutors.getInstance().diskIO().execute(() -> noteDao.deleteAllNotes());
    }

    //TODO: info==>>>
    //room executes this automatically to get all Notes on a background thread but not so for the above
    // as they not LiveData[s]
    public LiveData<List<Note>> getAllNotes(){
        return allNotes;
    }


}
