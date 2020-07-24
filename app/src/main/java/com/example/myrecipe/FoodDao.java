package com.example.myrecipe;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface FoodDao {

    @Query("SELECT * FROM fooddata")
    List<FoodData> getAll();


    @Insert
    void insert(FoodData foodData);

    @Delete
    void delete(FoodData foodData);

    @Update
    void update(FoodData foodData);
}
