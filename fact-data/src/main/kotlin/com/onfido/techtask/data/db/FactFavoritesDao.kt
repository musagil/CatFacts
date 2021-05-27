package com.onfido.techtask.data.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.onfido.techtask.data.FactFavorite
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.annotations.CheckReturnValue

@Dao
interface FactFavoritesDao {

    @CheckReturnValue
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun upsertFavorite(factFavorite: FactFavorite): Completable

    @CheckReturnValue
    @Query("SELECT EXISTS(SELECT * FROM favorites WHERE factId = :factId)")
    fun isFavorite(factId: Int): Observable<Boolean>

    @CheckReturnValue
    @Delete
    fun deleteFavorite(factFavorite: FactFavorite): Completable

    @CheckReturnValue
    @Query("SELECT * FROM favorites")
    fun getFavorites(): Observable<List<FactFavorite>>
}
