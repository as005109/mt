package com.lisovskaya.lab3.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lisovskaya.lab3.api.OmdbSearchResultMovie

@Database(entities = [OmdbSearchResultMovie::class], version = 1)
abstract class MoviesDatabase : RoomDatabase() {
    abstract fun getFavoritesDao(): MovieDao
}