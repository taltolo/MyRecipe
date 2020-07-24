package com.example.myrecipe;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {FoodData.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    public abstract FoodDao FoodDao();


}