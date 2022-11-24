package com.example.papb24_11.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Index;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Note note);

    @Update
    void update(Note note);
    @Delete
    void delete(Note note);

    @Query("SELECT * FROM note ORDER BY id ASC")
    LiveData<List<Note>> getAllNotes();

    //Query ber parameter
    @Query("SELECT * FROM note where id = 'keyword' ")
    LiveData<List<Note>> getNotes(int keyword);
    @Query("SELECT * FROM note where id = :keyword ")
    LiveData<List<Note>> getNote(int keyword);


}
