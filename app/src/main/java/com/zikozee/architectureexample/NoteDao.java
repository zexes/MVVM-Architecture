package com.zikozee.architectureexample;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

//TODO STEP 2
@Dao
public interface NoteDao {

    @Insert
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void delete(Note note);

    @Query("DELETE FROM note_table")
    void deleteAllNotes();

    //based on the live data added we can observe the list of Notes
    //Hence if any changes, the List<Note> will be updated & our activity will be notified
    //and room takes care of updating the LiveData object all we need do just as below is return LiveData
    //******** my exam i could have return DESC to update t the latest *****
    @Query("SELECT * FROM note_table ORDER BY priority DESC")
    LiveData<List<Note>> getAllNotes();
}
