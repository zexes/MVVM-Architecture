package com.zikozee.architectureexample;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

//TODO: info==>>>STEP 3
@Database(entities = {Note.class}, version = 1, exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase {

    // to create a singleton
    private static NoteDatabase instance;

    public abstract NoteDao noteDao();

    //TODO: info==>>> synchronized ensures only one instance can be accessed on all threads
    public static synchronized NoteDatabase getInstance(Context context){
        if(instance == null){
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    NoteDatabase.class, "note_database")
                    .fallbackToDestructiveMigration() //ensures database is migratable by increasing version number else app will crash
                    .addCallback(roomCallback)
                    .build();
        }

        return instance;
    }

    //TODO: info==>>> STEP 4.1 PRE-POPULATING DB
    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) { // used once when the app is created //there is also onOpen(called every time database is opened)
            super.onCreate(db);
            // we can do this as onCreate available after db has been created
            AppExecutors.getInstance().diskIO().execute(() -> {
                NoteDao noteDao =  instance.noteDao();
                noteDao.insert(new Note("Title 1", "Descriprion 1", 1));
                noteDao.insert(new Note("Title 2", "Descriprion 2", 2));
                noteDao.insert(new Note("Title 3", "Descriprion 3", 3));
            });
        }
    };


}
