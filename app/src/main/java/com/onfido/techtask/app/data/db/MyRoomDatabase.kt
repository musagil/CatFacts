package com.onfido.techtask.app.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.onfido.techtask.data.FactFavorite
import com.onfido.techtask.data.db.FactFavoritesDao

@Database(entities = [FactFavorite::class], version = 1)
abstract class MyRoomDatabase : RoomDatabase() {
    abstract fun favoriteDao(): FactFavoritesDao
}
