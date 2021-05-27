package com.onfido.techtask.app.di

import androidx.room.Room
import com.onfido.techtask.RoomScope
import com.onfido.techtask.app.App
import com.onfido.techtask.app.data.db.MyRoomDatabase
import dagger.Module
import dagger.Provides

@Module
internal interface RoomModule {
    companion object {
        @Provides
        @RoomScope
        fun providesRoomDatabase(app: App): MyRoomDatabase =
            Room.databaseBuilder(app, MyRoomDatabase::class.java, "githubRepositories.db")
                .build()

        @Provides
        fun providesFavoritesDao(myRoomDatabase: MyRoomDatabase) =
            myRoomDatabase.favoriteDao()
    }
}
