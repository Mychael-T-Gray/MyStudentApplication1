package com.example.myapplication.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;


import com.example.myapplication.entities.Terms;

import java.util.List;

@Dao
public interface TermsDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(Terms terms);
    @Update
    void update(Terms terms);
    @Delete
    void delete(Terms terms);
    @Query("SELECT * FROM TERMS ORDER BY termId ASC" )
    List<Terms> getAllTerms();
}
