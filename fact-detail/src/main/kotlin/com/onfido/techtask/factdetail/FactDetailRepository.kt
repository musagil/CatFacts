package com.onfido.techtask.factdetail

import android.annotation.SuppressLint
import com.onfido.techtask.OpenForTesting
import com.onfido.techtask.data.FactFavorite
import com.onfido.techtask.data.db.FactFavoritesDao
import com.onfido.techtask.network.di.NetworkScheduler
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.annotations.CheckReturnValue
import io.reactivex.disposables.Disposable
import javax.inject.Inject

@OpenForTesting
internal class FactDetailRepository @Inject constructor(
    private val factFavoritesDao: FactFavoritesDao,
    @NetworkScheduler private val networkScheduler: Scheduler
) {

    @SuppressLint("RxJava2SubscribeMissingOnError")
    @CheckReturnValue
    fun favoriteRepo(factFavorite: FactFavorite): Disposable =
        factFavoritesDao.upsertFavorite(factFavorite).subscribe()

    @SuppressLint("RxJava2SubscribeMissingOnError")
    @CheckReturnValue
    fun unfavoriteRepo(factFavorite: FactFavorite): Disposable =
        factFavoritesDao.deleteFavorite(factFavorite).subscribe()

    @CheckReturnValue
    fun observeRepo(repoId: Int): Observable<Boolean> =
        factFavoritesDao.isFavorite(repoId).subscribeOn(networkScheduler)
}
